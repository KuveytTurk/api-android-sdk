/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 17:10
 */

package tr.com.kuveytturk.android.sdk.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import tr.com.kuveytturk.android.sdk.api.util.QueryParameterBean;
import tr.com.kuveytturk.android.sdk.util.Constants;


public class GetService extends IntentService {
    private final static String CONTENT_TYPE = "application/json";

    public GetService() {
        super(Constants.KT_GET_SERVICE_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_API_ACCESS_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_ACCESS_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        String endPoint = intent.getStringExtra("EndPoint");
        boolean isPublicAPI = intent.getBooleanExtra("IsPublicAPI", false);

        GetServiceInterface apiService = retrofit.create(GetServiceInterface.class);
        Call<ResponseBody> call = null;

        if (!isPublicAPI) {
            String authorizationBearer = intent.getStringExtra("Authorization");
            String signature = intent.getStringExtra("Signature");
            int languageId = intent.getIntExtra("LanguageId", 2); //Default is 2 for the English language
            String deviceId = intent.getStringExtra("DeviceId");

            ArrayList<QueryParameterBean> queryParamList = null;
            try {
                queryParamList = intent.getParcelableArrayListExtra("QueryParams");
                if(queryParamList == null) { queryParamList = new ArrayList<>(); }
            } catch (Exception exp) {
                Log.i(Constants.KT_GET_SERVICE_TAG, "Error occured while unmarshalling the query parameter list in onHandleIntent method: " + exp.getMessage());
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + exp.getMessage());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
                return;
            }

            TreeMap<String, Object> queryParametersAsMap = new TreeMap<>();

            if (queryParamList != null && !queryParamList.isEmpty()) {

                for (QueryParameterBean queryParam : queryParamList) {
                    queryParametersAsMap.put(queryParam.getParamName(), queryParam.getParamValue());
                }

                //Make the web service request
                if (deviceId != null) {
                    call = apiService.getWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            authorizationBearer,
                            signature,
                            languageId,
                            deviceId,
                            queryParametersAsMap);
                } else {
                    call = apiService.get(
                            endPoint,
                            CONTENT_TYPE,
                            authorizationBearer,
                            signature,
                            languageId,
                            queryParametersAsMap);
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

            String authorizationBearer = intent.getStringExtra("Authorization");
            String signature = intent.getStringExtra("Signature");
            int languageId = intent.getIntExtra("LanguageId", 2); //Default is 2 for the English language
            String deviceId = intent.getStringExtra("DeviceId");

            ArrayList<QueryParameterBean> queryParamList = null;
            try {
                queryParamList = intent.getParcelableArrayListExtra("QueryParams");
                if(queryParamList == null) { queryParamList = new ArrayList<>(); }
            } catch (Exception exp) {
                Log.i(Constants.KT_GET_SERVICE_TAG, "Error occured while unmarshalling the query parameter list in onHandleIntent method: " + exp.getMessage());
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + exp.getMessage());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
                return;
            }

            Map<String, Object> queryParametersAsMap = new LinkedHashMap<>();


            if (queryParamList != null && !queryParamList.isEmpty()) {

                for (QueryParameterBean queryParam : queryParamList) {
                    queryParametersAsMap.put(queryParam.getParamName(), queryParam.getParamValue());
                }

                if (deviceId != null) {
                    call = apiService.getFromPublicAPIWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            signature,
                            languageId,
                            deviceId,
                            authorizationBearer,
                            queryParametersAsMap);
                } else {
                    call = apiService.getFromPublicAPI(
                            endPoint,
                            CONTENT_TYPE,
                            signature,
                            languageId,
                            authorizationBearer,
                            queryParametersAsMap);
                }
            } else { //queryParams does NOT exist
                if (deviceId != null) {
                    call = apiService.getFromPublicAPIWithDeviceId(
                            endPoint,
                            CONTENT_TYPE,
                            signature,
                            languageId,
                            deviceId,
                            authorizationBearer);
                } else {
                    call = apiService.getFromPublicAPI(
                            endPoint,
                            CONTENT_TYPE,
                            signature,
                            languageId,
                            authorizationBearer);
                }
            }
        }

        if (call != null) {
            Response<ResponseBody> backEndResponse;
            try {
                backEndResponse = call.execute();
            } catch (IOException e) {
                Log.i(Constants.KT_GET_SERVICE_TAG, "IOException occured while sending out the REST request in onHandleIntent method: " + e.getMessage());
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
                return;
            } catch (java.lang.IllegalArgumentException e) {
                Log.i(Constants.KT_GET_SERVICE_TAG, "IllegalArgumentException occured while sending out the REST request in onHandleIntent method: " + e.getMessage());
                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);
                return;
            }

            if (backEndResponse.isSuccessful()) {
                //Return the data to MainActivity
                String responseBodyAsString = "";
                try {
                    responseBodyAsString = backEndResponse.body().string();
                } catch (IOException e) {
                    Log.i(Constants.KT_GET_SERVICE_TAG, "IOException occured while fetching the response body onHandleIntent method: " + e.getMessage());
                    Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                    messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, "onHandleIntent: " + e.getMessage());
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                    broadcastManager.sendBroadcast(messageIntent);
                    return;
                }

                Intent messageIntent = new Intent(Constants.KT_GET_SERVICE_MESSAGE);
                messageIntent.putExtra(Constants.KT_GET_SERVICE_PAYLOAD, responseBodyAsString);
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastManager.sendBroadcast(messageIntent);

            } else {
                String errResponseBody = null;
                try {
                    errResponseBody = backEndResponse.errorBody().string();
                } catch (IOException e) {
                    Log.i(Constants.KT_GET_SERVICE_TAG, "IOException occured while processing the error message that is returned by the server in onHandleIntent method: " + e.getMessage());
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
