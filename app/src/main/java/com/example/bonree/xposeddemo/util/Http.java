package com.example.bonree.xposeddemo.util;

import android.os.Build;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Http {
    public static class Builder{
        HttpURLConnection connection;

        Builder method(String url ,String method){
            try {
                URL u = new URL(url);
                connection = (HttpURLConnection) u.openConnection();
                connection.setRequestMethod(method);
            }catch (IOException e){
                e.printStackTrace();
            }
          return this;
        }

        Builder get(String url){
            return method(url,"GET");
        }

        Builder post(String url){
            return method(url,"POST");
        }

        Builder head(String key,String value){
            connection.addRequestProperty(key,value);
            return this;
        }
    }



    public static Builder builder(){

        return new Http.Builder();
    }



}
