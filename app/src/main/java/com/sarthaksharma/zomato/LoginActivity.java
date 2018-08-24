package com.sarthaksharma.zomato;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    String strFacebookFirstName, strFacebookLastName, strFacebookUserName, strFacebookEmail, strFacebookProfilePic;
    TextView txtLoginEmail, txtLoginPassword;
    String strLoginEmail, strLoginPassword;
    DatabaseHelper myDB;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    private KeyStore keyStore;
    private static final String KEY_NAME = "fingerprint";
    private Cipher cipher;
    TextView lblCancel;

    KeyguardManager keyguardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#CD0000"));
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtLoginEmail = (TextView) findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (TextView) findViewById(R.id.txtLoginPassword);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
//            if (!fingerprintManager.isHardwareDetected()){
//                Log.e("Detection", "Hardware not found");
//            }else if (!fingerprintManager.hasEnrolledFingerprints()){
//                Log.e("Enrolls", "No fingerprints found");
//            }else {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
//                final View convertView = inflater.inflate(R.layout.layout_fingerprint_scan, null);
//                builder.setView(convertView);
//                builder.setCancelable(false);
//
//                lblCancel = (TextView) convertView.findViewById(R.id.lblCancel);
//                final AlertDialog b = builder.create();
//                lblCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        b.dismiss();
//                    }
//                });
//
//
//                b.show();
//
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
//                    Log.e("A", "Fingerprint authentication permission not enabled");
//                }else {
//                    if (!fingerprintManager.hasEnrolledFingerprints()){
//                        Log.e("B", "Register at least one fingerprint in Settings");
//                    }else {
//                        if (!keyguardManager.isKeyguardSecure()){
//                            Log.e("C", "Lock screen security not enabled in Settings");
//                        }else{
//                            generateKey();
//
//                            if (cipherInit()){
//                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                                FingerprintHandler helper = new FingerprintHandler(this);
//                                helper.startAuth(fingerprintManager, cryptoObject);
//                            }
//
//                        }
//                    }
//                }
//            }
//        }

//        if (!fingerprintManager.isHardwareDetected()){
//            Log.e("Hardware", "Finger print sensor not detected");
//            return;
//        }else {

//            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_SHORT).show();
//            }else if (!fingerprintManager.hasEnrolledFingerprints()){
//                Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();
//            }else if (!keyguardManager.isKeyguardSecure()){
//                Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
//            }else {
//                generateKey();
//                if (cipherInit()) {
//                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                    FingerprintHandler helper = new FingerprintHandler(this);
//                    helper.startAuth(fingerprintManager, cryptoObject);
//                }
//            }
//        }


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
                                if (response != null){
                                    try {
                                        JSONObject data = response.getJSONObject();

                                        if (data.has("first_name")){
                                            strFacebookFirstName = data.getString("first_name");
                                        }

                                        if (data.has("last_name")){
                                            strFacebookLastName = data.getString("last_name");
                                        }

                                        if (data.has("name")) {
                                            strFacebookUserName = data.getString("name");
                                            Toast.makeText(LoginActivity.this, "Welcome " + strFacebookUserName, Toast.LENGTH_SHORT).show();
                                        }

                                        if (data.has("email")) {
                                            strFacebookEmail = data.getString("email");
                                        }

                                        if (data.has("picture")) {
                                            strFacebookProfilePic = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        }

                                        Toast.makeText(LoginActivity.this, "Welcome! " + strFacebookUserName, Toast.LENGTH_SHORT).show();

                                        if (strFacebookUserName != null){
                                            mEditor.putString("UserName", strFacebookUserName);
                                        }
                                        if (strFacebookProfilePic != null){
                                            mEditor.putString("ImageUrl", strFacebookProfilePic);
                                        }
                                        mEditor.commit();
                                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                        startActivity(intent);


                                    }catch (Exception e){
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

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public void fncNormalLogin(View view) throws Exception {
        strLoginEmail = txtLoginEmail.getText().toString();
        strLoginPassword = txtLoginPassword.getText().toString();

        String strCheckPassword = fncEncrypt(strLoginPassword, "Blowfish");
        if (strCheckPassword.equals(myDB.fncGetUserValue("Password"))){
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
        }

    }

    public void fncLoginFacebookAuthenticaton(View view){
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void fncLoginGoogleAuthentication(View view){
        signInWithGoogle();
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

            if (strGoogleUserName != null){
                mEditor.putString("UserName", strGoogleUserName);
            }
            if (imgGoogleUserUrl != null){
                mEditor.putString("ImageUrl", imgGoogleUserUrl);
            }
            mEditor.commit();

            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
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

    public static String fncEncrypt(String strClearText, String strKey) throws Exception{
        String strData = "";
        try {
            SecretKeySpec sKeySpec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] encrypted = cipher.doFinal(strClearText.getBytes());
            strData = new String(encrypted);
        }catch (Exception e){
            e.printStackTrace();
        }
        return strData;
    }

    public static String fncDecrypt(String strEecrypted, String strKey) throws Exception {

        String strData = "";
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(strEecrypted.getBytes());
            strData = new String(decrypted);
        }catch (Exception e){
            e.printStackTrace();
        }

        return strData;

    }

    public void fncNavigateToForgotPassword(View view){
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
