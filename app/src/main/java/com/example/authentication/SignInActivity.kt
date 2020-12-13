package com.example.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.security.auth.login.LoginException


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
    }

    private fun init() {
        auth = Firebase.auth
        LogInButton.setOnClickListener {
            signin()

        }

    }

    private fun signin() {
        val email = emaileditText.text.toString()
        val password = passwordeditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            LogInButton.isClickable = false
            progressbar1.visibility = View.VISIBLE


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    LogInButton.isClickable = true
                    progressbar1.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        d("signin", "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Authentication is Success!", Toast.LENGTH_SHORT).show()

                    } else {
                        // If sign in fails, display a message to the user.
                        d("signin", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()


                    }


                }
        }else{
            Toast.makeText(this, "There is no user", Toast.LENGTH_SHORT).show()
        }
    }
}
