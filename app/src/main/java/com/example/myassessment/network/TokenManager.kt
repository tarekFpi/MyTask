package com.example.myassessment.network

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences("PREFS_TOKEN_FILE", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString("USER_TOKEN", token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString("USER_TOKEN", null)
    }


    fun saveLoginId(loginId: Int) {
        val editor = prefs.edit()
        editor.putInt("Login_Id", loginId)
        editor.apply()
    }

    fun getLoginId(): Int? {
        return prefs.getInt("Login_Id", 0)
    }
}