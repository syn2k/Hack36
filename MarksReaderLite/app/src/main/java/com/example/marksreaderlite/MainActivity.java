package com.example.marksreaderlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAM_REQUEST_CODE = 102;
    Button BSelectImage,cameraBtn;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        cameraBtn=findViewById(R.id.clickPicture);
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Camera btn clicked",Toast.LENGTH_SHORT).show();
                askCameraPermissions();
            }
        });
    }
    private void askCameraPermissions() {
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},
//                    CAMERA_PERM_CODE);
//        }
//        else
//        {
            openCamera();
        //}
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
            else if(requestCode==CAM_REQUEST_CODE)
            {
                Bitmap image= (Bitmap) data.getExtras().get("data");
                IVPreviewImage.setImageBitmap(image);
                //------

                //Code to convert Bitmap to TensorImage


//                if(!Python.isStarted())
//                {
//                    Python.start(new AndroidPlatform(this));
//                }
//                Python py=Python.getInstance();
//                final PyObject pyobj=py.getModule("image_processing3");
//                PyObject obj=pyobj.callAttr("main",image);
//                Toast.makeText(MainActivity.this,obj.toString(),Toast.LENGTH_SHORT).show();
                //------

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode==CAMERA_PERM_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
            else
            {
                Toast.makeText(this,"Camera Permission is required to use camera",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera()
    {
        //Toast.makeText(this,"Camera Open Request",Toast.LENGTH_SHORT).show();
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAM_REQUEST_CODE);
    }

    private void callInterpreter(Bitmap input){
        File path=new File("model.tflite");
        String output="";
        try (Interpreter interpreter = new Interpreter(path)) {
            interpreter.run(input, output);
        }
        catch(IllegalArgumentException e){}
    }
    // catch(Exception e)
    // {

    // }
}