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

import tr.com.kuveytturk.android.sdk.api.PostRequestFacade;
import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.receivers.PostResponseBroadcastReceiver;
import tr.com.kuveytturk.android.sdk.util.Constants;
import tr.com.kuveytturk.android.sdk.services.PostService;


public final class PostRequestHandler<T extends Activity & ResponseHandlingFacade> implements PostRequestFacade<T> {
    private T mCallerActivity;
    private PostResponseBroadcastReceiver<T> broadcastReceiver;
    private int mLanguageId;
    private String mDeviceId;

    public PostRequestHandler(T callerActivity) {
        this.mCallerActivity = callerActivity;
        broadcastReceiver = new PostResponseBroadcastReceiver<T>(mCallerActivity);
        LocalBroadcastManager.getInstance(mCallerActivity).registerReceiver(broadcastReceiver, new IntentFilter(Constants.KT_POST_SERVICE_MESSAGE));
        mLanguageId = 2;
        mDeviceId = null;
    }

    public PostRequestHandler(T callerActivity, int languageId) {
        this(callerActivity);
        mLanguageId = languageId;
    }

    public PostRequestHandler(T callerActivity, int languageId, String deviceId) {
        this(callerActivity, languageId);
        mDeviceId = deviceId;
    }


    @Override
    public void doPost(String endPoint,
                       String jsonBody,
                       String authorizationBearer,
                       String signature){
        Intent intent = new Intent(mCallerActivity, PostService.class);
        intent.putExtra("EndPoint", endPoint);
        intent.putExtra("JsonBody", jsonBody);
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
    public void doPost(String endPoint,
                       String jsonBody){
        Intent intent = new Intent(mCallerActivity, PostService.class);
        intent.putExtra("EndPoint", endPoint);
        intent.putExtra("JsonBody", jsonBody);
        intent.putExtra("IsPublicAPI", true);
        intent.putExtra("LanguageId", mLanguageId);
        if(mDeviceId != null && !mDeviceId.isEmpty()) {
            intent.putExtra("DeviceId", mDeviceId);
        }

        mCallerActivity.startService(intent);
    }

}
