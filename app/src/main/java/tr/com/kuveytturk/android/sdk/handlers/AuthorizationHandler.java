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

package tr.com.kuveytturk.android.sdk.handlers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import tr.com.kuveytturk.android.sdk.api.AuthorizationFacade;
import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.util.Constants;


public final class AuthorizationHandler<T extends Activity & ResponseHandlingFacade> implements AuthorizationFacade<T> {
    private T mCallerActivity;

    public AuthorizationHandler(T callerActivity) {
        this.mCallerActivity = callerActivity;
    }

    @Override
    public void requestAuthorization(String clientId, String responseType, String redirectURI, String scope) {
        requestAuthorization(clientId, responseType, redirectURI, scope,null);
    }

    @Override
    public void requestAuthorization(String clientId, String responseType, String redirectURI, String scope, String state) {
        StringBuilder authURLBuilder = new StringBuilder();

        authURLBuilder.append(Constants.BASE_AUTHORIZATION_URL).
                append(Constants.AUTHORIZATION_ENDPOINT).
                append("?").
                append("&client_id=").append(clientId).
                append("&scope=").append(scope).
                append("&response_type=").append(responseType).
                append("&redirect_uri=").append(redirectURI);

        if((state != null) && !state.isEmpty()){
            authURLBuilder.append("&state=").append(state);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authURLBuilder.toString()));
        mCallerActivity.startActivity(intent);
    }

    @Override
    public void pollAuthorizationResponse(String redirectUri){
        Uri uri = mCallerActivity.getIntent().getData();
        if(uri != null && uri.toString().startsWith((redirectUri))) {
            String code = uri.getQueryParameter("code");
            String error = uri.getQueryParameter("error");
            String state = uri.getQueryParameter("state");
            mCallerActivity.onAuthorizationCodeReceived(code, state, error);
        }
    }
}
