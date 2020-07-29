package com.example.videoplayersimple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.khizar1556.mkvideoplayer.MKPlayerActivity;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adp;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    Boolean aBooleanPermission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        //phone and sd card
        directory = new File("/mnt/");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        permissionForVideo();


    }

    private void permissionForVideo() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        } else {
            aBooleanPermission = true;
            getFiles(directory);
            Toast.makeText(this, fileArrayList.size() + "", Toast.LENGTH_LONG).show();
            adp = new MyAdapter(getApplicationContext(), fileArrayList);
            recyclerView.setAdapter(adp);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                aBooleanPermission = true;
                getFiles(directory);
                Toast.makeText(this, fileArrayList.size() + "", Toast.LENGTH_LONG).show();
                adp = new MyAdapter(getApplicationContext(), fileArrayList);
                recyclerView.setAdapter(adp);
            } else {
                Toast.makeText(this, "Please Allow The Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<File> getFiles(File directory) {

        File listFile[] = directory.listFiles();
        if (listFile != null && listFile.length > 0) {

            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getFiles(listFile[i]);
                } else {
                    aBooleanPermission = false;
                    if (listFile[i].getName().endsWith(".mp4")) {
                        for (int j = 0; j < fileArrayList.size(); j++) {
                            if (fileArrayList.get(j).getName().equals(listFile[i].getName())) {
                                aBooleanPermission = true;
                            } else {
                            }
                        }
                        if (aBooleanPermission) {
                            aBooleanPermission = false;
                        } else {
                            fileArrayList.add(listFile[i]);
                        }

                    }
                }

            }

        }

        return fileArrayList;

    }


//    private void pickVideo() {
//
//        new VideoPicker.Builder(MainActivity.this)
//                .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
//                .directory(VideoPicker.Directory.DEFAULT)
//                .extension(VideoPicker.Extension.MP4)
//                .enableDebuggingMode(true)
//                .build();
//
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
//            List<String> mPaths = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
//            //Your Code
//
//            MKPlayerActivity.configPlayer(MainActivity.this).play(mPaths.get(0));
//        }
//    }


}