/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 03.05.2018 15:36
 *
 * /
 */

package tr.com.kuveytturk.android.sdk.api.util;

/**
 * Container class to hold configuration data to access the Kuveyt Türk web service bakend
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */

public final class AccessConfigurationBean implements java.io.Serializable {
    public static final String DEFAULT_BASE_AUTHORIZATION_URL = "https://idprep.kuveytturk.com.tr/";
    public static final String DEFAULT_AUTHORIZATION_ENDPOINT = "api/connect/authorize";
    public static final String DEFAULT_BASE_ACCESS_TOKEN_URL = "https://idprep.kuveytturk.com.tr/";
    public static final String DEFAULT_ACCESS_TOKEN_ENDPOINT = "api/connect/token";
    public static final String DEFAULT_BASE_API_ACCESS_URL = "https://apitest.kuveytturk.com.tr/prep/";

    /**
     * Holds the base backend URL to where the authorization requests are sent
     * (e.g. https://idprep.kuveytturk.com.tr/)
     */
    private String baseAuthorizationUrl;

    /**
     * Holds the authorization endpoint at which the authorization requests are targeted
     * (e.g api/connect/authorize).
     */
    private String authorizationEndPoint;

    /**
     * Holds the backend URL from where the access token is to be retrieved
     * (e.g. https://idprep.kuveytturk.com.tr/)
     */
    private String baseAccessTokenUrl;

    /**
     * Holds the endpoint to where the access token requests are sent
     * (e.g api/connect/authorize).
     */
    private String accessTokenEndPoint;

    /**
     * Holds the backend URL to where the GET/POST requests are sent
     * (e.g. https://apitest.kuveytturk.com.tr/prep/)
     */
    private String apiAccessUrl;

    /**
     * Constructs the configuration bean with default parameters
     */
    public AccessConfigurationBean() {
        this.baseAuthorizationUrl = DEFAULT_BASE_AUTHORIZATION_URL;
        this.authorizationEndPoint = DEFAULT_AUTHORIZATION_ENDPOINT;
        this.baseAccessTokenUrl = DEFAULT_BASE_ACCESS_TOKEN_URL;
        this.accessTokenEndPoint = DEFAULT_ACCESS_TOKEN_ENDPOINT;
        this.apiAccessUrl = DEFAULT_BASE_API_ACCESS_URL;
    }

    /**
     * Constructs the configuration bean with custom parameters
     *
     * @param baseAuthorizationUrl Holds the base backend URL to where the authorization
     *                             requests are sent
     * @param authorizationEndPoint Holds the authorization endpoint to where the authorization
     *                              requests are fed
     * @param baseAccessTokenUrl Holds the backend URL from where the access token is to be
     *                           retrieved
     * @param accessTokenEndpoint  Holds the endpoint to where the access token requests are sent
     * @param apiAccessUrl The base64 encoded signature by using SHA256/RSA
     */
    public AccessConfigurationBean(String baseAuthorizationUrl,
                                   String authorizationEndPoint,
                                   String baseAccessTokenUrl,
                                   String accessTokenEndpoint,
                                   String apiAccessUrl) {
        this.baseAuthorizationUrl = baseAuthorizationUrl;
        this.authorizationEndPoint = authorizationEndPoint;
        this.baseAccessTokenUrl = baseAccessTokenUrl;
        this.accessTokenEndPoint = accessTokenEndpoint;
        this.apiAccessUrl = apiAccessUrl;
    }

    public String getBaseAuthorizationUrl() {
        return baseAuthorizationUrl;
    }

    public void setBaseAuthorizationUrl(String baseAuthorizationUrl) {
        this.baseAuthorizationUrl = baseAuthorizationUrl;
    }

    public String getAuthorizationEndPoint() {
        return authorizationEndPoint;
    }

    public void setAuthorizationEndPoint(String authorizationEndPoint) {
        this.authorizationEndPoint = authorizationEndPoint;
    }

    public String getBaseAccessTokenUrl() {
        return baseAccessTokenUrl;
    }

    public void setBaseAccessTokenUrl(String baseAccessTokenUrl) {
        this.baseAccessTokenUrl = baseAccessTokenUrl;
    }

    public String getAccessTokenEndpoint() {
        return accessTokenEndPoint;
    }

    public void setAccessTokenEndpoint(String accessTokenEndpoint) {
        this.accessTokenEndPoint = accessTokenEndpoint;
    }

    public String getApiAccessUrl() {
        return apiAccessUrl;
    }

    public void setApiAccessUrl(String apiAccessUrl) {
        this.apiAccessUrl = apiAccessUrl;
    }

}
