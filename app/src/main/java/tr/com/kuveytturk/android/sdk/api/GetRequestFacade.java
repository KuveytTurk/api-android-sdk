/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 */

package tr.com.kuveytturk.android.sdk.api;

import android.app.Activity;
import java.util.ArrayList;

import tr.com.kuveytturk.android.sdk.api.util.QueryParameterBean;

/**
 * Interface for the API routines to make GET requests to the Kuveyt Türk web service backend. The
 * generic type T extends the class android.app.Activity and implements the interface
 * ResponseHandlingFacade defined in the package tr.com.kuveytturk.android.sdk.api. It represents
 * the type of the Activity object that creates an instance of the class implemeting this interface.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public interface GetRequestFacade<T extends Activity & ResponseHandlingFacade> {

    /**
     * Instantiates an API handle to retrieve Accss Token
     *
     * @param endPoint Represents the end point from where the request is to be made
     *                 (e.g. v1/data/testcustomers).
     * @param queryParams Holds the query parameter names and and their values as an ordered
     *                    list. It represents the object form of the query parameter string, such as
     *                    v1/data/testcustomers?param1=1&param2=2, which begins with question
     *                    mark.
     * @param authorizationBearer Holds the text in the format "Bearer <ACCESS_TOKEN>" where
     *                            ACCESS_TOKEN ,s the token that is retrieved from the Kuveyt Türk
     *                            identity server.
     * @param signature The encrypted text that is formed by hashing the access token
     *                  with query parameter string by using the private key instance.
     *                  In class SignatureGenerator that is available in the package
     *                  tr.com.kuveytturk.android.sdk.api, you can find helper methods
     *                  to create this signature object parameter.
     */
    void doGet(String endPoint,
               ArrayList<QueryParameterBean> queryParams,
               String authorizationBearer,
               String signature);

    /**
     * Sends a GET request at the Kuveyt Türk web service by using the given parameters.
     *
     * @param endPoint Represents the end point from where the request is to be made
     *                 (e.g. v1/data/testcustomers).
     * @param queryParams Holds the query parameter names and and their values as an ordered
     *                    list. It represents the object form of the query parameter string, such as
     *                    v1/data/testcustomers?param1=1&param2=2, which begins with question
     *                    mark.
     */
    void doGet(String endPoint,
               ArrayList<QueryParameterBean> queryParams,
               String authorizationBearer);

    /**
     * Sends a GET request at the Kuveyt Türk web service by using the given parameters.
     *
     * @param endPoint Represents the end point from where the request is to be made
     *                 (e.g. v1/data/testcustomers).
     * @param authorizationBearer Holds the text in the format "Bearer <ACCESS_TOKEN>" where
     *                            ACCESS_TOKEN is the token that is retrieved from the Kuveyt Türk
     *                            identity server.
     * @param signature The encrypted text that is formed by hashing the access token
     *                  with query parameter string by using the private key instance.
     *                  In class SignatureGenerator that is available in the package
     *                  tr.com.kuveytturk.android.sdk.api, you can find helper methods
     *                  to create this signature object parameter.
     */
    void doGet(String endPoint,
               String authorizationBearer,
               String signature);

    /**
     * Sends a GET request to the Kuveyt Türk web service by using the given parameters.
     *
     * @param endPoint Represents the end point from where the request is to be made
     *                 (e.g. v1/data/testcustomers).
     * @param signature The encrypted text that is formed by hashing the access token
     *                  with query parameter string by using the private key instance.
     *                  In class SignatureGenerator that is available in the package
     *                  tr.com.kuveytturk.android.sdk.api, you can find helper methods
     *                  to create this signature object parameter.
     *
     * @param authorizationBearer Holds the text in the format "Bearer <ACCESS_TOKEN>" where
     *                            ACCESS_TOKEN is the token that is retrieved from the Kuveyt Türk
     *                            identity server.
     */
    void doGetToPublicAPIEndPoint(String endPoint,
                                  String signature,
                                  String authorizationBearer);


}
