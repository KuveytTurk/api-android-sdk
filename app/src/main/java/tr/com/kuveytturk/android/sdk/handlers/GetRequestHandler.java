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

package tr.com.kuveytturk.android.sdk.handlers;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;

import tr.com.kuveytturk.android.sdk.api.GetRequestFacade;
import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.receivers.GetResponseBroadcastReceiver;
import tr.com.kuveytturk.android.sdk.util.Constants;
import tr.com.kuveytturk.android.sdk.services.GetService;


public final class GetRequestHandler<T extends Activity & ResponseHandlingFacade> implements GetRequestFacade<T> {
    private T mCallerActivity;
    private GetResponseBroadcastReceiver<T> broadcastReceiver;
    private int mLanguageId;
    private String mDeviceId;

    public GetRequestHandler(T callerActivity) {
        this.mCallerActivity = callerActivity;
        broadcastReceiver = new GetResponseBroadcastReceiver<T>(mCallerActivity);
        LocalBroadcastManager.getInstance(mCallerActivity).registerReceiver(broadcastReceiver, new IntentFilter(Constants.KT_GET_SERVICE_MESSAGE));
        mLanguageId = 2;
        mDeviceId = null;
    }

    public GetRequestHandler(T callerActivity,
                               int languageId) {
        this(callerActivity);
        mLanguageId = languageId;
    }

    public GetRequestHandler(T callerActivity,
                               int languageId,
                               String deviceId) {
        this(callerActivity, languageId);
        mDeviceId = deviceId;
    }


    @Override
    public void doGet(String endPoint,
                      HashMap<String, Object> queryParams,
                      String authorizationBearer,
                      String signature) {
        Intent intent = new Intent(mCallerActivity, GetService.class);
        intent.putExtra("EndPoint", endPoint);
        intent.putExtra("Authorization", authorizationBearer);
        intent.putExtra("Signature", signature);
        intent.putExtra("QueryParams", queryParams);
        intent.putExtra("IsPublicAPI", false);
        intent.putExtra("LanguageId", mLanguageId);

        if(mDeviceId != null && !mDeviceId.isEmpty()) {
            intent.putExtra("DeviceId", mDeviceId);
        }

        mCallerActivity.startService(intent);

    }

    @Override
    public void doGet(String endPoint,
                      HashMap<String, Object> queryParams) {
        Intent intent = new Intent(mCallerActivity, GetService.class);
        intent.putExtra("EndPoint", endPoint);
        intent.putExtra("QueryParams", queryParams);
        intent.putExtra("IsPublicAPI", true);
        intent.putExtra("LanguageId", mLanguageId);

        if(mDeviceId != null && !mDeviceId.isEmpty()) {
            intent.putExtra("DeviceId", mDeviceId);
        }

        mCallerActivity.startService(intent);
    }

    @Override
    public void doGet(String endPoint,
                      String authorizationBearer,
                      String signature) {
        Intent intent = new Intent(mCallerActivity, GetService.class);
        intent.putExtra("EndPoint", endPoint);
        intent.putExtra("Authorization", authorizationBearer);
        intent.putExtra("Signature", signature);
        intent.putExtra("IsPublicAPI", false);
        intent.putExtra("LanguageId", mLanguageId);

        if(mDeviceId != null && !mDeviceId.isEmpty()) {
            intent.putExtra("DeviceId", mDeviceId);
        }

        mCallerActivity.startService(intent);

    }

    @Override
    public void doGet(String endPoint) {
        Intent intent = new Intent(mCallerActivity, GetService.class);
        intent.putExtra("EndPoint", endPoint);
        intent.putExtra("IsPublicAPI", true);
        intent.putExtra("LanguageId", mLanguageId);

        if(mDeviceId != null && !mDeviceId.isEmpty()) {
            intent.putExtra("DeviceId", mDeviceId);
        }

        mCallerActivity.startService(intent);
    }
}
