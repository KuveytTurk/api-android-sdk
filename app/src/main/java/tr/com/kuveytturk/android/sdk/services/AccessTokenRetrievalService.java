/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
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

import java.io.IOException;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_ACCESS_TOKEN_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
            String clientId = intent.getExtras().getString("ClientId");
            String clientSecret = intent.getExtras().getString("ClientSecret");
            String grantType = "authorization_code";
            String code = intent.getExtras().getString("Code");
            String redirectUri = intent.getExtras().getString("RedirectUri");

            //Make the web service request
            apiService = retrofit.create(AccessTokenServiceInterface.class);
            call = apiService.requestAccessTokenWithCode(
                    Constants.ACCESS_TOKEN_ENDPOINT,
                    clientId,
                    clientSecret,
                    grantType,
                    code,
                    redirectUri
            );
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
            String errResponseBody = null;
            try {
                errResponseBody = backEndResponse.errorBody().string();
            } catch (IOException e) {
                Log.i(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
                return;
            }
            Intent messageIntent = new Intent(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE);
            messageIntent.putExtra(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD, errResponseBody);
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
            broadcastManager.sendBroadcast(messageIntent);
        }
    }
}
