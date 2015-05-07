package com.boxpuzzle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
//import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.LinkedHashMap;

/**
 * Created by Matt on 3/9/15.
 */
public class Analytics {
    private Net.HttpRequest httpRequest;
    private SomeObject obj;
    private Json json;
    private String strObj;
    FileHandle analytics_file;
    private String appType;

    public Analytics() {
        appType = Gdx.app.getType().toString();
        System.out.println(appType);
        if (appType == "WebGL"){
            // Do nothing
        } else{
            analytics_file = Gdx.files.local("analytics_file.txt");
            analytics_file.writeString("", true);

            obj = new SomeObject();
            java.util.Date date = new java.util.Date();
            obj.event = "Start";
            obj.user = "1";
            obj.session = date.getTime();

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
                //Do Nothing
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
                //Do Nothing
                break;
            default:
                httpRequest.setContent("{\"events\":[" + readFile() + "]}");
                //Gdx.net.sendHttpRequest(httpRequest, null);

                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

                    public void handleHttpResponse(Net.HttpResponse httpResponse){
                        int statusCode = httpResponse.getStatus().getStatusCode();
                        System.out.println(statusCode);

                        String responseJson = httpResponse.getResultAsString();
                        if (responseJson.equals("Create that event")){
                            analytics_file.writeString("", false);
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
        }
    }
}
