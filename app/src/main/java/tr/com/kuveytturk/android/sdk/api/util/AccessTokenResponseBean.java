/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 17:50
 *
 * /
 */

package tr.com.kuveytturk.android.sdk.api.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Contains fields that are present in the response message that is sent back from the Kuveyt Türk
 * identity server for the corresponding access token related request that has been made earlier.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public final class AccessTokenResponseBean implements Serializable{

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("scope")
    @Expose
    private String scope;

    @SerializedName("expires_in")
    @Expose
    private int expiresIn;

    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("error_description")
    @Expose
    private String errorDescription;

    public AccessTokenResponseBean() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
