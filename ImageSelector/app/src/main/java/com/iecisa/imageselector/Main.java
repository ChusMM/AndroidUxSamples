package com.iecisa.imageselector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "com.iecisa.imageselector.Main";
    private static final String JPG_EXTENSION = ".jpg";
    private static final String INTENT_DATA_IMAGE_PARAM = "data";

    private CharSequence[] items; // = getResources().getStringArray(R.array.image_options);
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;

    private ImageView ivImage;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        items = getResources().getStringArray(R.array.image_options);
        this.ivImage = (ImageView) findViewById(R.id.ivImage);

        Button selectImg = (Button) findViewById(R.id.btnSelectPhoto);
        if (selectImg != null) {
            selectImg.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSelectPhoto:
                selectImage();
                break;
        }
    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
        builder.setTitle(getString(R.string.select_image));
        builder.setItems(getResources().getTextArray(R.array.image_options), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Main.this);

                if (items[item].equals(getString(R.string.take_photo))) {
                    userChoosenTask = getString(R.string.take_photo);
                    if(result) {
                        cameraIntent();
                    }
                } else if (items[item].equals(getString(R.string.select_from_media))) {
                    userChoosenTask = getString(R.string.select_from_media);
                    if(result) {
                        galleryIntent();
                    }
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo")) {
                        this.cameraIntent();
                    } else if (userChoosenTask.equals("Choose from Library")) {
                        this.galleryIntent();
                    }
                } else {
                    Log.e(TAG, "No se obtuvo permiso para seleccionar capturar la imagen");
                    Toast.makeText(this, "No se obtuvo permiso para seleccionar capturar la imagen", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ivImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get(INTENT_DATA_IMAGE_PARAM);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (thumbnail != null && thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)) {

            // .getExternalStorageDirectory()
            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + JPG_EXTENSION);
            FileOutputStream fileOutputStream;
            try {
                destination.createNewFile();
                fileOutputStream = new FileOutputStream(destination);
                fileOutputStream.write(bytes.toByteArray());
                fileOutputStream.close();

                ivImage.setImageBitmap(thumbnail);
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Log.e(TAG, "Fallo al guardar la imagen capturada");
            Toast.makeText(this, "Fallo al guardar la imagen capturada", Toast.LENGTH_SHORT).show();
        }
    }
}
