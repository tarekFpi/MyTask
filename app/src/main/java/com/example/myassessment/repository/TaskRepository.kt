package com.example.myassessment.repository

import com.example.myassessment.model.TaskResponseItem
import com.example.myassessment.retrofit.ApiService
import com.example.myassessment.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject


class TaskRepository  @Inject constructor(val apiService:ApiService){


   fun showTaskList() = flow {
       emit(apiService.getShowTask())
    }.flowOn(Dispatchers.IO)



}