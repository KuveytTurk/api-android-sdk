/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT Licence
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 18:11
 *
 *
 */

package tr.com.kuveytturk.android.sdk;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonObject;

import java.security.KeyPair;
import java.security.KeyStoreException;
import java.util.HashMap;

import tr.com.kuveytturk.android.sdk.api.AccessTokenFacade;
import tr.com.kuveytturk.android.sdk.api.AuthorizationFacade;
import tr.com.kuveytturk.android.sdk.api.FacadeFactory;
import tr.com.kuveytturk.android.sdk.api.GetRequestFacade;
import tr.com.kuveytturk.android.sdk.api.FacadeFactoryInterface;
import tr.com.kuveytturk.android.sdk.api.PostRequestFacade;
import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.api.util.AccessTokenResponseBean;
import tr.com.kuveytturk.android.sdk.api.util.SignatureGenerationException;
import tr.com.kuveytturk.android.sdk.api.util.SignatureGenerator;

public class MainActivity extends AppCompatActivity implements ResponseHandlingFacade {
    private final static String CLIENT_ID = "ea84d416918e4733bc87663a63142175";
    private final static String CLIENT_SECRET = "q7YEj9LIYeAdu7nZ28SSFJH7L3dLuTLGnk8tH6/LbSKIiPYvtihifQ==";
    private final static String REDIRECT_URI = "ktauth://callback";
    private final String SCOPE = "loans transfers public accounts offline_access";
    private final static String RESPONSE_TYPE = "code";

    private AuthorizationFacade<MainActivity> mKTAuthHandlerFacade;
    private AccessTokenFacade<MainActivity> mKTAccessTokenHandlerFacade;
    private PostRequestFacade<MainActivity> mKTPostRequestHandlerFacade;
    private GetRequestFacade<MainActivity> mKTGetRequestHandlerFacade;


    private String mKTAuthorizationCode;
    private String mAuthorizationBearer;
    private String mAccessToken;
    private String mRefreshToken;

    private final static String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKdiTMfK6+6opAt7Lh3230kGA2" +
            "Rz+ErdKpjB41LI0lVh/HBuvVv46/Mou8sodPApuO0OXFrxRjqRLjr3yECOuY65vk" +
            "knHEjK5XjOF1GrlOzSMHaEggcdWBZTJo4wOyHA5kI8a+RVxNcRPCuQAxq5FXEzA6" +
            "Q+gWwkAzxqSZ3dQRWwIDAQAB" +
            "-----END PUBLIC KEY-----";

    private final static String PRIVATE_KEY = "MIICWwIBAAKBgQCKdiTMfK6+6opAt7Lh3230kGA2Rz+ErdKpjB41LI0lVh/HBuvV" +
            "v46/Mou8sodPApuO0OXFrxRjqRLjr3yECOuY65vkknHEjK5XjOF1GrlOzSMHaEgg" +
            "cdWBZTJo4wOyHA5kI8a+RVxNcRPCuQAxq5FXEzA6Q+gWwkAzxqSZ3dQRWwIDAQAB" +
            "AoGAaNvQQoyqSiuVSC3WaviqbOxp8LFEiVaak4xp1BtJSV1P84pqUBYiJOpCqUUK" +
            "8+slo6LQYEWXS2Jfy866ncOjp65jzPlxm5D+0wNiuHJKDck5oMiRwqmMlS9nNezV" +
            "xGG9RGXjUzwsII5AyorkDE2U3wXZy7hB8yHT0a6JrpDCJgECQQDD2yk+cprOkrxU" +
            "/U/ZPG2TYtUffGlS45gyIsIhgsbRwhLhTvfxg8zwA+zk3ItUKKAJ/R8vYu1c0oFs" +
            "NWC6nsojAkEAtPsFHy++8FA89ewW8+YvMnM6sNmFoO30xiTAPGi7d9hAkqUJAyMf" +
            "pV3xf2QRCnzJYl3DrEzRu40fHLpWi8RDaQJAT8Gvyf5hjD208+cz3QL+nEZjA69m" +
            "NJr6H3CIHZ1j2YduqNG/plpF2ne+wHQPSPZCNc8eI+3lOyd+DNKv0U9YgQJAa2lP" +
            "8OJ1gEse4xXryXWkLV0WSD/Rf2G7FJ5bOX8vREGkkWRBpQsDjTHkUqchNgg5vZfI" +
            "ukodcCKhhHtTQkCJgQJAb1mkLUtvRFSqkhKO5nFkwGMNfTR/3cfu19bG0iZ0MqVQ" +
            "YTviFIbJrheEkjdo0+GgL9eBsONXlio0ALnKLZZrYg==";

    java.security.PublicKey mPublicKey;
    java.security.PrivateKey mPrivateKey;
    KeyPair mKeyPair;

    public MainActivity() throws KeyStoreException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BEGIN - KUVEYT TÜRK API HANDLEs
        FacadeFactoryInterface<MainActivity> ktFacadeFactory = new FacadeFactory<MainActivity>(this);
        mKTAuthHandlerFacade = ktFacadeFactory.getAuthorizationFacade();
        mKTAccessTokenHandlerFacade = ktFacadeFactory.getAccessTokenFacade();
        mKTGetRequestHandlerFacade =  ktFacadeFactory.getGetFacade();
        mKTPostRequestHandlerFacade = ktFacadeFactory.getPostFacade();
        //END - KUVEYT TÜRK API HANDLEs

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                mKTAuthHandlerFacade.requestAuthorization(CLIENT_ID, RESPONSE_TYPE, REDIRECT_URI, SCOPE);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mKTAuthHandlerFacade.pollAuthorizationResponse(REDIRECT_URI);
    }

    @Override
    public void onGetResponseReceivedFromKTAPI(String getResponseMsg) {
        System.out.println("GET RESPONSE:\n " + getResponseMsg);
    }

    @Override
    public void onPostResponseReceivedFromKTAPI(String postResponseMsg) {
        System.out.println("POST RESPONSE:\n " + postResponseMsg);
    }

    @Override
    public void onAccessTokenReceived(AccessTokenResponseBean responseBean) {
        if (responseBean.getError() == null || responseBean.getError().isEmpty()) {

            mAccessToken = responseBean.getAccessToken();
            mRefreshToken = responseBean.getRefreshToken();
            mAuthorizationBearer = "Bearer " + mAccessToken;

            //Sample GET Request BEGIN
            HashMap<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("cityId", "34");

            String signatureForGETRequest = null;
            try {
                signatureForGETRequest = SignatureGenerator.generateSignatureForGetRequest(mAccessToken, PRIVATE_KEY, queryParams);
            } catch (SignatureGenerationException e) {
                e.printStackTrace();
                return;
            }

            mKTGetRequestHandlerFacade.doGet("v1/data/banks/10/branches", queryParams, mAuthorizationBearer, signatureForGETRequest);
            //mKTGetRequestHandlerFacade.doGet("v1/data/testcustomers",mAuthorizationBearer, mAccessToken);
            //Sample GET Request END

            //Sample POST Request BEGIN
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("SenderAccountSuffix", 1);
            jsonObject.addProperty("ReceiverName", "Dohn");
            jsonObject.addProperty("ReceiverIBAN", "TR660020500009105718200001");
            jsonObject.addProperty("Amount", 1);
            jsonObject.addProperty("Comment", "SDK Test");
            jsonObject.addProperty("PaymentTypeId", 99);
            String jsonBody = jsonObject.toString();

            String signatureForPostRequest = null;
            try {
                signatureForPostRequest = SignatureGenerator.generateSignatureForPostRequest(mAccessToken, PRIVATE_KEY, jsonBody);
            } catch (SignatureGenerationException e) {
                e.printStackTrace();
                return;
            }

            //Make post request
            mKTPostRequestHandlerFacade.doPost("v1/transfers/ToIBAN",jsonBody, mAuthorizationBearer, signatureForPostRequest);
            //Sample POST Request END

        } else {
            System.out.println("ERROR OCCURRED WHILE RETRIEVING THE ACCESS CODE:\n ERROR: " +
                    responseBean.getError() + "\nERROR DESCRIPTION: " +
                    responseBean.getErrorDescription());
        }
    }

    @Override
    public void onAuthorizationCodeReceived(String authorizationCode, String state, String errorCode) {
        if (errorCode == null || errorCode.isEmpty()) {
            mKTAuthorizationCode = authorizationCode;
            mKTAccessTokenHandlerFacade.requestAccessTokenWithCode(CLIENT_ID, CLIENT_SECRET, mKTAuthorizationCode, REDIRECT_URI);
        } else {
            System.out.println("AUTHORIZATION FAILED!\n ERROR CODE: " + errorCode + "\nSTATE: " + state);
        }
    }
}

