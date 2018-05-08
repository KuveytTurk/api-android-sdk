/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 */

package tr.com.kuveytturk.android.sdk.api;

import android.app.Activity;

/**
 * Interface for the API routines to make access token requests to the Kuveyt Türk
 * identity server. The generic type T extends the class android.app.Activity and implements the
 * interface ResponseHandlingFacade defined in the package tr.com.kuveytturk.android.sdk.api.
 * It represents the type of the Activity object that creates an instance of the class implemeting
 * this interface.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public interface AccessTokenFacade<T extends Activity & ResponseHandlingFacade> {

    /**
     * Sends an access token request to the identity server of Kuveyt Türk.
     *
     * @param clientId The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param clientSecret The clientSecret that is provided by Kuveyt Türk API market when an
     *                     application is created.
     * @param code The authorization code that is returned from the identity server.
     * @param redirectUri Holds redirect URI that is provided by Kuveyt Türk API market when
     *                    an application is created.
     */
    void requestAccessTokenWithCode(String clientId,
                                    String clientSecret,
                                    String code,
                                    String redirectUri);

    /**
     * Sends a refresh token to the identity server to get a new access token.
     *
     * @param clientId The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param clientSecret The clientSecret that is provided by Kuveyt Türk API market when an
     *                     application is created.
     * @param refreshToken Represents the refresh token with which one can get a new acccess token
     *                     from the identity server.
     */
    void requestAccessTokenWithRefreshToken(String clientId,
                                            String clientSecret,
                                            String refreshToken);

    /**
     * Sends an access token revoke request to the identity server.
     *
     * @param clientId The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param clientSecret The clientSecret that is provided by Kuveyt Türk API market when an
     *                     application is created.
     * @param accessToken The acess token to be revoked.
     */
    void revokeAccessToken(String clientId,
                           String clientSecret,
                           String accessToken);

    /**
     * Sends a refresh token revoke request to the identity server.
     *
     * @param clientId The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param clientSecret The clientSecret that is provided by Kuveyt Türk API market when an
     *                     application is created.
     * @param refreshToken The refresh token to be revoked.
     */
    void revokeRefreshToken(String clientId,
                            String clientSecret,
                            String refreshToken);
}
