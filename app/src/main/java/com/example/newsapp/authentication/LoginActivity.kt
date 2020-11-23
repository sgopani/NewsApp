package com.example.newsapp.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            auth = Firebase.auth
            callbackManager = CallbackManager.Factory.create()
            val facebookSignInButton =findViewById<LoginButton>(R.id.facebook_sign_in_button)
            facebookSignInButton.setPermissions("email", "public_profile")
            //Toast.makeText(this,"$callbackManager", Toast.LENGTH_SHORT).show()

            facebookSignInButton.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.i(TAG, "facebook:onSuccess:$loginResult")
                    hideViews()
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.i(TAG, "facebook:onCancel")
                    // [START_EXCLUDE]
                    updateUI(null)
                    showViews()
                    // [END_EXCLUDE]
                }

                override fun onError(error: FacebookException) {
                    Log.i(TAG, "facebook:onError", error)
                    // [START_EXCLUDE]
                    updateUI(null)
                    showViews()
                    // [END_EXCLUDE]
                }
            })
             val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Companion.RC_SIGN_IN) {
           val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                hideViews()
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                //Toast.makeText(this.context,"${account.idToken}",Toast.LENGTH_SHORT).show()
                firebaseAuthWithGoogle(account.idToken!!)

              } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                showViews()
                // [END_EXCLUDE]
           }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        //        // [START_EXCLUDE silent]
       showProgressBar()
//        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                // [START_EXCLUDE]
                // [END_EXCLUDE]
                updateUI(null)
            }
        }
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        //updateUI(user)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.i(TAG, "handleFacebookAccessToken:$token")
        // [START_EXCLUDE silent]
        showProgressBar()
        // [END_EXCLUDE]

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                hideProgressBar()
                // [END_EXCLUDE]
            }
    }
    private fun updateUI(user: FirebaseUser?) {
        val signIn: SignInButton=findViewById(R.id.google_button)
        signIn.setOnClickListener {
        signIn()
        }
        hideProgressBar()
        if (user != null) {
            Toast.makeText(this,"Welcome ${user.displayName}", Toast.LENGTH_SHORT).show()
            hideViews()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            showViews()
        }
    }
    companion object {
        private const val TAG = "FacebookLogin"
        private const val RC_SIGN_IN = 9001

    }
    private fun showProgressBar()
    {
        login_progress_bar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        login_progress_bar.visibility=View.GONE
    }
    fun hideViews(){
        login_textView.visibility=View.GONE
        login_image.visibility=View.GONE
        facebook_sign_in_button.visibility=View.GONE
        google_button.visibility=View.GONE
    }
    fun showViews(){
        login_textView.visibility=View.VISIBLE
        login_image.visibility=View.VISIBLE
        google_button.visibility=View.VISIBLE
        facebook_sign_in_button.visibility=View.VISIBLE
    }

}
