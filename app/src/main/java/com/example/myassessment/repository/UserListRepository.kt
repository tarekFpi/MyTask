package com.example.myassessment.repository

import androidx.lifecycle.MutableLiveData
import com.example.myassessment.model.userlist.UserListRequest
import com.example.myassessment.model.userlist.UserResponseItem
import com.example.myassessment.retrofit.ApiService
import com.example.myassessment.utils.Resource
import com.example.myassessment.utils.TokenManager
import org.json.JSONObject
import javax.inject.Inject

class UserListRepository @Inject constructor(val apiService: ApiService,
                                             private val tokenManager: TokenManager){


    private var _userlistResponseLiveData = MutableLiveData<Resource<List<UserResponseItem>>>()
    val  userResponseLiveData : MutableLiveData<Resource<List<UserResponseItem>>>
        get() =_userlistResponseLiveData


    suspend  fun getUserList(){

      val userlistRequest = UserListRequest(tokenManager.getLoginId()!!, tokenManager.getToken()!!)

        val  response=  apiService.postUserList(userlistRequest)

        try {
            _userlistResponseLiveData.postValue(Resource.Loading())

            if(response.isSuccessful && response.body() != null){

           _userlistResponseLiveData.postValue(Resource.Success(response.body()!!))

            } else if(response.errorBody()!=null){
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _userlistResponseLiveData.postValue(Resource.Error(errorObj.toString()))
            }
            else{
                _userlistResponseLiveData.postValue(Resource.Error("Something Went Wrong"))
            }

        } catch(e: Exception) {

            _userlistResponseLiveData.postValue(Resource.Error(e.message))
        }

    }

}