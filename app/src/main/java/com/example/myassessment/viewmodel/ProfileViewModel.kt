package com.example.myassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassessment.model.profile.ProfileResponse
import com.example.myassessment.repository.ProfileRepository
import com.example.myassessment.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {

    val profileLiveData : LiveData<Resource<ProfileResponse>>
        get() = profileRepository.profileResponseLiveData

    init {
        showProfile()
    }

    fun showProfile(){

        viewModelScope.launch(Dispatchers.Main){

            profileRepository.getProfileList()

        }

    }
}