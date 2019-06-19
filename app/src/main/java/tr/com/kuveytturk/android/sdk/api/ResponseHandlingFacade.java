/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 15.04.2018 12:09
 *
 *
 */

package tr.com.kuveytturk.android.sdk.api;

import tr.com.kuveytturk.android.sdk.api.util.AccessTokenResponseBean;

/**
 * Interface for the API routines to deliver response messages coming from the the Kuveyt Türk
 * web service backend. The generic type T extends the class android.app.Activity and implements
 * the interface ResponseHandlingFacade defined in the package tr.com.kuveytturk.android.sdk.api.
 * It represents the type of the Activity object that creates an instance of the class implemeting
 * this interface.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public interface ResponseHandlingFacade {

    /**
     * Fetches the response message that is returned from Kuveyt Türk
     * web service backend as a result of the corresponding GET request.
     *
     * @param responseMessage Holds the content of the response message that is returned from
     *                        the Kuveyt Türk web service backend as a result of a GET request.
     */
    void onGetResponseReceivedFromKTAPI(String responseMessage);

    /**
     * Fetches the response message that is returned from Kuveyt Türk
     * web service backend as a result of the corresponding POST request
     *
     * @param responseMessage Holds the content of the response message that is returned from
     *                        the Kuveyt Türk web service backend as a result of a POST request.
     */
    void onPostResponseReceivedFromKTAPI(String responseMessage);

    /**
     * Fetches the response the access token that is returned from the
     * Kuveyt Türk identity server.
     *
     * @param accessToken Holds the content of the access token that is returned from the
     *                    Kuveyt Türk identity server.
     */
    void onAccessTokenReceived(AccessTokenResponseBean accessToken);

    /**
     * Fetches the response the authorization code that is returned from the
     * Kuveyt Türk identity server.
     *
     * @param authorizationCode Holds the content of the authorization code that is returned from
     *                          the Kuveyt Türk identity server.
     * @param state             Holds the state of the response returned from the backend if an
     *                          error occurs.
     *
     * @param error             Holds the content of the error message from the backend in case
     *                          an error occurs.
     */
    void onAuthorizationCodeReceived(String authorizationCode, String state, String error);
}
