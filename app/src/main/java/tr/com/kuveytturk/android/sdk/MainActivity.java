/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 17.04.2018 18:11
 *
 *
 */

package tr.com.kuveytturk.android.sdk;

import android.os.Bundle;
import android.os.StrictMode;
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
import java.util.ArrayList;

import tr.com.kuveytturk.android.sdk.api.AccessTokenFacade;
import tr.com.kuveytturk.android.sdk.api.AuthorizationFacade;
import tr.com.kuveytturk.android.sdk.api.FacadeFactory;
import tr.com.kuveytturk.android.sdk.api.GetRequestFacade;
import tr.com.kuveytturk.android.sdk.api.FacadeFactoryInterface;
import tr.com.kuveytturk.android.sdk.api.PostRequestFacade;
import tr.com.kuveytturk.android.sdk.api.ResponseHandlingFacade;
import tr.com.kuveytturk.android.sdk.api.util.AccessTokenResponseBean;
import tr.com.kuveytturk.android.sdk.api.util.QueryParameterBean;
import tr.com.kuveytturk.android.sdk.api.util.SignatureGenerationException;
import tr.com.kuveytturk.android.sdk.api.util.SignatureGenerator;

public class MainActivity extends AppCompatActivity implements ResponseHandlingFacade {

    private final static String CLIENT_ID = "9a879348887f4066a843f0495d95acb3";
    private final static String CLIENT_SECRET = "hgAj/Y8COOI84OmmNzCzH2KDqUi8OzEABb8PMo9vFf7YaLiIJLHwxg==";
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

    private boolean isClientCredentials = false;


    private final static String PRIVATE_KEY =
            "-----BEGIN PRIVATE KEY-----" +
                    "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANRMWo1MOpaPs7vJ" +
                    "QiiYRLAiQz1DxS0gJA6wr6sGol56Xb+94FtU4ref6fwD86FMhosKN9l4iLjXMMqk" +
                    "1K0uBAVpRRCehqQPH4pwEOojdTlOzih8PG1iKRhPjgeD5HDN9Z12hjpu/bemlPTy" +
                    "ntyzMG1ZmQGiQMdoaYQenVHbDhlTAgMBAAECgYBFgqJ7dSQRvAdrSuBAjmqfCPjf" +
                    "DFt5BPcJYEyEQO3U5VfgufjFrqt02AUyoNCaVYYP7E6RA+gwLTUqhIGmGlTyH376" +
                    "Ruiz65CqPo8ZfWxOnQiywMJwFk2u4CpmdI3IJODLfF38Ps8Vwaqhr95koNT7e6mS" +
                    "rpPKGxCvj/L9qsO1AQJBAPwL40MKlyp8B63iajMXZITrb2Yc8ZESPLkdu2X21kdT" +
                    "mQKokdauaew9JGHkElu1J9WyUXZULEekLr4/FvRnQUECQQDXoNqiVrj46chMJrh7" +
                    "c8I1h2MlqnYa8MMh43DmBR8uynsGlz0eFIEjcbYM4A3+5FNrEtN7wY2pE6kGJQp3" +
                    "imGTAkEArFk8qAU/5Q82+RJP6Gvgknuji0HTdY3w8+x+znSBhfiGMqkuQIy3ZZFR" +
                    "pZadbxRrDteGmNFqDfsY84KUob9RgQJAOrW9Ub4zFvLwamuQh2x5UIHQaQ0Eo0ky" +
                    "mCOJNdfnKaJP5PeA2JPUpYXsf4zxwpkAbYLuuh91JrgHqXikZO/0qQJBAIqQiiHv" +
                    "oGIUEE5y3RZ1dOsvGPbaavXG/OxtyAvjl5tfBSt1zmHMQi46ZuFrHSr54uc4BDfD" +
                    "gDa5DmMqw0sTrhY=" +
                    "-----END PRIVATE KEY-----";

    java.security.PublicKey mPublicKey;
    java.security.PrivateKey mPrivateKey;
    KeyPair mKeyPair;

    public MainActivity() throws KeyStoreException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BEGIN - KUVEYT TÜRK API HANDLEs
        FacadeFactoryInterface<MainActivity> ktFacadeFactory =
                new FacadeFactory<MainActivity>(this);
        mKTAuthHandlerFacade = ktFacadeFactory.getAuthorizationFacade();
        mKTAccessTokenHandlerFacade = ktFacadeFactory.getAccessTokenFacade();
        mKTGetRequestHandlerFacade =  ktFacadeFactory.getGetFacade();
        mKTPostRequestHandlerFacade = ktFacadeFactory.getPostFacade();
        //END - KUVEYT TÜRK API HANDLEs

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Authorization Code Flow", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                isClientCredentials = false;

                mKTAuthHandlerFacade.requestAuthorization(
                        CLIENT_ID,
                        RESPONSE_TYPE,
                        REDIRECT_URI,
                        SCOPE);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Client Credential Flow", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                isClientCredentials = true;
                mKTAccessTokenHandlerFacade.requestAccessTokenWithClientCredentials(
                        CLIENT_ID,
                        CLIENT_SECRET,
                        "public");
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

            //****** BEGINNING of Sample GET Request with Authorization Code Flow ******
            if(!isClientCredentials) {
                String signatureForGETRequest = null;
                try {
                    signatureForGETRequest =
                            SignatureGenerator
                                    .generateSignatureForGetRequest(mAccessToken,
                                            PRIVATE_KEY);
                } catch (SignatureGenerationException e) {
                    e.printStackTrace();
                    return;
                }

                //BEGIN - FİX the BUG here to get linkedin recommendation.
                ArrayList<QueryParameterBean> queryParamList = new ArrayList<>();
                QueryParameterBean queryParamBean = new QueryParameterBean("itemCount", "100");
                queryParamList.add(queryParamBean);

                mKTGetRequestHandlerFacade.doGet(
                        "v2/accounts/1/transactions",
                        queryParamList,
                        mAuthorizationBearer,
                        signatureForGETRequest);
                //END - FİX the BUG here to get linkedin recommendation.

                //****** END of Sample GET Request with Authorization Code Flow******

                //****** BEGINNING of Sample POST Request with Authorization Code Flow ******
                //Fetches account transactions
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("suffix", 1);
                jsonObject.addProperty("itemCount", 100);
                String jsonBody = jsonObject.toString();

                String signatureForPostRequest = null;
                try {
                    signatureForPostRequest =
                            SignatureGenerator
                                    .generateSignatureForPostRequest(mAccessToken, PRIVATE_KEY, jsonBody);
                } catch (SignatureGenerationException e) {
                    e.printStackTrace();
                    return;
                }

                //Make post request
                mKTPostRequestHandlerFacade.doPost(
                        "v1/accounts/transactions",
                        jsonBody,
                        mAuthorizationBearer,
                        signatureForPostRequest);
                //****** END of Sample POST Request  with Authorization Code Flow ******

            } else {
                //****** BEGINNING of  Sample GET Request with Client Credentials Flow ******
                String signatureForGETRequest = null;
                try {
                    signatureForGETRequest =
                            SignatureGenerator.
                                    generateSignatureForGetRequest(mAccessToken, PRIVATE_KEY);
                } catch (SignatureGenerationException e) {
                    e.printStackTrace();
                    return;
                }
                mKTGetRequestHandlerFacade.doGetToPublicAPIEndPoint(
                        "v1/data/testcustomers",
                        mAuthorizationBearer,
                        signatureForGETRequest);
                //****** END of Sample GET Request with Client Credentials Flow ******

                //****** BEGINNING of Sample POST Request with Client Credentials Flow ******
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("accountNumber", 8002577);
                jsonObject.addProperty("accountSuffix", 1);
                jsonObject.addProperty("processId", 421553);
                jsonObject.addProperty("isPTTCollection", "false");
                String jsonBody = jsonObject.toString();

                String signatureForPostRequest = null;
                try {
                    signatureForPostRequest =
                            SignatureGenerator
                                    .generateSignatureForPostRequest(mAccessToken, PRIVATE_KEY, jsonBody);
                } catch (SignatureGenerationException e) {
                    e.printStackTrace();
                    return;
                }

                //Make post request
                mKTPostRequestHandlerFacade.doPost(
                        "v1/collections",
                        jsonBody,
                        mAuthorizationBearer,
                        signatureForPostRequest);

                //****** END of Sample POST Request with Client Credentials Flow ******
            }

        } else {
            System.out.println("ERROR OCCURRED WHILE RETRIEVING THE ACCESS CODE:\n ERROR: " +
                    responseBean.getError() + (responseBean.getErrorDescription() != null ?
                    "\nERROR DESCRIPTION: " +
                    responseBean.getErrorDescription() : ""));
        }
    }

    @Override
    public void onAuthorizationCodeReceived(String authorizationCode, String state, String errorCode) {
        if (errorCode == null || errorCode.isEmpty()) {
            mKTAuthorizationCode = authorizationCode;
            mKTAccessTokenHandlerFacade.requestAccessTokenWithAuthorizationCode(
                    CLIENT_ID,
                    CLIENT_SECRET,
                    mKTAuthorizationCode,
                    REDIRECT_URI);
        } else {
            System.out.println("AUTHORIZATION FAILED!\n ERROR CODE: "
                    + errorCode + "\nSTATE: " + state);
        }
    }
}

