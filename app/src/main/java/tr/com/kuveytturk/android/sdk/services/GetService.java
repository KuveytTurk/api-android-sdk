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

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tr.com.kuveytturk.android.sdk.util.Constants;


public class GetService extends IntentService {
    private final static String CONTENT_TYPE = "application/json";

    public GetService() {
        super(Constants.KT_GET_SERVICE_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_ACCESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String endPoint = intent.getExtras().getString("EndPoint");
        boolean isPublicAPI = intent.getExtras().getBoolean("IsPublicAPI");

        GetServiceInterface apiService = retrofit.create(GetServiceInterface.class);
        Call<JsonObject> call = null;

        if (!isPublicAPI) {
            String authorizationBearer = intent.getExtras().getString("Authorization");
            String signature = intent.getExtras().getString("Signature");
            int languageId = intent.getExtras().getInt("LanguageId", 2); //Default is 2 for the English language
            String deviceId = intent.getExtras().getString("DeviceId", null);
            HashMap<String, Object> queryParams = (HashMap<String, Object>) intent.getSerializableExtra("QueryParams");

            if (queryParams != null && !queryParams.isEmpty()) {
                //Make the web service request
                if (deviceId != null) {
                    call = apiService.getWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            authorizationBearer,
                            signature,
                            languageId,
                            deviceId,
                            queryParams);
                } else {
                    call = apiService.get(
                            endPoint,
                            CONTENT_TYPE,
                            authorizationBearer,
                            signature,
                            languageId,
                            queryParams);
                }
            } else { //queryParams does NOT exist
                if (deviceId != null) {
                    call = apiService.getWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            authorizationBearer,
                            signature,
                            languageId,
                            deviceId);
                } else {
                    call = apiService.get(
                            endPoint,
                            CONTENT_TYPE,
                            authorizationBearer,
                            signature,
                            languageId);
                }
            }

        } else { //public API
            HashMap<String, Object> queryParams = (HashMap<String, Object>) intent.getSerializableExtra("QueryParams");
            int languageId = intent.getExtras().getInt("LanguageId", 2); //Default is 2 for the English language
            String deviceId = intent.getExtras().getString("DeviceId", null);
            String signature = intent.getExtras().getString("Signature");

            if (queryParams != null && !queryParams.isEmpty()) {

                if (deviceId != null) {
                    call = apiService.getFromPublicAPIWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            signature,
                            languageId,
                            deviceId,
                            queryParams);
                } else {
                    call = apiService.getFromPublicAPI(
                            endPoint,
                            CONTENT_TYPE,
                            signature,
                            languageId,
                            queryParams);
                }
            } else { //queryParams does NOT exist
                if (deviceId != null) {
                    call = apiService.getFromPublicAPIWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            languageId,
                            deviceId);
                } else {
                    call = apiService.getFromPublicAPI(
                            endPoint,
                            CONTENT_TYPE,
                            languageId);
                }
            }
        }

        if (call != null) {

            Response<JsonObject> backEndResponse;

            try {
                backEndResponse = call.execute();
            } catch (IOException e) {
                Log.i(Constants.KT_GET_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
            messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            broadcastManager.sendBroadcast(messageIntent);
            return;
        } catch (java.lang.IllegalArgumentException e){
            Log.i(Constants.KT_GET_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
            Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
            messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            broadcastManager.sendBroadcast(messageIntent);
            return;
        }

            if (backEndResponse.isSuccessful()) {
                //Return the data to MainActivity
                JsonObject responseBody = backEndResponse.body();
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, responseBody.toString());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
            } else {
                String errResponseBody = null;
                try {
                    errResponseBody = backEndResponse.errorBody().string();
                } catch (IOException e) {
                    Log.i(Constants.KT_GET_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
                    Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                    messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                    broadcastManager.sendBroadcast(messageIntent);
                    return;
                }
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, errResponseBody);
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
            }

        }
    }
}
