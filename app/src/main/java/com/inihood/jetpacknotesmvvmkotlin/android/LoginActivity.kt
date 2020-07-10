package com.inihood.jetpacknotesmvvmkotlin.android

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.email
import kotlinx.android.synthetic.main.activity_register.password

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener {
            init()
        }

        reg_txt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private fun clearTextFields(){
        email.text.clear()
        passwor.text.clear()
    }

    private fun init() {
        var email = email.text.toString()
        var passwor = passwor.text.toString()
        if (!email.isNullOrBlank() || !passwor.isNullOrBlank()){
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Just a sec...")
        progressDialog.setMessage("Registering...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, passwor).
            addOnCompleteListener{
                if(!it.isSuccessful)return@addOnCompleteListener
                progressDialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
            clearTextFields()
            progressDialog.dismiss()
            val toast = Toast.makeText(this, "There was problem while registering: ${it.message}", Toast.LENGTH_LONG)
            toast.show()
        }
    }else{
        val toast = Toast.makeText(this, "Something is not right", Toast.LENGTH_LONG)
        toast.show()
    }
}

}
