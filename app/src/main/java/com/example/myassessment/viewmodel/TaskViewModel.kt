package com.example.myassessment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassessment.model.TaskResponseItem
import com.example.myassessment.repository.TaskRepository
import com.example.myassessment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {


    private var _response = MutableStateFlow<NetworkResult<List<TaskResponseItem>>>(NetworkResult.Loading())
    val postResponse: StateFlow<NetworkResult<List<TaskResponseItem>>> = _response


    init {

        viewModelScope.launch(Dispatchers.Main){

            taskRepository.showTaskList().onStart {

                _response.emit(NetworkResult.Loading())

            }.catch {

                _response.emit(NetworkResult.Error(it.message))

            }.collect{

                _response.emit(NetworkResult.Success(it))
            }
        }
    }


}