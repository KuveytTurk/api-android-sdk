/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 17:50
 *
 *
 */

package tr.com.kuveytturk.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OAuthRequestBean implements Serializable {
    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("scope")
    private String scope;

    @SerializedName("response_type")
    private String responseType;

    @SerializedName("redirect_uri")
    private String redirectUri;

    @SerializedName("username")
    private String userName;

    public OAuthRequestBean(){}


    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String theClientSecret) {
        this.clientSecret = theClientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String theClientId) {
        this.clientId = theClientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String theScope) {
        this.scope = theScope;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String theResponseType) {
        this.responseType = theResponseType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String theRedirectUri) {
        this.redirectUri = theRedirectUri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
