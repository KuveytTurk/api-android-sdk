# KuveytTurkAPIAndroid
Kuveyt Turk Android SDK written in Java to help Android developers make use of Kuveyt Turk Banking API  in their app development.

# 1.	OVERVIEW

Kuveyt Türk Banking Android SDK aims at helping Android developers build their apps by consuming Kuveyt Türk Banking API Service. The SDK has been written in Java and can be utilized as part of an Android Studio project.
In github, we have provided a sample android application that can be taken as an example.  Nevertheless, this sample application does not necessarily demonstrate the best use but rather features of using Kuveyt Turk API Banking Java SDK. Always remember to handle exceptions.
In the rest of this document, the name “SDK user” is used to refer to the application developer that intends to develop an android app by using the Kuveyt Türk Banking Android SDK to access Kuveyt Türk API service.

# 1.1	Highlights

•	Kuveyt Türk banking SDK provides a high level API for sending/receiving requests to Kuveyt Türk API server by using a combination Factory and Façade design patterns in order to minimize the effort for the SDK user to code.

•	The SDK provides a non-blocking client API for sending requests to the API server. This means the methods provided in the API returns immediately per method call. This is achieved transparently to the SDK user by delegating the execution immediately to a background thread per method call. Background thread is managed by the android platform and the SDK user has nothing to do to manage it.

•	The SDK allows the application developer to receive response messages asynchronously in a fashion that is similar to the Java Enterprise Message Beans.

# 1.2	Dependencies

The SDK implementation makes use of retrofit and gson libraries. Therefore, the following gradle dependencies must be specified in the project build.gradle configuration file within the dependencies section:

		implementation 'com.squareup.retrofit2:retrofit:2.4.0'

		implementation 'com.squareup.retrofit2:converter-scalars:2.4.0'

		implementation 'com.squareup.retrofit2:converter-gson:2.4.0'


# 2.	ARCHITECTURE

The SDK provides a high level API for sending/receiving requests to Kuveyt Türk API server by using the combination Factory and Façade design patterns in order to minimize the effort for the SDK user to code. The classes that the SDK user needs are located under the following two packages:

tr.com.kuveytturk.android.sdk.api

tr.com.kuveytturk.android.sdk.api.util

The classes and interfaces in the API package are a set of factory and facade classes/interfaces that help SDK users make requests to the Kuveyt Türk API server in a non-blocking way. The descriptions of these classes are as follows:

# 2.1	Classes in Package tr.com.kuveytturk.android.sdk.api

The classes that are provided in this package are the primary sources for the SDK user to make requests towards Kuveyt Türk API server. They provide high-level API handles that let SDK users make authorization, access token, GET, POST requests without having to know the communication details to Kuveyt Türk API server


# 2.1.1 Interface FacadeFactoryInterface

This interface provides a template that allows the SDK user to create facade instances (i.e. handles) for accessing to the Kuveyt Turk API server. The concrete method implementations for this interface are provided in the class FacadeFactory. These method definitions are listed as follows:

•	AuthorizationFacade getAuthorizationFacade(): The factory method get a handle to the authorization handler facade.

•	AccessTokenFacade getAccessTokenFacade(): The factory method get a handle to the Access token handler facade.

•	GetRequestFacade getGetFacade(): The factory method get a handle to the GET request handler facade.

•	PostRequestFacade getPostFacade():The factory method get a handle to the GET request handler facade.


# 2.1.2 Class FacadeFactory
This class provides implementations for the methods that are specified in FacadeFactoryInterface. These method implementations help SDK users create facade instances (i.e. handles) for accessing to the Kuveyt Turk API server. The generic type T extends the class android.app.Activity in Android SDK and implements the interface ResponseHandlingFacade. The type T represents the type of the activity instance in an android studio project that makes use of our SDK and it is used internally by the SDK in order to deliver response messages to the activity instance. An example for the usage of type T has been provided under usage section below.


# 2.1.3 Interface AuthorizationFacade
This interface acts as a template for the SDK users to make OAuth authorization requests to the Kuveyt Türk identity server. The generic type T extends the class android.app.Activity in Android SDK and implements the interface ResponseHandlingFacade. The type T represents the type of the activity instance in an android studio project that makes use of our SDK and it is used internally by the SDK in order to deliver authorization related response messages to the activity instance. 

# 2.1.4	Interface AccessTokenFacade
This interface acts as a template for the SDK users to make access token requests to the Kuveyt Türk identity server. The generic type T extends the class android.app.Activity in Android SDK and implements the interface ResponseHandlingFacade. The type T represents the type of the activity instance in an android studio project that makes use of our SDK and it is used internally by the SDK in order to deliver access token related response messages to the activity instance.

# 2.1.5	Interface GetRequestFacade
This interface acts as template in order of the SDK users to make GET requests to the Kuveyt Türk API server. The generic type T extends the class android.app.Activity in Android SDK and implements the interface ResponseHandlingFacade. The type T represents the type of the activity instance in an android studio project that makes use of our SDK and it is used internally by the SDK in order to deliver access token related response messages to the activity instance.

# 2.1.6	Interface PostRequestFacade
This interface acts as template in order of the SDK users to make POST requests to the Kuveyt Türk API server. The generic type T extends the class android.app.Activity in Android SDK and implements the interface ResponseHandlingFacade. The type T represents the type of the activity instance in an android studio project that makes use of our SDK and it is used internally by the SDK in order to deliver access token related response messages to the activity instance.

# 2.1.7	Interface ResponseHandlingFacade
This interface acts as template in order of the SDK users to receive response messages coming from the Kuveyt Türk API server in their app implementation.


# 2.2 Classes in Package tr.com.kuveytturk.android.sdk.api.util

# 2.2.1 Class AccessConfigurationBean

This class conforms to Java Bean standard and provides fields for accessing Kuveyt Türk identity server and API server.  These fields are listed as follows:

•	baseAuthorizationUrl: Holds the base backend URL to Kuveyt Türk identity server at which  authorization requests are sent (e.g. https://idprep.kuveytturk.com.tr/). The default value for this field is https://idprep.kuveytturk.com.tr/ in case default constructor is used.

•	authorizationEndPoint: Holds the authorization endpoint at which the authorization requests are targeted (e.g. api/connect/authorize). Default is set to api/connect/authorize in case the default constructor is used.

•	baseAccessTokenUrl: Holds the base backend URL to Kuveyt Türk identity server at which  access token requests are sent (e.g. https://idprep.kuveytturk.com.tr/). The default value for this field is https://idprep.kuveytturk.com.tr/ in case default constructor is used.

•	accessTokenEndPoint: Holds the access token endpoint at which the access token related requests are targeted (e.g. api/connect/token). Default is set to api/connect/token in case the default constructor is used.

•	apiAccessUrl: Holds the base backend URL to Kuveyt Türk API server at which  GET/POST requests are sent (e.g. https://apitest.kuveytturk.com.tr/prep/). The default value for this field is https://apitest.kuveytturk.com.tr/prep/ in case default constructor is used.

The SDK user can optionally read the respective values for the above listed fields from a property file. If this is the case, we have provided an additional non-default constructor in the class AccessConfigurationBean for the use of the SDK user as follows:


       public AccessConfigurationBean(String baseAuthorizationUrl,
                                      String authorizationEndPoint,
			 				          String baseAccessTokenUrl,
							          String accessTokenEndpoint,
							          String apiAccessUrl)

# 2.2.2 Class AccessTokenResponseBean

This class contains fields that are present in the response message that is sent back from the Kuveyt Türk identity server for the corresponding access token related request that has been made earlier by using an instance of class AccessTokenFacade. These fields are listed as follows:

•	accessToken: Holds the access token that is returned from the identity server.

•	tokenType: Indicates how the access token may be used, which is always "Bearer".

•	Scope: Holds a space-separated list of scopes which have been granted for the access token.

•	ExpiresIn: The time period (in seconds) for which the access token is valid.

•	refreshToken: Holds the refresh token thst is snet by the identity server. Since access tokens have finite lifetimes, refresh tokens allow requesting new access tokens without user interaction.

•	error: Indicates a high level description of the error, if any, as specified in section 5.2 of RFC-6749 in section 5.2.

•	errorDescription: Indicates a more detailed description of the error, if any, as specified in section 4.1.2.1 of RFC-6749 .


# 2.2.3 Class SignatureGenerator

This class provides utility methods to generate signatures for the GET and POST requests that are listed below. An example about the usage of this class has been provided under the usage section. 

	    public static String generateSignatureForGetRequest(
										String accessToken,                                                    									
										String privateKeyAsString,										
										HashMap<String, Object> queryParams) throws SignatureGenerationException

	
	    public static String generateSignatureForPostRequest(
										String accessToken,										
										String privateKeyAsString,										
										String jsonBody) throws SignatureGenerationException 



# 2.2.4 Class SignatureGeneratorException

This class is a custom exception class that wraps Java’s standard java.lang.Exception class. An instance of this class is thrown per public utility method that is available in class SignatureGenerator, which are listed in section 2.2.3 above.

# 3.	USAGE

i)	To start using the SDK, an application developer firstly needs to create an android project with a basic activity that is possibly named as MainActivity, for example. 

ii)	In the project buil.gradle file, the application developer then has to add the SDK dependencies (i.e. Retrofit and gson dependencies) as shown in the following:

        dependencies {
           //Other dependencies added by the android studio automatically by default
           // Retrofit & Gson related dependencies	
           implementation 'com.squareup.retrofit2:retrofit:2.4.0'	
           implementation 'com.squareup.retrofit2:converter-scalars:2.4.0'	
           implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
        }


iii)	The class MainActivity that was created in step (i) previously must then implement the interface ResponseHandlingFacade that is located under the package tr.com.kuveytturk.android.sdk.api.

iv)	The class MainActivity must then implement the below listed methods to receive response messages that are related to authorization, access token retrieval, and GET/POST request handling. 

		void onGetResponseReceivedFromKTAPI(String responseMessage);

		void onPostResponseReceivedFromKTAPI(String responseMessage);

		void onAccessTokenReceived(AccessTokenResponseBean accessToken);

		void onAuthorizationCodeReceived(String authorizationCode, 
										 String state, 
										 String error);

v)	The class MainActivity must then create an instance of class FacadeFactory which provides factory methods that can be used by the SDK user to handle authorization, access token and GET/POST request handling. This can be done in the onCreate method of the class MainActivity as shown in the following:

       //BEGIN - KUVEYT TÜRK API HANDLEs

       FacadeFactoryInterface<MainActivity> ktFacadeFactory = new FacadeFactory<MainActivity>(this);

       mKTAuthHandlerFacade=ktFacadeFactory.getAuthorizationFacade();

       mKTAccessTokenHandlerFacade = ktFacadeFactory.getAccessTokenFacade();

       mKTGetRequestHandlerFacade =  ktFacadeFactory.getGetFacade();

       mKTPostRequestHandlerFacade = ktFacadeFactory.getPostFacade();

       //END - KUVEYT TÜRK API HANDLEs


vi)	The class MainActivity can then start authorization process within event handler as shown in the following code snippet which can be placed again within the onCreate method:

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

       fab.setOnClickListener(new View.OnClickListener() { 
       
	       @Override
           public void onClick(View view) {
                
				Snackbar.make(view, "Replace with your own action",  Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
				
				mKTAuthHandlerFacade.requestAuthorization(CLIENT_ID, RESPONSE_TYPE, REDIRECT_URI, SCOPE);
            }
	
        });


For further details, please review the class MainActivity that is located under the package tr.com.kuveytturk.android.sdk in our github android repository.


