/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 25.04.2018 17:07
 *
 *
 */

package tr.com.kuveytturk.android.sdk.api.util;

/**
 * Custom exception class that can be thrown during signature generation
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public final class SignatureGenerationException extends Exception {

    /**
     * Constructor
     *
     * @param  message Holds the error message
     * @param  e Holds the original exception object
     */
    public SignatureGenerationException(String message, Exception e){
        super(message,e);
    }
}
