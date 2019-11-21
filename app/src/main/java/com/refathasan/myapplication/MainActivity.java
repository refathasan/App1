package com.refathasan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static String TAG = "MainActivity";
    /*static {
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"Open CV connected successfully");
        }else {
            Log.d(TAG,"Failed!!");
        }
    }*/
    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    boolean startCanny  = false;
    Button Canny;
    /*public void Canny(View Button){

        if (startCanny == false){
            startCanny = true;
        }

        else{

            startCanny = false;


        }




    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Canny = (Button)findViewById(R.id.button);
        cameraBridgeViewBase = (JavaCameraView) findViewById(R.id.cameraView);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
                switch (status) {
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };

        Canny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startCanny == false){
                    startCanny = true;
                }

                else{

                    startCanny = false;


                }

            }
        });
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
       Mat frame = inputFrame.rgba();
        /*if(counter%2==0){
            Core.flip(frame,frame,1);
            Imgproc.cvtColor(frame,frame,Imgproc.COLOR_RGB2GRAY );
        }
        counter = counter+1;
        return frame;*/
        if(startCanny ==true){
            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2GRAY);
            Imgproc.Canny(frame, frame, 100, 80);
        }
        return frame;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(), "There's a problem, yo!", Toast.LENGTH_SHORT).show();
        }else{
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }
}
