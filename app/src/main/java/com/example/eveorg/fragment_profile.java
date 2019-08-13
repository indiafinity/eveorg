package com.example.eveorg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class fragment_profile extends Fragment {

    TextView name, email, sap, mob;
    FirebaseFirestore db;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = FirebaseFirestore.getInstance();

        name = view.findViewById(R.id.set_name);
        email = view.findViewById(R.id.set_email);
        sap = view.findViewById(R.id.set_sap);
        mob = view.findViewById(R.id.set_mobile);

//        if (getArguments() != null) {
//            String UID = getArguments().getString("UID");
//            mob.setText(UID);
//        }

        //Bundle b = new Bundle();

//
//        db.collection("users")
//                .whereEqualTo("name", UID);

        return view;
    }
}
