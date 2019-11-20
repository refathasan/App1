package com.refathasan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {
    private static String TAG ="MainActivity";
    static {
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"Open CV connected successfully");
        }else {
            Log.d(TAG,"Failed!!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
