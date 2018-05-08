/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 15.04.2018 11:52
 *
 *
 */

package tr.com.kuveytturk.android.sdk.services;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface GetServiceInterface {

    @GET("{endPoint}")
    Call<JsonObject> get(@Path("endPoint") String endPoint,
                         @Header("Content-Type") String contentType,
                         @Header("Authorization") String authorizationBearer,
                         @Header("Signature") String signature,
                         @Header("LanguageId") int languageId,
                         @QueryMap Map<String, Object> queryParams);

    @GET("{endPoint}")
    Call<JsonObject> get(@Path("endPoint") String endPoint,
                         @Header("Content-Type") String contentType,
                         @Header("Authorization") String authorizationBearer,
                         @Header("Signature") String signature,
                         @Header("LanguageId") int languageId);

    @GET("{endPoint}")
    Call<JsonObject> getWithDeviceId(@Path("endPoint") String endPoint,
                                     @Header("Content-Type") String contentType,
                                     @Header("Authorization") String authorizationBearer,
                                     @Header("Signature") String signature,
                                     @Header("LanguageId") int languageId,
                                     @Header("DeviceId") String deviceId);


    @GET("{endPoint}")
    Call<JsonObject> getWithDeviceId(@Path("endPoint") String endPoint,
                                     @Header("Content-Type") String contentType,
                                     @Header("Authorization") String authorizationBearer,
                                     @Header("Signature") String signature,
                                     @Header("LanguageId") int languageId,
                                     @Header("DeviceId") String deviceId,
                                     @QueryMap Map<String, Object> queryParams);


    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPI(@Path("endPoint") String endPoint,
                                      @Header("Content-Type") String contentType,
                                      @Header("LanguageId") int languageId);

    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPI(@Path("endPoint") String endPoint,
                                      @Header("Content-Type") String contentType,
                                      @Header("Signature") String signature,
                                      @Header("LanguageId") int languageId);

    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPI(@Path("endPoint") String endPoint,
                                      @Header("Content-Type") String contentType,
                                      @Header("Signature") String signature,
                                      @Header("LanguageId") int languageId,
                                      @QueryMap Map<String, Object> queryParams);

    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId);

    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("Signature") String signature,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId);

    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId,
                                                  @QueryMap Map<String, Object> queryParams);

    @GET("{endPoint}")
    Call<JsonObject> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("Signature") String signature,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId,
                                                  @QueryMap Map<String, Object> queryParams);

}
