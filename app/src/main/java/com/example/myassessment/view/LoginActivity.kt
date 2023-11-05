package com.example.myassessment.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myassessment.R
import com.example.myassessment.model.auth.UserRequest
import com.example.myassessment.utils.CheckInternetConnection
import com.example.myassessment.utils.Resource
import com.example.myassessment.utils.TokenManager
import com.example.myassessment.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private  lateinit var editText_userName: EditText

    private  lateinit var editText_password: EditText

    private lateinit var  progressBar: ProgressDialog

    private lateinit var authViewModel: AuthViewModel

   @Inject
   lateinit var tokenManager: TokenManager

    @Inject
    lateinit var checkInternetConnection: CheckInternetConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = ProgressDialog(this)
        progressBar.setMessage("please wait...")


        editText_userName=findViewById(R.id.edit_userName)

        editText_password=findViewById(R.id.edit_userPass)

        authViewModel= ViewModelProvider(this)[AuthViewModel::class.java]


    }

    override fun onResume() {
        super.onResume()

        InternetConnection()
    }

   private  fun InternetConnection(){

        if (checkInternetConnection.isInternetAvailable(this)) {

            goToHome()
        }else{

            Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_SHORT).show()
        }

    }

    private fun goToHome() {

        if(!tokenManager.getToken().equals("")){

           val intent=Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(intent)
            this.finish()

        }

    }


    fun login_button(view: View) {

        if(editText_userName.text.toString().isEmpty()){

            editText_userName.setError("User Name is Empty!!")
            editText_userName.requestFocus()

        }else if(editText_password.text.toString().isEmpty()){

            editText_password.setError("Passwrod is Empty!!")
            editText_password.requestFocus()

        }else{


            var email = Integer.parseInt(editText_userName.text.toString());
            var pwd = editText_password.text.toString()

            progressBar.show()

            val userRequest = UserRequest(email,pwd)

            authViewModel.loginRequest(userRequest)

            bindObservers()

        }

    }

   private fun  bindObservers(){

          authViewModel.userLoginLiveData.observe(this, Observer {

              when (it) {

                  is Resource.Loading ->{

                      progressBar.show()
                  }

                  is Resource.Success -> {

                      Toast.makeText(applicationContext,"User logged in successfully",Toast.LENGTH_SHORT).show()

                       editText_userName.setText("")

                      editText_password.setText("")

                      val intent = Intent(this, HomePageActivity::class.java)
                      startActivity(intent)
                      this.finish()

                      progressBar.dismiss()
                  }
                  is Resource.Error -> {
                      progressBar.dismiss()
                      Toast.makeText(applicationContext,"Error ${it.message}",Toast.LENGTH_SHORT).show()
                  }

              }

          })
      }
}