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


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface GetServiceInterface {

    @GET("{endPoint}")
    Call<ResponseBody> get(@Path("endPoint") String endPoint,
                           @Header("Content-Type") String contentType,
                           @Header("Authorization") String authorizationBearer,
                           @Header("Signature") String signature,
                           @Header("LanguageId") int languageId,
                           @QueryMap Map<String, Object> queryParams);

    @GET("{endPoint}")
    Call<ResponseBody> get(@Path("endPoint") String endPoint,
                          @Header("Content-Type") String contentType,
                          @Header("Authorization") String authorizationBearer,
                          @Header("Signature") String signature,
                          @Header("LanguageId") int languageId);

    @GET("{endPoint}")
    Call<ResponseBody> getWithDeviceId(@Path("endPoint") String endPoint,
                                     @Header("Content-Type") String contentType,
                                     @Header("Authorization") String authorizationBearer,
                                     @Header("Signature") String signature,
                                     @Header("LanguageId") int languageId,
                                     @Header("DeviceId") String deviceId);


    @GET("{endPoint}")
    Call<ResponseBody> getWithDeviceId(@Path("endPoint") String endPoint,
                                     @Header("Content-Type") String contentType,
                                     @Header("Authorization") String authorizationBearer,
                                     @Header("Signature") String signature,
                                     @Header("LanguageId") int languageId,
                                     @Header("DeviceId") String deviceId,
                                     @QueryMap Map<String, Object> queryParams);


    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPI(@Path("endPoint") String endPoint,
                                      @Header("Content-Type") String contentType,
                                      @Header("LanguageId") int languageId);

    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPI(@Path("endPoint") String endPoint,
                                      @Header("Content-Type") String contentType,
                                      @Header("Signature") String signature,
                                      @Header("LanguageId") int languageId);

    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPI(@Path("endPoint") String endPoint,
                                      @Header("Content-Type") String contentType,
                                      @Header("Signature") String signature,
                                      @Header("LanguageId") int languageId,
                                      @QueryMap Map<String, Object> queryParams);

    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId);

    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("Signature") String signature,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId);

    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId,
                                                  @QueryMap Map<String, Object> queryParams);

    @GET("{endPoint}")
    Call<ResponseBody> getFromPublicAPIWithDeviceId(@Path("endPoint") String endPoint,
                                                  @Header("Content-Type") String contentType,
                                                  @Header("Signature") String signature,
                                                  @Header("LanguageId") int languageId,
                                                  @Header("DeviceId") String deviceId,
                                                  @QueryMap Map<String, Object> queryParams);

}
