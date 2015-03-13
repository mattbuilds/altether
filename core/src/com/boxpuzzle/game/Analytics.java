package com.boxpuzzle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
//import java.util.LinkedHashMap;
import java.util.Map;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.LinkedHashMap;

/**
 * Created by Matt on 3/9/15.
 */
public class Analytics {

    public class SomeObject{
        String event;
        String user;
        String session;
    }

    public Analytics(){

        SomeObject obj = new SomeObject();
        obj.event = "Bullshit";
        obj.user = "1";
        obj.session = "20";

        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        System.out.println(json.toJson(obj));

        Net.HttpRequest httpRequest = new Net.HttpRequest("POST");
        httpRequest.setContent(json.toJson(obj));

        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader("Accept", "application/json");
        httpRequest.setUrl("http://104.131.200.26/create_event");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse){
                int statusCode = httpResponse.getStatus().getStatusCode();
                System.out.println(statusCode);

                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println(responseJson);
                }
                catch(Exception exception) {
                    exception.printStackTrace();
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
    }
}
