/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 04.05.2018 13:57
 *
 *
 */

package tr.com.kuveytturk.android.sdk.api.util;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides utility methods for signature generation for the GET and POST requests.
 *
 * @author      Fikri Aydemir
 * @version     1.0
 * @since       2018-04-18
 */
public final class SignatureGenerator {

    /**
     * Utility method for generating signature for GET requests
     *
     * @param accessToken The clientId that is provided by Kuveyt Türk API market when an application
     *                    is created.
     * @param privateKeyAsString The RSA private key as a string object
     * @param queryParams The query parameter names and values as an HashMap object
     * @return The base64 encoded signature by using SHA256/RSA
     */
    public static String generateSignatureForGetRequest(String accessToken,
                                                        String privateKeyAsString,
                                                        ArrayList<QueryParameterBean> queryParams) throws SignatureGenerationException {

        String queryString = getQueryParamsString(queryParams);
        String input = accessToken.trim() + queryString;
        String base64Signature;

        try {
            PrivateKey privateKey = buildPrivateKeyFromString(privateKeyAsString);
            base64Signature = signSHA256RSA(input, privateKey);
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            throw new SignatureGenerationException(msg, e);
        }

        return base64Signature;
    }

    /**
     * Utility method for generating signature for GET requests
     *
     * @param accessToken The clientId that is provided by Kuveyt Türk API market when an application
     *                    is created.
     * @param privateKeyAsString The RSA private key as a string object
     * @return The base64 encoded signature by using SHA256/RSA
     */
    public static String generateSignatureForGetRequest(String accessToken,
                                                        String privateKeyAsString) throws SignatureGenerationException {
        String input = accessToken.trim();
        String base64Signature;

        try {
            PrivateKey privateKey = buildPrivateKeyFromString(privateKeyAsString);
            base64Signature = signSHA256RSA(input, privateKey);
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            throw new SignatureGenerationException(msg, e);
        }

        return base64Signature;
    }

    /**
     * Utility method for generating signature for POST requests
     *
     * @param accessToken The clientId that is provided by Kuveyt Türk API market when an application
     *                 is created.
     * @param privateKeyAsString The RSA private key as a string object.
     * @param jsonBody The content of the request body in JSON format as a String object.
     * @return The base64 encoded signature by using SHA256/RSA
     */
    public static String generateSignatureForPostRequest(String accessToken,
                                                         String privateKeyAsString,
                                                         String jsonBody) throws SignatureGenerationException {
        String input = accessToken + jsonBody;
        String base64Signature;

        try {
            PrivateKey privateKey = buildPrivateKeyFromString(privateKeyAsString);
            base64Signature = signSHA256RSA(input, privateKey);
        } catch (Exception e) {
            String msg =  e.getLocalizedMessage();
            throw  new SignatureGenerationException(msg, e);
        }
        return base64Signature;
    }

    /**
     * Utility method for generating base64 encoded signature by using SHA256/RSA.
     *
     * @param input The string object upon which hashing is to be applied.
     * @param privateKey The RSA private key
     * @return The base64 encoded signature by using SHA256/RSA
     */
    private static String signSHA256RSA(String input,
                                        PrivateKey privateKey) throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(privateKey);
        s.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] signature = s.sign();
        return Base64.encodeToString(signature, Base64.NO_WRAP);
    }

    /**
     * Utility method for generating the query parameter string
     *
     * @param queryParams The query parameter names and values as an HashMap object
     * @return Query parameters as string (e.g. ?param1=1&param2=2)
     */
    private static String getQueryParamsString(ArrayList<QueryParameterBean> queryParams) throws SignatureGenerationException {
        if (queryParams != null && !queryParams.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            try{
                sb.append(URLEncoder.encode("?", "UTF-8"));
            } catch (UnsupportedEncodingException exp) {
                String msg = "Error occurred while converting ? mark into URLEncoded form.\n";
                throw  new SignatureGenerationException(msg, exp);
            }

            int count = 0;
            for (QueryParameterBean e : queryParams) {
                count++;
                try{
                  sb.append(URLEncoder.encode(e.getParamName(), "UTF-8")).
                          append(URLEncoder.encode("=", "UTF-8")).
                          append(URLEncoder.encode(e.getParamValue(), "UTF-8"));
                } catch (UnsupportedEncodingException exp) {
                    String msg = "Error occurred while converting query parameters into string form.\n";
                    throw new SignatureGenerationException(msg, exp);
                }

                if (count != queryParams.size()) {
                    try{
                        sb.append(URLEncoder.encode("&", "UTF-8"));
                    } catch (UnsupportedEncodingException exp) {
                        String msg = "Error occurred while converting & mark into URLEncoded form.\n";
                        throw new SignatureGenerationException(msg, exp);
                    }
                }
            }

            return sb.toString();
        }

        return "";
    }

    /**
     * Utility method for converting the private key in text format into the binary format
     *
     * @param publicKeyAsString The RSA private key as a string object.
     * @return Public key as an instance of java.security.PublicKey
     */
    private static PublicKey buildPublicKeyFromString(String publicKeyAsString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKeyStrWithoutHeaderFooter = publicKeyAsString.
                replaceAll("-----BEGIN PUBLIC KEY-----\n", "").
                replaceAll("-----END PUBLIC KEY-----\n", "").
                replaceAll("\n", "");
        byte[] publicKeyBytes = Base64.decode(publicKeyStrWithoutHeaderFooter.getBytes(StandardCharsets.UTF_8),  Base64.NO_WRAP);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    /**
     * Utility method for converting the private key in text format into the binary format
     *
     * @param privateKeyAsString The RSA private key as a string object.
     * @return Private key as an instance of java.security.PrivateKey
     */
    private static PrivateKey buildPrivateKeyFromString(String privateKeyAsString) throws NoSuchAlgorithmException, InvalidKeySpecException{
        String privateKeyStrWithoutHeaderFooter = privateKeyAsString.
                replaceAll("-----BEGIN PRIVATE KEY-----", "").
                replaceAll("-----END PRIVATE KEY-----", "").
                replaceAll("\n", "");
        byte[] privateKeyBytes =
                Base64.decode(privateKeyStrWithoutHeaderFooter.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePrivate(keySpec);
    }
}
