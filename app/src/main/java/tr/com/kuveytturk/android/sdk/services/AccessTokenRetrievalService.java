/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 17:10
 *
 *
 */

package tr.com.kuveytturk.android.sdk.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tr.com.kuveytturk.android.sdk.api.util.AccessTokenResponseBean;
import tr.com.kuveytturk.android.sdk.util.Constants;


public class AccessTokenRetrievalService extends IntentService{

    public AccessTokenRetrievalService() {
        super(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_TAG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, startId, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_ACCESS_TOKEN_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .callTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_ACCESS_TOKEN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        Call<AccessTokenResponseBean> call;
        AccessTokenServiceInterface apiService;
        boolean isForRefreshToken = intent.getExtras().getBoolean("IsForRefreshToken");
        boolean isForRefreshTokenRevoke = intent.getExtras().getBoolean("IsForRefreshTokenRevoke");
        boolean isForAccessTokenRevoke = intent.getExtras().getBoolean("IsForAccessTokenRevoke");

        if(isForAccessTokenRevoke) {
            String clientId = intent.getExtras().getString("ClientId");
            String clientSecret = intent.getExtras().getString("ClientSecret");
            String token = intent.getExtras().getString("Token");
            String tokenTypeHint = "access_token";

            //Make the web service request
            apiService = retrofit.create(AccessTokenServiceInterface.class);
            call = apiService.revokeAccessToken(Constants.ACCESS_TOKEN_ENDPOINT, clientId, clientSecret, token, tokenTypeHint);
        }
        else if(isForRefreshTokenRevoke) {
            String clientId = intent.getExtras().getString("ClientId");
            String clientSecret = intent.getExtras().getString("ClientSecret");
            String token = intent.getExtras().getString("Token");
            String tokenTypeHint = "refresh_token";

            //Make the web service request
            apiService = retrofit.create(AccessTokenServiceInterface.class);
            call = apiService.revokeRefreshToken(Constants.ACCESS_TOKEN_ENDPOINT, clientId, clientSecret, token, tokenTypeHint);
        }
        else if(isForRefreshToken) {
            String clientId = intent.getExtras().getString("ClientId");
            String clientSecret = intent.getExtras().getString("ClientSecret");
            String grantType = "refresh_token";
            String refreshToken = intent.getExtras().getString("RefreshToken");
            apiService = retrofit.create(AccessTokenServiceInterface.class);
            call = apiService.requestAccessTokenWithRefreshToken(
                    Constants.ACCESS_TOKEN_ENDPOINT,
                    clientId,
                    clientSecret,
                    grantType,
                    refreshToken);
        }
        else {
            String grantType = intent.getExtras().getString("GrantType");
            if(grantType == null && grantType.isEmpty()) { grantType = Constants.AUTHORIZATION_CODE; }

            if(grantType.equalsIgnoreCase(Constants.AUTHORIZATION_CODE)) {

                String clientId = intent.getExtras().getString("ClientId");
                String clientSecret = intent.getExtras().getString("ClientSecret");
                String code = intent.getExtras().getString("Code");
                String redirectUri = intent.getExtras().getString("RedirectUri");

                //Make the web service request
                apiService = retrofit.create(AccessTokenServiceInterface.class);
                call = apiService.requestAccessTokenWithAuthorizationCode(
                        Constants.ACCESS_TOKEN_ENDPOINT,
                        clientId,
                        clientSecret,
                        grantType,
                        code,
                        redirectUri
                );
            } else if(grantType.equalsIgnoreCase(Constants.CLIENT_CREDENTIALS)) {

                String clientId = intent.getExtras().getString("ClientId");
                String clientSecret = intent.getExtras().getString("ClientSecret");
                String scope = intent.getExtras().getString("Scope");

                //Make the web service request
                apiService = retrofit.create(AccessTokenServiceInterface.class);
                call = apiService.requestAccessTokenWithClientCredentials(
                        Constants.ACCESS_TOKEN_ENDPOINT,
                        clientId,
                        clientSecret,
                        grantType,
                        scope
                );
            }
            else {
                Intent messageIntent = new Intent(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE);
                messageIntent.
                        putExtra(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD,
                                "GRANT_TYPE parameter is missing in the request!");
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
                broadcastManager.sendBroadcast(messageIntent);
                return;
            }
        }

        Response<AccessTokenResponseBean> backEndResponse;

        try {
            backEndResponse = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
            return;
        }

        if (backEndResponse.isSuccessful()) {
            //Return the data to MainActivity
            AccessTokenResponseBean responseBody = backEndResponse.body();
            Intent messageIntent = new Intent(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE);
            messageIntent.putExtra(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD, responseBody);
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
            broadcastManager.sendBroadcast(messageIntent);
        } else {
            AccessTokenResponseBean errResponseBody = null;
            try {
                String responseStr = backEndResponse.errorBody().string();
                errResponseBody = gson.fromJson(responseStr, AccessTokenResponseBean.class);
            } catch (IOException e) {
                Log.i(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
                Intent messageIntent = new Intent(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE);
                errResponseBody = new AccessTokenResponseBean();
                errResponseBody.setError("Error occurred while retrieving access token response!");
                errResponseBody.setErrorDescription(e.getLocalizedMessage());
                messageIntent.putExtra(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD, errResponseBody);
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
                broadcastManager.sendBroadcast(messageIntent);
                return;
            }
            Intent messageIntent = new Intent(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE);
            messageIntent.putExtra(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD, errResponseBody);
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
            broadcastManager.sendBroadcast(messageIntent);
        }
    }
}
