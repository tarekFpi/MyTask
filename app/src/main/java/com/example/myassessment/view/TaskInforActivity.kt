package com.example.myassessment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myassessment.R

class TaskInforActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView

    private lateinit var textViewGender: TextView

    private lateinit var textViewHome: TextView

    private lateinit var textViewSpecies: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_infor)

        textViewName =findViewById(R.id.text_infoName)
        textViewGender =findViewById(R.id.text_infoGender)
        textViewHome =findViewById(R.id.text_infoHouse)
        textViewSpecies =findViewById(R.id.text_infoSpecies)

        var bundle :Bundle ?=intent.extras

        if (bundle != null) {

            textViewName.text="Name:"+ bundle!!.getString("name")!!.toString()

            textViewSpecies.text="Species"+ bundle!!.getString("species")!!.toString()

            textViewGender.text="Gender"+ bundle!!.getString("gender")!!.toString()

            textViewHome.text="House"+ bundle!!.getString("house")!!.toString()

        }

    }

}