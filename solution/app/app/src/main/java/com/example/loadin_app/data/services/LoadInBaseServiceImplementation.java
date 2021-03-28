package com.example.loadin_app.data.services;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadInBaseServiceImplementation {
    protected Retrofit retrofitContext;
    protected String baseUrl;
    protected BaseServiceUrlProvider.Config currentConfig;

    public LoadInBaseServiceImplementation(BaseServiceUrlProvider.Config config){
        baseUrl = BaseServiceUrlProvider.getBaseUrl(config);
        currentConfig = config;
        retrofitContext = createRetrofitBuilder().build();

    }

    protected Retrofit.Builder createRetrofitBuilder(){

                return new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(createRetrofitClient().build());


    }

    protected OkHttpClient.Builder createRetrofitClient(){
        switch(currentConfig){
            case LOCAL:
            case ANDROID_SIM:
               return getAllTrustingClient();

            case PRODUCTION:
                return new OkHttpClient.Builder();

        }

        return null;
    }


    protected static OkHttpClient.Builder getAllTrustingClient(){
        //WARNING: this is extremely dangerous and should not really be used for a production environment

        try{
            TrustManager theManager = getAllTrustingTrustManager();
            SSLSocketFactory fact = installAllTrustingManager(theManager);
            OkHttpClient.Builder b = new OkHttpClient.Builder();
            b.sslSocketFactory(fact, (X509TrustManager)theManager); //set the socket factory
            b.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return b;
        }
        catch(Exception e){
            System.out.println(e);
        }

        return null;

    }

    private static SSLSocketFactory installAllTrustingManager(TrustManager allTrusting) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context =  SSLContext.getInstance("SSL");
        context.init(null, new TrustManager[]{allTrusting}, new SecureRandom());
        SSLSocketFactory fact = context.getSocketFactory();
        return fact;
    }
    private static TrustManager getAllTrustingTrustManager(){
        //WARNING: this is extremely dangerous and should not really be used for a production environment
        TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return tm;
    }

}
