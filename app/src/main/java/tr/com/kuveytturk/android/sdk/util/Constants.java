/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 17:20
 *
 *
 */

package tr.com.kuveytturk.android.sdk.util;


public class Constants {

    public static String BASE_AUTHORIZATION_URL = "https://idprep.kuveytturk.com.tr/";
    public static String AUTHORIZATION_ENDPOINT = "api/connect/authorize";

    public static String BASE_ACCESS_TOKEN_URL = "https://idprep.kuveytturk.com.tr/";
    public static String ACCESS_TOKEN_ENDPOINT = "api/connect/token";

    public static String BASE_API_ACCESS_URL = "https://apitest.kuveytturk.com.tr/prep/"; //"http://172.31.236.73:92/";

    public static final String KT_GET_SERVICE_TAG = "KTGetService";
    public static final String KT_GET_SERVICE_MESSAGE = "KTWebAPIGetServiceMessage";
    public static final String KT_GET_SERVICE_PAYLOAD = "KTWebAPIGetServicePayload";

    public static final String KT_POST_SERVICE_TAG = "KTPostService";
    public static final String KT_POST_SERVICE_MESSAGE = "KTWebAPIPostServiceMessage";
    public static final String KT_POST_SERVICE_PAYLOAD = "KTWebAPIPostServicePayload";

    public static final String KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_TAG = "KTATRService";
    public static final String KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_MESSAGE = "KTWebAPIATRMessage";
    public static final String KT_ACCESS_TOKEN_RETRIEVAL_SERVICE_PAYLOAD = "KTWebAPIATRPayload";

    public static void setAccessProperties(String baseAuthorizationUrl,
                                           String authorizationEndPoint,
                                           String baseAccessTokenUrl,
                                           String accessTokenEndpoint,
                                           String apiAccessUrl){
        BASE_AUTHORIZATION_URL = baseAuthorizationUrl;
        AUTHORIZATION_ENDPOINT = authorizationEndPoint;
        BASE_ACCESS_TOKEN_URL = baseAccessTokenUrl;
        ACCESS_TOKEN_ENDPOINT = accessTokenEndpoint;
        BASE_API_ACCESS_URL = apiAccessUrl;//"https://apitest.kuveytturk.com.tr/prep/";
    }
}
