package com.example.myassessment.utils

import com.example.myassessment.model.profile.ProfileResponse
import com.example.myassessment.model.userlist.UserResponseItem

sealed class Resource<T>(val data: T? = null, val message: String? = null,) {

    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()

}