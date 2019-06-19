/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 16.04.2018 14:20
 *
 *
 */

package tr.com.kuveytturk.android.sdk.api;

import android.app.Activity;

/**
 * Interface for the API routines to make POST requests to the Kuveyt Türk web service backend. The
 * generic type T extends the class android.app.Activity and implements the interface
 * ResponseHandlingFacade defined in the package tr.com.kuveytturk.android.sdk.api. It represents
 * the type of the Activity object that creates an instance of the class implemeting this interface.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public interface PostRequestFacade<T extends Activity & ResponseHandlingFacade> {

    /**
     * Sends a POST request at the Kuveyt Türk web service by using the given parameters.
     *
     * @param endPoint Represents the end point from where the request is to be made
     *                 (e.g. v1/transfers/ToIBAN).
     * @param jsonBody Holds the body of the request that is in the JSON format.
     * @param authorizationBearer Holds the text in the format "Bearer <ACCESS_TOKEN>" where
     *                            ACCESS_TOKEN ,s the token that is retrived from the Kuveyt Türk
     *                            identity server.
     * @param signature The encrypted text that is formed by hashing the access token
     *                  with query parameter string by using the private key instance.
     *                  In class SignatureGenerator that is available in the package
     *                  tr.com.kuveytturk.android.sdk.api, you can find helper methods
     *                  to create this signature object parameter.
     */
    void doPost(String endPoint,
                String jsonBody,
                String authorizationBearer,
                String signature);

    /**
     * Sends a POST request to the Kuveyt Türk web service by using the given parameters.
     *
     * @param endPoint Represents the end point from where the request is to be made
     *                 (e.g. v1/transfers/ToIBAN).
     * @param jsonBody Holds the body of the request that is in the JSON format.
     * @param authorizationBearer Holds the text in the format "Bearer <ACCESS_TOKEN>" where
     *                            ACCESS_TOKEN ,s the token that is retrived from the Kuveyt Türk
     *                            identity server.
     * @param signature The encrypted text that is formed by hashing the access token
     *                  with query parameter string by using the private key instance.
     *                  In class SignatureGenerator that is available in the package
     *                  tr.com.kuveytturk.android.sdk.api, you can find helper methods
     *                  to create this signature object parameter.
     */
    public void doPostToPublicAPIEndPoint(String endPoint,
                                          String jsonBody,
                                          String authorizationBearer,
                                          String signature);
}
