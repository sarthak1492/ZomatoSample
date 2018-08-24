package com.sarthaksharma.zomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sarthaksharma.zomato.Database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignUpName;
    TextView txtSignUpEmail;
    TextView txtSignUpPassword;
    TextView txtMobileNo;

    DatabaseHelper myDB;
    String AES = "AES";

    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    SoapPrimitive soapResponse;

    String strRespomse;
    String strSignUpName, strSignUpEmail, strMobileNo, strSignUpPassword;

    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    String strFacebookFirstName, strFacebookLastName, strFacebookUserName, strFacebookEmail, strFacebookProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB = new DatabaseHelper(this);

        txtSignUpName = (TextView) findViewById(R.id.txtSignUpName);
        txtSignUpEmail = (TextView) findViewById(R.id.txtSignUpEmail);
        txtSignUpPassword = (TextView) findViewById(R.id.txtSignUpPassword);
        txtMobileNo = (TextView) findViewById(R.id.txtMobileNo);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#CD0000"));
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("Result", "Success");

                Bundle params = new Bundle();
                params.putString("fields", "id,first_name,last_name,name,email,gender,picture.type(large)");
                new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                if (response != null) {
                                    try {
                                        JSONObject data = response.getJSONObject();

                                        if (data.has("first_name")) {
                                            strFacebookFirstName = data.getString("first_name");
                                        }

                                        if (data.has("last_name")) {
                                            strFacebookLastName = data.getString("last_name");
                                        }

                                        if (data.has("name")) {
                                            strFacebookUserName = data.getString("name");
                                        }

                                        if (data.has("email")) {
                                            strFacebookEmail = data.getString("email");
                                        }

                                        if (data.has("picture")) {
                                            strFacebookProfilePic = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        }

                                        Toast.makeText(SignUpActivity.this, strFacebookUserName, Toast.LENGTH_SHORT).show();

                                        if (strFacebookUserName != null) {
                                            mEditor.putString("UserName", strFacebookUserName);
                                        }
                                        if (strFacebookProfilePic != null) {
                                            mEditor.putString("ImageUrl", strFacebookProfilePic);
                                        }
                                        mEditor.commit();
                                        Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                                        startActivity(intent);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void fncSignUpFacebookAuthenticaton(View view) {
        LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void fncSignUpGoogleAuthentication(View view) {
        signInWithGoogle();
    }

    public void fncNormalSignUp(View view) throws Exception {
        strSignUpName = txtSignUpName.getText().toString();
        strSignUpEmail = txtSignUpEmail.getText().toString();
        strSignUpPassword = txtSignUpPassword.getText().toString();
        strMobileNo = txtMobileNo.getText().toString();

        SignUpUser signUpUser = new SignUpUser();
        try {
            signUpUser.execute();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error during Sign up", Toast.LENGTH_SHORT).show();
        }

    }

    public class SignUpUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String URL = "http://192.168.0.37:54950/ZomatoWebService.asmx";
            String NAMESPACE = "http://tempuri.org/";
            String METHODNAME = "fncSignUpUser";
            String SOAPACTION = "http://tempuri.org/fncSignUpUser";

            try {
                SoapObject soapObject = new SoapObject(NAMESPACE, METHODNAME);
                soapObject.addProperty("strName", strSignUpName);
                soapObject.addProperty("strEmail", strSignUpEmail);
                soapObject.addProperty("iMobileNo", strSignUpPassword);
                soapObject.addProperty("strPassword", strMobileNo);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);

                HttpTransportSE transportSE = new HttpTransportSE(URL, 100000);
                transportSE.debug = true;
                transportSE.call(SOAPACTION, envelope);

                soapResponse = (SoapPrimitive) envelope.getResponse();

                strRespomse = soapResponse.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (strRespomse.equals("true")) {
                Toast.makeText(SignUpActivity.this, "Data has been inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // FOR FACEBOOK AUTHENTICATION
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        // FOR GOOGLE AUTHENTICATION
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Result", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.i("Result", "display name: " + acct.getDisplayName());

            String strGoogleUserName = acct.getDisplayName();
            String strGoogleFirstName = acct.getGivenName();
            String strGoogleLastName = acct.getFamilyName();

            String imgGoogleUserUrl = null;
            if (acct.getPhotoUrl().toString() != null || !acct.getPhotoUrl().toString().equals("")) {
                imgGoogleUserUrl = acct.getPhotoUrl().toString();
            } else if (imgGoogleUserUrl == null) {
                imgGoogleUserUrl = "";
            }

            String GoogleEmail = acct.getEmail();

            if (strGoogleUserName != null) {
                mEditor.putString("UserName", strGoogleUserName);
            }
            if (imgGoogleUserUrl != null) {
                mEditor.putString("ImageUrl", imgGoogleUserUrl);
            }
            mEditor.commit();

            Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
            startActivity(intent);


        } else {
            Toast.makeText(this, "Could not Sign Up", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 9001);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void fncNavigateToHomePage(View view) {
        Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
        startActivity(intent);

    }

    public void fncNavigateToLoginPageFromSignUp(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    private SecretKeySpec generateKey(String password) throws Exception {

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;

    }

    public String fncEncrypt(String strClearText, String strPassword) throws Exception {

        SecretKeySpec keySpec = generateKey(strPassword);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encVal = cipher.doFinal(strClearText.getBytes());
        String encryptedText = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedText;
    }


    public String fncDecrypt(String outputString, String password) throws Exception {

//        SecretKeySpec skeySpec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
//        Cipher cipher = Cipher.getInstance("Blowfish");
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//        byte[] decrypted = cipher.doFinal(encrypted);
//        return new String(decrypted);

        SecretKeySpec keySpec = generateKey(password);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodeValue = Base64.decode(outputString, Base64.DEFAULT);
        byte[] decValue = cipher.doFinal(decodeValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }

}
