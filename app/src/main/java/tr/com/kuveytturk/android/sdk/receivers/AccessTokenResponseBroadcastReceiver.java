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

package tr.com.kuveytturk.android.sdk.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.api.util.AccessTokenResponseBean;
import tr.com.kuveytturk.android.sdk.util.Constants;

public class AccessTokenResponseBroadcastReceiver<T extends Activity & ResponseHandlingFacade> extends BroadcastReceiver {
    private T mResponseAcceptorActivity;

    public AccessTokenResponseBroadcastReceiver(T responseAcceptorActivity){
        mResponseAcceptorActivity = responseAcceptorActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AccessTokenResponseBean responseBean = (AccessTokenResponseBean)intent.getSerializableExtra(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD);
        mResponseAcceptorActivity.onAccessTokenReceived(responseBean);
    }
}
