package com.inihood.jetpacknotesmvvmkotlin.android

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val code = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_reg.setOnClickListener {
            init()
        }

        select_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, code)
        }

        login_txt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clearTextFields(){
        email.text.clear()
        password.text.clear()
    }

    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == code && data != null){
                selectedPhotoUri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
                val bitmapDrawable = BitmapDrawable(bitmap)
                select_photo.setBackgroundDrawable(bitmapDrawable)
            }
        }
    }

    private fun init() {
        var email = email.text.toString()
        var password = password.text.toString()
        if (!email.isNullOrBlank() || !password.isNullOrBlank()){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Just a sec...")
            progressDialog.setMessage("Registering...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).
                addOnCompleteListener{
                    if(!it.isSuccessful)return@addOnCompleteListener
                    uploadImage()
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

    private fun uploadImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}
