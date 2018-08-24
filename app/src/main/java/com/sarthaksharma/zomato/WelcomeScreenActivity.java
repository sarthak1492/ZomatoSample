package com.sarthaksharma.zomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.Arrays;

public class WelcomeScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    String strFacebookFirstName, strFacebookLastName, strFacebookUserName, strFacebookEmail, strFacebookProfilePic;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#808080"));
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login");

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

                                        Toast.makeText(WelcomeScreenActivity.this, "Welcome! " + strFacebookUserName, Toast.LENGTH_SHORT).show();

                                        if (strFacebookUserName != null){
                                            mEditor.putString("UserName", strFacebookUserName);
                                        }
                                        if (strFacebookProfilePic != null){
                                            mEditor.putString("ImageUrl", strFacebookProfilePic);
                                        }
                                        mEditor.commit();
                                        Intent intent = new Intent(WelcomeScreenActivity.this, HomePageActivity.class);
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
                Toast.makeText(WelcomeScreenActivity.this, "Could not Log in", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(WelcomeScreenActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fncNavigateToSignUpPage(View view) {
        Intent intent = new Intent(WelcomeScreenActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void fncNavigateToLoginPage(View view) {
        Intent intent = new Intent(WelcomeScreenActivity.this, LoginActivity.class);
        startActivity(intent);
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

    public void fncGoogleAuthentication(View view){
        signInWithGoogle();
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

            if (strGoogleUserName != null){
                mEditor.putString("UserName", strGoogleUserName);
            }
            if (imgGoogleUserUrl != null){
                mEditor.putString("ImageUrl", imgGoogleUserUrl);
            }
            mEditor.commit();

            Intent intent = new Intent(WelcomeScreenActivity.this, HomePageActivity.class);
            startActivity(intent);




        } else {
            Toast.makeText(this, "Could not Sign Up", Toast.LENGTH_SHORT).show();
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

    public void fncFacebookAuthentication(View view) {
        LoginManager.getInstance().logInWithReadPermissions(WelcomeScreenActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed", "onConnectionFailed:" + connectionResult);
    }
}
