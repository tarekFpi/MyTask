package com.example.myassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;

public class MainActivity2 extends AppCompatActivity {

    private String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ProgressBack PB = new ProgressBack();
        PB.execute();
    }

    private class ProgressBack extends AsyncTask<String,String,String> {
        ProgressDialog PD;
        @Override
        protected void onPreExecute() {
            PD= ProgressDialog.show( MainActivity2.this,null, "Please Wait ...", true);
            PD.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... arg0) {
          //  downloadFile("http://beta-vidizmo.com/hilton.mp4","Sample.mp4");

            downloadFile("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4","Sample.mp4");

             return null;
        }
        protected void onPostExecute(Boolean result) {
            PD.dismiss();

        }

    }

    private void downloadFile(String fileURL, String fileName) {
        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();

            Intent activityIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "CHANNEL_2_ID")
                    .setSmallIcon(R.drawable.baseline_download_for_offline_24)
                    .setContentTitle("Download")
                    .setContentText("Downloading in process")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(contentIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification.build());

            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[4096];

            int readfile = 0;

            long fileSizeDownloaded = 0;

            while ((readfile = in.read(buffer)) > 0) {
                f.write(buffer, 0, readfile);

                fileSizeDownloaded += readfile;

                Log.e(TAG, "file download: " + fileSizeDownloaded + " of " +readfile );
                Long fileSizeD = fileSizeDownloaded;
                Long fileS  = Long.valueOf(readfile);
                int down = fileSizeD.intValue();
                int max  = fileS.intValue();
                Log.e(TAG, "MAX: " + max + " Down: "+ down);
                notification.setProgress(max, down, false);
                notificationManager.notify(1, notification.build());
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }

    }

    private boolean saveFile(ResponseBody fileURL, String fileName) {

        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;


          //  final int idd = Integer.parseInt(id);

            Intent activityIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
            final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "CHANNEL_2_ID")
                    .setSmallIcon(R.drawable.baseline_download_for_offline_24)
                    .setContentTitle("Download")
                    .setContentText("Downloading in process")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(contentIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification.build());

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = fileURL.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = fileURL.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {

                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.e(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    Long fileSizeD = fileSizeDownloaded;
                    Long fileS     = fileSize;
                    int down = fileSizeD.intValue();
                    int max  = fileS.intValue();
                    Log.e(TAG, "MAX: " + max + " Down: "+ down);
                    notification.setProgress(max, down, false);
                    notificationManager.notify(1, notification.build());
                }

                outputStream.flush();

                notification.setProgress(0, 0, false);
                notification.setContentTitle("Downloasdfasad");
                notification.setOngoing(false);
                notification.setPriority(NotificationCompat.PRIORITY_HIGH);
                notificationManager.notify(1, notification.build());
                Log.e(TAG, "1");

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();

                }
            }
        } catch (IOException e) {
            return false;
        }

    }
}