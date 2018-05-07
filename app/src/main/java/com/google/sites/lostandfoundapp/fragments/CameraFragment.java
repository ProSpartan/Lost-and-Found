package com.google.sites.lostandfoundapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.sites.lostandfoundapp.R;
import com.google.sites.lostandfoundapp.database.AppDatabase;
import com.google.sites.lostandfoundapp.database.entities.ImageEntity;
import com.google.sites.lostandfoundapp.log.ToastLog;

import java.io.ByteArrayOutputStream;

/**
 * Created by Isaac on 3/7/2018.
 */

public class CameraFragment extends Fragment{
    private static CameraFragment INSTANCE;
    private static int blah = 0;

    private static final ToastLog TOAST_LOG = ToastLog.getInstance();
    private static final int CAMERA_REQUEST = 1888;

    private ImageView imageView;
    private Button takePhotoButton;
    private Button savePhotoButton;
    private Button cancelPhotoButton;

    private EditText title;
    private EditText description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_layout, container, false);

        imageView = view.findViewById(R.id.imgCameraImage);
        takePhotoButton = view.findViewById(R.id.camera_button_take_photo);
        savePhotoButton = view.findViewById(R.id.camera_button_save_photo);
        cancelPhotoButton = view.findViewById(R.id.camera_button_cancel_photo);
        title = view.findViewById(R.id.camera_edittext_title);
        description = view.findViewById(R.id.camera_edittext_description);

        takePhotoButton.setOnClickListener(photoTakeOnClickListener);
        savePhotoButton.setOnClickListener(photoSaveOnClickListener);
        cancelPhotoButton.setOnClickListener(photoCancelOnClickListener);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1888:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageView.setImageBitmap(bitmap);
                    savePhotoButton.setEnabled(true);
                    cancelPhotoButton.setEnabled(true);
                    title.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public static CameraFragment getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new CameraFragment();
        }
        return INSTANCE;
    }

    private Button.OnClickListener photoTakeOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Context context = v.getContext().getApplicationContext();
            if (checkCameraHardware(context)) {
                if (checkCameraPermission(context)) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else {
                    TOAST_LOG.toastMessage(context, "Application does not have permission to use the camera.", Toast.LENGTH_LONG);
                }
            }
            else {
                TOAST_LOG.toastMessage(context, "Your device does not have a camera.", Toast.LENGTH_LONG);
            }
        }
    };

    private Button.OnClickListener photoSaveOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(final View v) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            final Bitmap bitmap = bitmapDrawable.getBitmap();

            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            final byte[] bytes = stream.toByteArray();
            final String byteString = byteArrayToStringOfBytes(bytes);

            final ImageEntity imageEntity = new ImageEntity(blah++, title.getText().toString(), description.getText().toString(), byteString);
            final Context context = v.getContext().getApplicationContext();
            final AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

            resetComponents();
            TOAST_LOG.toastMessage(context, "Saving image.", Toast.LENGTH_LONG);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.imageDao().insertImage(imageEntity);
                }
            });
        }
    };

    private Button.OnClickListener photoCancelOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Context context = v.getContext().getApplicationContext();
            TOAST_LOG.toastMessage(context, "Canceling image.", Toast.LENGTH_LONG);
            resetComponents();
        }
    };

    private boolean checkCameraPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    private String byteArrayToStringOfBytes(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < bytes.length; i++) {
            if (i != bytes.length - 1) {
                sb.append(bytes[i]).append(", ");
            } else {
                sb.append(bytes[i]);
            }
        }

        sb.append("]");

        return sb.toString();
    }

    private void resetComponents() {
        savePhotoButton.setEnabled(false);
        cancelPhotoButton.setEnabled(false);
        title.setText("");
        description.setText("");
        title.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        imageView.setImageResource(android.R.color.transparent);
    }
}
