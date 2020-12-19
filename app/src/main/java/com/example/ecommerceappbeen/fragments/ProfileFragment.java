package com.example.ecommerceappbeen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ecommerceappbeen.activities.LikedItemsActivity;
import com.example.ecommerceappbeen.R;
import com.parse.ParseUser;

import java.util.ArrayList;

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

        myArraylist.add("Liked Items");

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        startActivity(new Intent(getApplicationContext(), LikedItemsActivity.class));
                        break;
                }

            }
        });

        return view;

    }
}