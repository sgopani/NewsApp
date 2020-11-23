package com.example.newsapp.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapp.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class UserProfile :Fragment() {
    private lateinit var userProfileView: View
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userProfileView=inflater.inflate(R.layout.user_profile, container, false)
        activity?.title="User Profile"
        val user=FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userImage=userProfileView.findViewById<ImageView>(R.id.user_image)
            val imgUri=user.photoUrl
            Glide.with(userImage.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_baseline_person_24)
                        .fitCenter().override(600, 200)
                )
                .into(userImage)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
            val userName=userProfileView.findViewById<TextView>(R.id.profile_name)
            userName.text=user.displayName
            val userEmail=userProfileView.findViewById<TextView>(R.id.email_id_textView)
            userEmail.text=user.email
            val userUid=userProfileView.findViewById<TextView>(R.id.phone_no_textView)
            userUid.text=user.uid
            val signOut=userProfileView.findViewById<Button>(R.id.sign_out_button)
            signOut.setOnClickListener {
                signOutUser()
                    val intent=Intent(this.requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this.context, "Signed Out", Toast.LENGTH_SHORT).show()
            }
            }
        return userProfileView
    }
    private fun signOutUser(){
        val auth=FirebaseAuth.getInstance()
        auth.signOut()
        googleSignInClient.signOut()
        LoginManager.getInstance().logOut()

    }
}


