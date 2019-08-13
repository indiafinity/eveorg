package com.example.eveorg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class fragment_settings extends Fragment {

    Button change,us,pass;
    TextView us_txt;
    Boolean us_vis = true, pass_vis = true;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        change = view.findViewById(R.id.change_sett);
        us = view.findViewById(R.id.about_us);
        pass = view.findViewById(R.id.clickpass);

        us_txt = view.findViewById(R.id.about_us_text);

        if (pass_vis){
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pass.setVisibility(View.VISIBLE);
                }
            });
            pass_vis = false;
        }
        else if (!pass_vis){
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pass.setVisibility(View.GONE);
                }
            });
            pass_vis = true;
        }



        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Forget_Password.class));
            }
        });

        if (us_vis) {
            us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    us_txt.setVisibility(View.VISIBLE);
                    us_vis = false;
                }
            });
        }
        else if (!us_vis) {
            us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    us_txt.setVisibility(View.GONE);
                    us_vis = true;
                }
            });
        }


        return view;

    }


}
