package com.example.myassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassessment.model.userlist.UserResponseItem
import com.example.myassessment.repository.UserListRepository
import com.example.myassessment.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRepository: UserListRepository) : ViewModel() {

    val userlistLiveData : LiveData<Resource<List<UserResponseItem>>>
        get() = userListRepository.userResponseLiveData

    init {
        showUserlist()
    }

    fun showUserlist(){

        viewModelScope.launch(Dispatchers.Main){

            userListRepository.getUserList()

        }

    }
}