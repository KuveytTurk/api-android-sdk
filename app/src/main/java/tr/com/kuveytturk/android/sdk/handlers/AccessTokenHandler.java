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
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import tr.com.kuveytturk.android.sdk.api.AccessTokenFacade;
import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.receivers.AccessTokenResponseBroadcastReceiver;
import tr.com.kuveytturk.android.sdk.services.AccessTokenRetrievalService;
import tr.com.kuveytturk.android.sdk.util.Constants;


public class AccessTokenHandler<T extends Activity & ResponseHandlingFacade>  implements AccessTokenFacade<T> {
    private T mCallerActivity;
    private AccessTokenResponseBroadcastReceiver<T> broadcastReceiver;

    public AccessTokenHandler(T callerActivity) {
        this.mCallerActivity = callerActivity;
        broadcastReceiver = new AccessTokenResponseBroadcastReceiver<T>(mCallerActivity);
        LocalBroadcastManager.getInstance(mCallerActivity).registerReceiver(broadcastReceiver, new IntentFilter(Constants.KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE));
    }


    @Override
    public void requestAccessTokenWithAuthorizationCode(String clientId,
                                                        String clientSecret,
                                                        String code,
                                                        String redirectUri){
        Intent intent = new Intent(mCallerActivity, AccessTokenRetrievalService.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("ClientSecret", clientSecret);
        intent.putExtra("Code", code);
        intent.putExtra("RedirectUri", redirectUri);
        intent.putExtra("GrantType", Constants.AUTHORIZATION_CODE);
        intent.putExtra("IsForRefreshToken", false);
        intent.putExtra("IsForAccessTokenRevoke", false);
        intent.putExtra("IsForRefreshTokenRevoke", false);

        mCallerActivity.startService(intent);
    }

    @Override
    public void requestAccessTokenWithClientCredentials(String clientId,
                                                        String clientSecret,
                                                        String scope){
        Intent intent = new Intent(mCallerActivity, AccessTokenRetrievalService.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("ClientSecret", clientSecret);
        intent.putExtra("Scope", scope);
        intent.putExtra("GrantType", Constants.CLIENT_CREDENTIALS);
        intent.putExtra("IsForRefreshToken", false);
        intent.putExtra("IsForAccessTokenRevoke", false);
        intent.putExtra("IsForRefreshTokenRevoke", false);

        mCallerActivity.startService(intent);
    }

    @Override
    public void requestAccessTokenWithRefreshToken(String clientId, String clientSecret, String refreshToken){
        Intent intent = new Intent(mCallerActivity, AccessTokenRetrievalService.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("ClientSecret", clientSecret);
        intent.putExtra("RefreshToken", refreshToken);
        intent.putExtra("IsForRefreshToken", true);
        intent.putExtra("IsForAccessTokenRevoke", false);
        intent.putExtra("IsForRefreshTokenRevoke", false);

        mCallerActivity.startService(intent);
    }

    @Override
    public void revokeAccessToken(String clientId, String clientSecret, String accessToken){
        Intent intent = new Intent(mCallerActivity, AccessTokenRetrievalService.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("ClientSecret", clientSecret);
        intent.putExtra("Token", accessToken);
        intent.putExtra("IsForAccessTokenRevoke", true);
        intent.putExtra("IsForRefreshTokenRevoke", false);
        intent.putExtra("IsForRefreshToken", false);

        mCallerActivity.startService(intent);
    }

    @Override
    public void revokeRefreshToken(String clientId, String clientSecret, String refreshToken){
        Intent intent = new Intent(mCallerActivity, AccessTokenRetrievalService.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("ClientSecret", clientSecret);
        intent.putExtra("Token", refreshToken);
        intent.putExtra("IsForRefreshTokenRevoke", true);
        intent.putExtra("IsForAccessTokenRevoke", false);
        intent.putExtra("IsForRefreshToken", false);

        mCallerActivity.startService(intent);
    }
}
