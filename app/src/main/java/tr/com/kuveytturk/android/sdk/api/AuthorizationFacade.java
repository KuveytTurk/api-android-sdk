/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 */

package tr.com.kuveytturk.android.sdk.api;

import android.app.Activity;

/**
 * Interface for the API routines to make OAuth authorization requests to the Kuveyt Türk
 * identity server. The generic type T extends the class android.app.Activity and implements the
 * interface ResponseHandlingFacade defined in the package tr.com.kuveytturk.android.sdk.api.
 * It represents the type of the Activity object that creates an instance of the class implemeting
 * this interface.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public interface AuthorizationFacade<T extends Activity & ResponseHandlingFacade> {

    /**
     * Sends an OAuth authorization request to the identity server of Kuveyt Türk.
     *
     * @param clientId The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param responseType Holds the response type, which is "CODE" at all times by default.
     * @param redirectURI Holds redirect URI that is provided by Kuveyt Türk API market when
     *                    an application is created.
     * @param scope Holds the scope of the authorization. Can have the values seperated by space for
     *              example: loans transfers public accounts offline_access
     */
    void requestAuthorization(String clientId,
                              String responseType,
                              String redirectURI,
                              String scope);

    /**
     * Sends an OAuth authorization request to the identity server of Kuveyt Türk.
     *
     * @param clientId The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param responseType Holds the response type, which is "CODE" at all times by default.
     * @param redirectURI Holds redirect URI that is provided by Kuveyt Türk API market when
     *                    an application is created.
     * @param scope Holds the scope of the authorization. Can have the values seperated by space for
     *              example: loans transfers public accounts offline_access
     * @param state Holds the desird authorization state.
     */
    void requestAuthorization(String clientId,
                              String responseType,
                              String redirectURI,
                              String scope,
                              String state);

    /**
     * Sends an OAuth authorization request to the identity server of Kuveyt Türk.
     *
     * @param redirectURI Holds redirect URI that is provided by Kuveyt Türk API market when
     *                    an application is created.
     */
    void pollAuthorizationResponse(String redirectURI);
}
