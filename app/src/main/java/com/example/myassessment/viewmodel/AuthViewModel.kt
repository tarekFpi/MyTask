package com.example.myassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassessment.model.auth.LoginResponse
import com.example.myassessment.model.auth.UserRequest
import com.example.myassessment.repository.AuthRepository
import com.example.myassessment.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {


     val userLoginLiveData : LiveData<Resource<LoginResponse>>
          get() = authRepository.authResponseLiveData

     fun loginRequest(userRequest: UserRequest){

        viewModelScope.launch{

      authRepository.doLoginUsingNetwork(userRequest)

        }

    }


}