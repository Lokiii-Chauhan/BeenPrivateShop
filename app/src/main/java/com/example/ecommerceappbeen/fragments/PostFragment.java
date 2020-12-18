package com.example.ecommerceappbeen.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ecommerceappbeen.imageprocessing.ImageRotationDetectionHelper;
import com.example.ecommerceappbeen.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

import static com.parse.Parse.getApplicationContext;

public class PostFragment extends Fragment {
    private ImageView imgUpload, imgUpload2, imgUpload3;
    private EditText edtName, edtDes, edtPrice;
    private Button btnPost;
    private Uri uri1, uri2, uri3;
    private ParseFile newParseFile,newParseFile2,newParseFile3;
    private ParseObject newParseObject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        imgUpload = view.findViewById(R.id.imgUpload);
        imgUpload2 = view.findViewById(R.id.imgUpload2);
        imgUpload3 = view.findViewById(R.id.imgUpload3);
        edtName = view.findViewById(R.id.edtName);
        edtDes = view.findViewById(R.id.edtDes);
        edtPrice = view.findViewById(R.id.edtPrice);
        btnPost = view.findViewById(R.id.btnPost);

        imgUpload2.setVisibility(View.GONE);
        imgUpload3.setVisibility(View.GONE);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                } else {
                    getChosenImage();
                }
            }
        });

        imgUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                } else {
                    getChosenImage2();
                }
            }
        });

        imgUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                } else {
                    getChosenImage3();
                }
            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtName.getText().toString().equals("") && edtDes.getText().toString().equals("") && edtPrice.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter All Values", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        newParseObject.put("name", edtName.getText().toString());
                        newParseObject.put("des", edtDes.getText().toString());
                        newParseObject.put("price", edtPrice.getText().toString() + "/-");
                        newParseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        newParseObject.put("email", ParseUser.getCurrentUser().getEmail());
                        newParseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Succcess", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    private void getChosenImage2() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }

    private void getChosenImage3() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && resultCode == Activity.RESULT_OK && data != null) {
            try {

                uri1 = data.getData();
                ImageRotationDetectionHelper.getCameraPhotoOrientation(uri1.getPath());
                InputStream inputStream1 = getApplicationContext().getContentResolver().openInputStream(uri1);
                ByteArrayOutputStream byteBuffer1 = new ByteArrayOutputStream();


                // this is storage overwritten on each iteration with bytes
                int bufferSize1 = 1024;
                byte[] buffer1 = new byte[bufferSize1];

                // we need to know how may bytes were read to write them to the byteBuffer
                int len = 0;
                while ((len = inputStream1.read(buffer1)) != -1) {
                    byteBuffer1.write(buffer1, 0, len);
                }

                Glide.with(getApplicationContext())
                        .load(uri1)
                        .into(imgUpload);


                newParseFile = new ParseFile(UUID.randomUUID().toString() + ".png", byteBuffer1.toByteArray());
                newParseObject = new ParseObject("Photo");
                newParseObject.put("picture1", newParseFile);
                imgUpload2.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("LOG", e.getMessage());
            }
        } else if (requestCode == 3000 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                uri2 = data.getData();

                ImageRotationDetectionHelper.getCameraPhotoOrientation(uri2.getPath());


                InputStream inputStream2 = getApplicationContext().getContentResolver().openInputStream(uri2);
                ByteArrayOutputStream byteBuffer2 = new ByteArrayOutputStream();

                // this is storage overwritten on each iteration with bytes
                int bufferSize2 = 1024;
                byte[] buffer2 = new byte[bufferSize2];

                // we need to know how may bytes were read to write them to the byteBuffer
                int len = 0;
                while ((len = inputStream2.read(buffer2)) != -1) {
                    byteBuffer2.write(buffer2, 0, len);
                }

                Glide.with(getApplicationContext())
                        .load(uri2)
                        .into(imgUpload2);


                newParseFile2 = new ParseFile(UUID.randomUUID().toString() + ".png", byteBuffer2.toByteArray());
                newParseObject.put("picture2", newParseFile2);

                imgUpload3.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("LOG", e.getMessage());
            }
        } else if (requestCode == 4000 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                uri3 = data.getData();
                ImageRotationDetectionHelper.getCameraPhotoOrientation(uri3.getPath());

                InputStream inputStream3 = getApplicationContext().getContentResolver().openInputStream(uri3);
                ByteArrayOutputStream byteBuffer3 = new ByteArrayOutputStream();

                // this is storage overwritten on each iteration with bytes
                int bufferSize3 = 1024;
                byte[] buffer3 = new byte[bufferSize3];

                // we need to know how may bytes were read to write them to the byteBuffer
                int len = 0;
                while ((len = inputStream3.read(buffer3)) != -1) {
                    byteBuffer3.write(buffer3, 0, len);
                }

                Glide.with(getApplicationContext())
                        .load(uri3)
                        .into(imgUpload3);


                newParseFile3 = new ParseFile(UUID.randomUUID().toString() + ".png", byteBuffer3.toByteArray());
                newParseObject.put("picture3", newParseFile3);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("LOG", e.getMessage());
            }


        }

    }

}