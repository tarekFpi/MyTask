package com.example.myassessment.view

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.myassessment.MainActivity
import com.example.myassessment.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class TaskDowanlodActivity : AppCompatActivity() {


   private lateinit var youTubePlayerView: YouTubePlayerView

   private lateinit var downloadBtn: ExtendedFloatingActionButton

    private lateinit var  progressDialog: ProgressDialog

    private var TAG="TAG"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_dowanlod)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait...")
        progressDialog.show()

        downloadBtn = findViewById(R.id.downlodVidoeBtn)

         youTubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                youTubePlayer.loadVideo("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4", 0f)

                progressDialog.dismiss()
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {

                super.onStateChange(youTubePlayer, state)
            }
        })

        downloadBtn.setOnClickListener {

            Toast.makeText(this, "Start Download", Toast.LENGTH_SHORT).show()

            GlobalScope.launch {

                downloadFromUrl("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")

            }
        }

    }



    private fun downloadFromUrl(url: String) {
        try {

            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle("Download")
            request.setAllowedOverRoaming(false)
            request.setDescription("Downloading Your File")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                System.currentTimeMillis().toString()
            )

            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

        } catch (exception: Exception) {

         Log.d(TAG,"${exception}")
        }
    }

}