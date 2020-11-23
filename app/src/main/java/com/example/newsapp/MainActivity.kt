package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.authentication.LoginActivity
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)
        val topLevelDestinations: Set<Int> = setOf(R.id.newsList2,
            R.id.newsSearchFragment,
            R.id.newsFavFragment,
            R.id.userProfile)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).build()
        val navController=this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {

            val user = FirebaseAuth.getInstance().currentUser
                if (user != null)
                {
                    Log.i("User Token ", user.uid)
                }
                else{
                   val intent=Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
//            }
//        }
      super.onStart()
    }
}

//package com.example.newsapp.authentication
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.newsapp.R
//import com.facebook.*
//import com.facebook.FacebookSdk.getApplicationContext
//import com.facebook.login.LoginResult
//import com.facebook.login.widget.LoginButton
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.SignInButton
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.FacebookAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class Login():Fragment() {
//    private lateinit var auth: FirebaseAuth
//    // [END declare_auth]
//    private lateinit var loginView: View
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var callbackManager: CallbackManager
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//
//        //keytool -exportcert -alias androiddebugkey -keystore "C:\Users\Shubh\.android\debug.keystore" | "C:\Program Files\Java\jdk-15\bin\openssl" sha1 -binary | "C:\Program Files\Java\jdk-15\bin\bin\openssl" base64
//        FacebookSdk.sdkInitialize(getApplicationContext())
//        loginView=inflater.inflate(R.layout.login_fragment, container, false)
//        activity?.title="Login"
//
//        val facebookSignInButton = loginView.findViewById<LoginButton>(R.id.facebook_sign_in_button)
//        callbackManager = CallbackManager.Factory.create()
////        facebookSignInButton.setOnClickListener {
////            Toast.makeText(this.context,"Clicked",Toast.LENGTH_SHORT).show()
////        }
//        facebookSignInButton.setPermissions("email", "public_profile")
//        facebookSignInButton.setOnClickListener {
//            facebookSignInButton.registerCallback(callbackManager, object :
//                FacebookCallback<LoginResult>{
//                override fun onSuccess(result: LoginResult?) {
//                    handleFacebookAccessToken(result!!.accessToken)
//                    Log.i("Facebook Token","$result!!.accessToken")
//                }
//
//                override fun onCancel() {
//                    updateUI(null)
//                }
//
//                override fun onError(error: FacebookException?) {
//                    Log.i("Facebook Token","Error")
//                    updateUI(null)
//                }
//            })
//        }
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)
//        auth = Firebase.auth
//        return loginView
//    }
//    private fun handleFacebookAccessToken(token: AccessToken) {
//        Log.d(TAG, "handleFacebookAccessToken:$token")
//        // [START_EXCLUDE silent]
//        //showProgressBar()
//        // [END_EXCLUDE]
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this.requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    //Toast.makeText(baseContext, "Authentication failed.",
//                    //  Toast.LENGTH_SHORT).show()
//                    updateUI(null)
//                }
//
//                // [START_EXCLUDE]
//                //hideProgressBar()
//                // [END_EXCLUDE]
//            }
//    }
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//    private fun signOut() {
//        // Firebase sign out
//        auth.signOut()
//        // Google sign out
//        googleSignInClient.signOut().addOnCompleteListener(this.requireActivity()) {
//            updateUI(null)
//        }
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//        super.onActivityResult(requestCode, resultCode, data)
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == Companion.RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                //Toast.makeText(this.context,"${account.idToken}",Toast.LENGTH_SHORT).show()
//                firebaseAuthWithGoogle(account.idToken!!)
//
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e)
//                // [START_EXCLUDE]
//                updateUI(null)
//                // [END_EXCLUDE]
//            }
//        }
//    }
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        // [START_EXCLUDE silent]
//        //showProgressBar()
//        // [END_EXCLUDE]
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential).addOnCompleteListener(this.requireActivity()) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "signInWithCredential:success")
//                val user = auth.currentUser
//                updateUI(user)
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                // [START_EXCLUDE]
//                // [END_EXCLUDE]
//                updateUI(null)
//            }
//
//            // [START_EXCLUDE]
//            //hideProgressBar()
//            // [END_EXCLUDE]
//        }
//    }
//    private fun updateUI(user: FirebaseUser?) {
//        val signIn: SignInButton=loginView.findViewById(R.id.google_button)
//        signIn.setOnClickListener {
//            //Toast.makeText(this.context,"Clicked",Toast.LENGTH_SHORT).show()
//            signIn()
//        }
//        val signOut:Button=loginView.findViewById(R.id.sign_out_button)
//        signOut.setOnClickListener {
//            signIn.visibility=View.VISIBLE
//            Toast.makeText(this.context,"Signed out",Toast.LENGTH_SHORT).show()
//            signOut()
//        }
//
//        //hideProgressBar()
//        if (user != null) {
//            signIn.visibility=View.GONE
//            signOut.visibility=View.VISIBLE
////            val dispText = loginView.findViewById<View>(R.id.login_textView) as TextView
////            dispText.text= user.email
////            dispText.text=user.displayName
//            Log.i("Token", user.uid)
//        } else {
//            signOut.visibility=View.GONE
//            //Toast.makeText(this.context, "No user Loged  in", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    companion object {
//        private const val TAG = "GoogleActivity"
//        private const val RC_SIGN_IN = 9001
//    }
//}