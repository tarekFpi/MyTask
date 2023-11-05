package com.example.myassessment.repository

import androidx.lifecycle.MutableLiveData
import com.example.myassessment.model.profile.ProfileRequest
import com.example.myassessment.model.profile.ProfileResponse
import com.example.myassessment.retrofit.ApiService
import com.example.myassessment.utils.Resource
import com.example.myassessment.utils.TokenManager
import org.json.JSONObject
import javax.inject.Inject

class ProfileRepository @Inject constructor(val apiService: ApiService,
                                            private val tokenManager: TokenManager
){

    private var _profileResponseLiveData = MutableLiveData<Resource<ProfileResponse>>()
    val  profileResponseLiveData : MutableLiveData<Resource<ProfileResponse>>
        get() =_profileResponseLiveData


    suspend  fun getProfileList(){

        val userlistRequest = ProfileRequest(tokenManager.getLoginId()!!, tokenManager.getToken()!!)

        val  response=  apiService.postUserList(userlistRequest)

        try {
            _profileResponseLiveData.postValue(Resource.Loading())

            if(response.isSuccessful && response.body() != null){

                _profileResponseLiveData.postValue(Resource.Success(response.body()!!))

            } else if(response.errorBody()!=null){
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _profileResponseLiveData.postValue(Resource.Error(errorObj.toString()))
            }
            else{
                _profileResponseLiveData.postValue(Resource.Error("Something Went Wrong"))
            }

        } catch(e: Exception) {

            _profileResponseLiveData.postValue(Resource.Error(e.message))
        }

    }


}