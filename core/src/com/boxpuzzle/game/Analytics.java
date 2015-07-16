package com.boxpuzzle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
//import java.util.LinkedHashMap;
import java.util.*;
import java.util.Timer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;

/**
 * Created by Matt on 3/9/15.
 */
public class Analytics {
    private Net.HttpRequest httpRequest;
    private SomeObject obj;
    private Json json;
    private String strObj, name, appType;
    private long session;
    FileHandle analytics_file;
    private Preferences pref;
    private java.util.Timer autoModeTime;

    public Analytics() {
        appType = Gdx.app.getType().toString();
        java.util.Date date = new java.util.Date();
        session = date.getTime();
        pref = Gdx.app.getPreferences("Prefs");
        autoModeTime = new Timer();

        name = pref.getString("id", "No Id");
        if (name.equals("No Id")){
            name = String.valueOf(session);
            pref.putString("id", name);
            pref.flush();
        }

        if (appType == "WebGL"){
            httpRequest = new Net.HttpRequest("POST");

            httpRequest.setHeader("Content-Type", "application/json");
            httpRequest.setHeader("Accept", "application/json");
            httpRequest.setUrl("/create_event");
        } else{
            analytics_file = Gdx.files.local("analytics_file.txt");
            analytics_file.writeString("", true);

            obj = new SomeObject();
            obj.event = "Start";
            obj.user = name;
            obj.session = session;

            json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
            System.out.println(json.toJson(obj));

            httpRequest = new Net.HttpRequest("POST");
            httpRequest.setContent(json.toJson(obj));

            httpRequest.setHeader("Content-Type", "application/json");
            httpRequest.setHeader("Accept", "application/json");
            httpRequest.setUrl("http://104.131.200.26/create_event");
        }
    }

    public void writeEvent(String event){
        switch (Gdx.app.getType()){
            case WebGL:
                strObj = "{\"events\":[{\"user\":\""+name+"\",\"session\":"+session+",\"event\":\""+ event +"\"}]}";
                createEvent();
                break;
            default:
                obj.event = event;
                strObj = json.toJson(obj);
                analytics_file.writeString(strObj + ",", true);
                break;
        }
    }

    public String readFile(){
        return analytics_file.readString().substring(0, analytics_file.readString().length() -1);
    }

    public void createEvent(){
        switch (Gdx.app.getType()){
            case WebGL:
                httpRequest.setContent(strObj);
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

                    public void handleHttpResponse(Net.HttpResponse httpResponse){
                        int statusCode = httpResponse.getStatus().getStatusCode();
                        System.out.println(statusCode);

                        String responseJson = httpResponse.getResultAsString();
                        if (responseJson.equals("Create that event")){
                            strObj = "";
                        }
                    }

                    public void failed(Throwable t){
                        System.out.println("Request Failed");
                    }

                    @Override
                    public void cancelled(){
                        System.out.println("Cancelled");
                    }
                });
                break;
            default:
                this.autoModeTime.purge();
                this.autoModeTime.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        httpRequest.setContent("{\"events\":[" + readFile() + "]}");
                        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                int statusCode = httpResponse.getStatus().getStatusCode();
                                System.out.println(statusCode);

                                String responseJson = httpResponse.getResultAsString();
                                if (responseJson.equals("Event Created")) {
                                    System.out.println("Create events SUCCESS");
                                    analytics_file.writeString("", false);
                                }
                            }

                            public void failed(Throwable t) {
                                System.out.println("Request Failed");
                                System.out.println(t);
                            }

                            @Override
                            public void cancelled() {
                                System.out.println("Cancelled");
                            }
                        });
                    }
                }, 0);
                break;
        }
    }
}
