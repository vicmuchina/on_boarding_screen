package com.example.shoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    //call the FirebaseAuth class
    private lateinit var auth: FirebaseAuth
    //variables to store users input
    var emailLogin: String = ""
    var passLogin: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //creating an instance of the firebase auth class
        auth = FirebaseAuth.getInstance()

        textView5.setOnClickListener {

            val intent = Intent(applicationContext,ForgotPassword::class.java)

            startActivity(intent)
        }

        create_account.setOnClickListener {
            val intent = Intent(applicationContext,CreateAccount::class.java)

            startActivity(intent)

        }
        appCompatButton2.setOnClickListener {

            captureInput()

        }

    }

    private fun captureInput() {
        emailLogin = editTextEmail.text.toString()
        passLogin = editTextPassword.text.toString()

        //validation of inputs
        if (emailLogin.isEmpty() && passLogin.isEmpty()){
            Toast.makeText(applicationContext,
                    "Fields cannot be empty", Toast.LENGTH_LONG).show()
        } else {
            loginUser(emailLogin,passLogin)
        }
    }

    private fun loginUser(emailLogin: String, passLogin: String) {

        auth.signInWithEmailAndPassword(emailLogin,passLogin)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(applicationContext,
                                "Login verified", Toast.LENGTH_LONG).show()
                        updateUi()
                    }else {
                        Toast.makeText(applicationContext,
                                "Login Failure " + it.exception , Toast.LENGTH_LONG).show()
                        Log.d("auth","details are " + it.exception)
                    }
                }
    }

    private fun updateUi() {
        val intent = Intent(applicationContext, UploadForm::class.java)
        startActivity(intent)
    }



}