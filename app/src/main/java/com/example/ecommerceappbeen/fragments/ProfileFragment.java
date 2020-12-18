package com.example.ecommerceappbeen.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.ecommerceappbeen.R;
import com.example.ecommerceappbeen.auth.LogIn;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.parse.Parse.getApplicationContext;


public class ProfileFragment extends Fragment {

    private ArrayList<String> myArraylist;
    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private TextView txtname, txtEmail;
    private CircleImageView profile_iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtEmail = view.findViewById(R.id.profiletxtEmail);
        txtname = view.findViewById(R.id.profileTxtName);

        txtEmail.setText(ParseUser.getCurrentUser().getEmail());
        txtname.setText(ParseUser.getCurrentUser().getUsername());

        myArraylist = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, myArraylist);
        listView = view.findViewById(R.id.listView);
        profile_iv = view.findViewById(R.id.profile_iv);

        return view;

    }
}