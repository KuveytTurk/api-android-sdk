/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 15.04.2018 11:51
 *
 *
 */

package tr.com.kuveytturk.android.sdk.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface PostServiceInterface{

    @POST("{endPoint}")
    Call<JsonObject> post(@Path("endPoint") String endPoint,
                          @Body JsonObject jsonBody,
                          @Header("Content-Type") String contentType,
                          @Header("Authorization") String authorizationBearer,
                          @Header("Signature") String signature);

    @POST("{endPoint}")
    Call<JsonObject> post(@Path("endPoint") String endPoint,
                          @Body JsonObject jsonBody,
                          @Header("Content-Type") String contentType,
                          @Header("Authorization") String authorizationBearer,
                          @Header("Signature") String signature,
                          @Header("LanguageId") int languageId,
                          @Header("DeviceId") String deviceId);

    @POST("{endPoint}") //FOR PUBLIC API
    Call<JsonObject> post(@Path("endPoint") String endPoint,
                          @Body JsonObject jsonBody,
                          @Header("Content-Type") String contentType,
                          @Header("LanguageId") int languageId,
                          @Header("DeviceId") String deviceId);
}
