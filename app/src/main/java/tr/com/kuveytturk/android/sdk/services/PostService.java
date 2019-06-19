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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tr.com.kuveytturk.android.sdk.util.Constants;


public class PostService extends IntentService {
    private final static String CONTENT_TYPE = "application/json";

    public PostService() {
        super(Constants.KT_POST_SERVICE_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_ACCESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String endPoint = intent.getExtras().getString("EndPoint");
        boolean isPublicAPI = intent.getExtras().getBoolean("IsPublicAPI");

        String strJsonBody = intent.getExtras().getString("JsonBody");
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonBody = jsonParser.parse(strJsonBody).getAsJsonObject();

        int languageId = intent.getExtras().getInt("LanguageId", 2); //Default is 2 for the English language
        String deviceId = intent.getExtras().getString("DeviceId", null);

        String authorizationBearer = intent.getExtras().getString("Authorization");
        String signature = intent.getExtras().getString("Signature");

        //Make the web service request
        PostServiceInterface apiService = retrofit.create(PostServiceInterface.class);
        Call<JsonObject> call = null;

        if(!isPublicAPI) {
            call = apiService.post(
                    endPoint,
                    jsonBody,
                    CONTENT_TYPE,
                    authorizationBearer,
                    signature,
                    languageId,
                    deviceId);
        } else {
            call = apiService.post(
                    endPoint,
                    jsonBody,
                    CONTENT_TYPE,
                    languageId,
                    deviceId);
        }
        Response<JsonObject> backEndResponse;

        if(call != null) {
            try {
                backEndResponse = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(Constants.KT_POST_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
                Intent messageIntent = new Intent(Constants.KT_POST_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_POST_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
                return;
            }

            if (backEndResponse.isSuccessful()) {
                //Return the data to MainActivity
                String responseBody = backEndResponse.body().toString();
                Intent messageIntent = new Intent(Constants.KT_POST_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_POST_SERVICE_PAYLOAD, responseBody);
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
                broadcastManager.sendBroadcast(messageIntent);
            } else {
                String errResponseBody = null;
                try {
                    errResponseBody = backEndResponse.errorBody().string();
                } catch (IOException e) {
                    Log.i(Constants.KT_POST_SERVICE_TAG, "onHandleIntent: " + e.getMessage());
                    Intent messageIntent = new Intent(Constants.KT_POST_SERVICE_MESSAGE);
                    messageIntent.putExtra(Constants.KT_POST_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                    broadcastManager.sendBroadcast(messageIntent);
                    return;
                }
                Intent messageIntent = new Intent(Constants.KT_POST_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_POST_SERVICE_PAYLOAD, errResponseBody);
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
                broadcastManager.sendBroadcast(messageIntent);
            }
        }
    }
}
