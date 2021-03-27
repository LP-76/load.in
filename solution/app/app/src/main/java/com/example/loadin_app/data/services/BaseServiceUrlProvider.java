package com.example.loadin_app.data.services;

public class BaseServiceUrlProvider {
    public enum Config{
        LOCAL,
        ANDROID_SIM,
        PRODUCTION
    }

    public static String getBaseUrl(Config config){
        switch(config){
            case LOCAL:
                return "https://localhost:9000/";
            case ANDROID_SIM:
                return "https://10.0.2.2:9000/";

        }
        return null;
    }

    public static Config getCurrentConfig(){
        return Config.ANDROID_SIM; //TODO: wire this up to something else
    }

}
