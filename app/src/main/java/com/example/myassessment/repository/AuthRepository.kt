package com.example.myassessment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myassessment.model.auth.LoginResponse
import com.example.myassessment.model.auth.UserRequest
import com.example.myassessment.retrofit.ApiService
import com.example.myassessment.utils.Resource
import com.example.myassessment.utils.TokenManager
import org.json.JSONObject

import javax.inject.Inject


class AuthRepository  @Inject constructor(val apiService:ApiService,private val tokenManager: TokenManager){



    private var _authResponseLiveData = MutableLiveData<Resource<LoginResponse>>()
    val  authResponseLiveData : LiveData<Resource<LoginResponse>>
     get() =_authResponseLiveData


     suspend  fun doLoginUsingNetwork(userRequest: UserRequest){

         val  response=  apiService.postLogin(userRequest)

         try {
             _authResponseLiveData.postValue(Resource.Loading())

             if(response.isSuccessful && response.body() != null){


                 _authResponseLiveData.postValue(Resource.Success(response.body()!!))

                 tokenManager.saveToken(response.body()!!.token)
                 tokenManager.saveLoginId(userRequest.login)

             } else if(response.errorBody()!=null){
                 val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                 _authResponseLiveData.postValue(Resource.Error(errorObj.getString("result")))
             }
             else{
                 _authResponseLiveData.postValue(Resource.Error("Something Went Wrong"))
             }

         } catch(e: Exception) {

             _authResponseLiveData.postValue(Resource.Error(e.message))
         }

     }

}