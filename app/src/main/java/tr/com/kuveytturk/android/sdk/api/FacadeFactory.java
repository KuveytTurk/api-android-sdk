/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 */

package tr.com.kuveytturk.android.sdk.api;

import android.app.Activity;

import tr.com.kuveytturk.android.sdk.api.util.AccessConfigurationBean;
import tr.com.kuveytturk.android.sdk.handlers.AccessTokenHandler;
import tr.com.kuveytturk.android.sdk.handlers.AuthorizationHandler;
import tr.com.kuveytturk.android.sdk.handlers.GetRequestHandler;
import tr.com.kuveytturk.android.sdk.handlers.PostRequestHandler;
import tr.com.kuveytturk.android.sdk.util.Constants;

/**
 * Provides implementations for the API routines to create facade instancees (i.e. handles)
 * for accessing to the Kuveyt Turk web service backend. The generic type T extends the class
 * android.app.Activity and implements the interface ResponseHandlingFacade defined in the package
 * tr.com.kuveytturk.android.sdk.api. It represents the type of the Activity object that creates an
 * instance of this class.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public final class FacadeFactory <T extends Activity & ResponseHandlingFacade> implements FacadeFactoryInterface<T> {

    /**
     * Represents the language type in which back-end reşated messages appear.
     */
    private LANGUAGE_TYPE mLanguage;

    /**
     * The id of the device that makes the backend requests
     */
    private String mDeviceId;

    /**
     * This main Activity object that utilizes this FacadeFactory.
     * It is of generic type T which extends class android.app.Activity
     * and implements class ResponseHandlingFacade defined in the package
     * tr.com.kuveytturk.android.sdk.api
     */
    private T mCallerActivity;

    /**
     * The variable that holds the configuration to access the Kuveyt Türk
     * web service backend
     */
    private AccessConfigurationBean mAccessConfiguration;

    /**
     * Constructs an instance of class FacadeFactory
     *
     * @param callerActivity An instance of generic type T which extends class android.app.Activity
     *                       and implements class ResponseHandlingFacade defined in the package
     *                       tr.com.kuveytturk.android.sdk.api.
     */
    public FacadeFactory(T callerActivity) {
        mCallerActivity = callerActivity;
        mAccessConfiguration = new AccessConfigurationBean();
        Constants.setAccessProperties(
                mAccessConfiguration.getBaseAuthorizationUrl(),
                mAccessConfiguration.getAuthorizationEndPoint(),
                mAccessConfiguration.getBaseAccessTokenUrl(),
                mAccessConfiguration.getAccessTokenEndpoint(),
                mAccessConfiguration.getApiAccessUrl());
        mLanguage = LANGUAGE_TYPE.ENGLISH;
        mDeviceId = null;
    }

    /**
     * Constructs an instance of class FacadeFactory
     *
     * @param callerActivity An instance of generic type T which extends class android.app.Activity
     *                       and implements class ResponseHandlingFacade defined in the package
     *                       tr.com.kuveytturk.android.sdk.api.
     * @param accessConfiguration An instance of class AccessConfigurationBean  that is defined in
     *                            the package tr.com.kuveytturk.android.sdk.api.util.
     */
    public FacadeFactory(T callerActivity,
                         AccessConfigurationBean accessConfiguration){
        mCallerActivity = callerActivity;
        mAccessConfiguration = accessConfiguration;
        Constants.setAccessProperties(mAccessConfiguration.getBaseAuthorizationUrl(),
                                      mAccessConfiguration.getAuthorizationEndPoint(),
                                      mAccessConfiguration.getBaseAccessTokenUrl(),
                                      mAccessConfiguration.getAccessTokenEndpoint(),
                                      mAccessConfiguration.getApiAccessUrl());
        mLanguage = LANGUAGE_TYPE.ENGLISH;
        mDeviceId = null;
    }

    /**
     * Constructs an instance of class FacadeFactory
     *
     * @param callerActivity An instance of generic type T which extends class android.app.Activity
     *                       and implements class ResponseHandlingFacade defined in the package
     *                       tr.com.kuveytturk.android.sdk.api.
     * @param accessConfiguration An instance of class AccessConfigurationBean  that is defined in
     *                            the package tr.com.kuveytturk.android.sdk.api.util.
     * @param languageType An enum of type LANGUAGE_TYPE that is signaling the type of the language
     *                     in which the communicaiton with the Kuveyt Türk backend is handled for
     *                     human readable messages.
     * @param deviceId  The id of the device from where the request will be being sent to the
     *                  backend.
     */
    public FacadeFactory(T callerActivity,
                         AccessConfigurationBean accessConfiguration,
                         LANGUAGE_TYPE languageType,
                         String deviceId){
        mCallerActivity = callerActivity;
        mAccessConfiguration = accessConfiguration;
        Constants.setAccessProperties(mAccessConfiguration.getBaseAuthorizationUrl(),
                mAccessConfiguration.getAuthorizationEndPoint(),
                mAccessConfiguration.getBaseAccessTokenUrl(),
                mAccessConfiguration.getAccessTokenEndpoint(),
                mAccessConfiguration.getApiAccessUrl());
        mLanguage = languageType;
        mDeviceId = deviceId;
    }

    /**
     * Instantiates an API handle to make Authorization
     * related access handling.
     *
     * @return an instance of class AuthorizationFacade
     */
    @Override
    public AuthorizationFacade getAuthorizationFacade() {
        return new AuthorizationHandler<T>(mCallerActivity);
    }

    /**
     * Instantiates an API handle to retrieve Accss Token
     *
     * @return an instance of class AccessTokenFacade
     */
    @Override
    public AccessTokenFacade getAccessTokenFacade() {
        return new AccessTokenHandler<T>(mCallerActivity);
    }

    /**
     * Instantiates an API handle to make a GET request
     *
     * @return an instance of class GetRequestFacade
     */
    @Override
    public GetRequestFacade getGetFacade() {
        return new GetRequestHandler<T>(mCallerActivity);
    }

    /**
     * Instantiates an API handle to make a POST request
     *
     * @return an instance of class PostRequestFacade
     */
    @Override
    public PostRequestFacade getPostFacade() {
        return new PostRequestHandler<T>(mCallerActivity);
    }


}
