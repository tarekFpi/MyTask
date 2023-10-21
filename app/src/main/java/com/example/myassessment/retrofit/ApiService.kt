package com.example.myassessment.retrofit

import com.example.myassessment.model.TaskResponseItem
import com.example.myassessment.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/characters")
    suspend fun getShowTask(): List<TaskResponseItem>
}