/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 */

package tr.com.kuveytturk.android.sdk.api;

/**
 * The interface providing the API routines to create
 * facade instances (i.e. handles) for accessing to
 * the Kuveyt Turk web service backend.
 *
 * @author  Fikri Aydemir
 * @version 1.0
 * @since   2018-04-18
 */

public interface FacadeFactoryInterface<T> {

    /**
     * Instantiates an API handle to make Authorization
     * related access handling.
     *
     * @return an instance of class AuthorizationFacade
     */
     AuthorizationFacade getAuthorizationFacade();

    /**
     * Instantiates an API handle to retrieve Accss Token
     *
     * @return an instance of class AccessTokenFacade
     */
     AccessTokenFacade getAccessTokenFacade();

    /**
     * Instantiates an API handle to make a GET request
     *
     * @return an instance of class GetRequestFacade
     */
     GetRequestFacade getGetFacade();

    /**
     * Instantiates an API handle to make a POST request
     *
     * @return an instance of class PostRequestFacade
     */
     PostRequestFacade getPostFacade();

    /**
     * Represents the language types in which back-end messages are displayed.
     */
    enum LANGUAGE_TYPE {
        TURKISH(1), ENGLISH(2);

        private int numVal;

        LANGUAGE_TYPE(int numVal) {
            this.numVal = numVal;
        }

        public int value() {
            return numVal;
        }
    }

}
