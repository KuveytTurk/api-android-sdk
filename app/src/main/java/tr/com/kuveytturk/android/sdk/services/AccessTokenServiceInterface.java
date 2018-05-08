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

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tr.com.kuveytturk.android.sdk.api.util.AccessTokenResponseBean;

public interface AccessTokenServiceInterface {

    @POST("{endPoint}")
    @FormUrlEncoded
    Call<AccessTokenResponseBean> requestAccessTokenWithCode(
            @Path("endPoint") String endPoint,
            @Field("client_id") String clientString,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri
    );

    @POST("{endPoint}")
    @FormUrlEncoded
    Call<AccessTokenResponseBean> requestAccessTokenWithRefreshToken(
            @Path("endPoint") String endPoint,
            @Field("client_id") String clientString,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("refresh_token") String refreshToken
    );

    @POST("{endPoint}")
    @FormUrlEncoded
    Call<AccessTokenResponseBean> revokeAccessToken(
            @Path("endPoint") String endPoint,
            @Field("client_id") String clientString,
            @Field("client_secret") String clientSecret,
            @Field("token") String accessToken,
            @Field("token_type_hint") String tokenTypeHint
    );

    @POST("{endPoint}")
    @FormUrlEncoded
    Call<AccessTokenResponseBean> revokeRefreshToken(
            @Path("endPoint") String endPoint,
            @Field("client_id") String clientString,
            @Field("client_secret") String clientSecret,
            @Field("token") String accessToken,
            @Field("token_type_hint") String tokenTypeHint
    );
}
