package com.example.myassessment.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myassessment.R
import com.squareup.picasso.Picasso

class TaskInforActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView

    private lateinit var textViewGender: TextView

    private lateinit var textViewHome: TextView

    private lateinit var textViewSpecies: TextView

    private lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_infor)

        textViewName =findViewById(R.id.text_infoName)
        textViewGender =findViewById(R.id.text_infoGender)
        textViewHome =findViewById(R.id.text_infoHouse)
        textViewSpecies =findViewById(R.id.text_infoSpecies)

        imageView =findViewById(R.id.taskInfoImage)

        var bundle :Bundle ?=intent.extras

        if (bundle != null) {

            textViewName.text="Name:"+ bundle!!.getString("name")!!.toString()

            textViewSpecies.text="Species"+ bundle!!.getString("species")!!.toString()

            textViewGender.text="Gender"+ bundle!!.getString("gender")!!.toString()

            textViewHome.text="House"+ bundle!!.getString("house")!!.toString()

            Picasso.get().load(bundle!!.getString("image")).into(imageView)

        }

    }

}