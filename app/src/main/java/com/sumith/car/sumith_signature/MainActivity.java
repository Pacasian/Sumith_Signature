package com.sumith.car.sumith_signature;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    Button btnSave;
    Button btnClear;
    SignatureView signatureView;
    String location;
    private static  final String IMAGE_DIRECTORY ="/signature";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SignatureView signatureView=findViewById(R.id.sumith_signature_view);
        btnClear=findViewById(R.id.clearSign);
        btnSave=findViewById(R.id.SaveSign);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signatureView.clearCanvas();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap=signatureView.getSignatureBitmap();
                location=saveImage(bitmap);
            }
        });
    }
    public String saveImage(Bitmap mybitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        mybitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
        File wallpaperDirectory=new File(Environment.getExternalStorageDirectory()+IMAGE_DIRECTORY);
        if(!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdir();
            Log.d("hhhhh",wallpaperDirectory.toString());
        }
        try{
            File f=new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis()+".jpg");
            f.createNewFile();
            FileOutputStream fileOutputStream=new FileOutputStream(f);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            MediaScannerConnection.scanFile(MainActivity.this,new String[]{f.getPath()},new String[]{"image/jpeg"},null);
            fileOutputStream.close();
            Log.d("TAG","File Saved::----->"+f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
