package com.example.myassessment.retrofit

import com.example.myassessment.model.auth.LoginResponse
import com.example.myassessment.model.auth.UserRequest
import com.example.myassessment.model.profile.ProfileRequest
import com.example.myassessment.model.profile.ProfileResponse
import com.example.myassessment.model.userlist.UserListRequest
import com.example.myassessment.model.userlist.UserResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    suspend fun postLogin(@Body userRequest: UserRequest) : Response<LoginResponse>


    @POST("api/ClientCabinetBasic/GetOpenTrades")
    suspend fun postUserList(@Body userListRequest: UserListRequest) : Response<List<UserResponseItem>>

    @POST("api/ClientCabinetBasic/GetAccountInformation")
    suspend fun postUserList(@Body profileRequest: ProfileRequest) : Response<ProfileResponse>

/*
    @POST("api/ClientCabinetBasic/GetLastFourNumbersPhone")
    suspend fun postLastNumber(@Body profileRequest: ProfileRequest) : Response<lastnumber>
*/

}