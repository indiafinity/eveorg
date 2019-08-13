package com.example.eveorg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class fragment_search extends Fragment {

    FirebaseFirestore db;
    EditText search;
    Button btn;
    ImageView imageView;
    TextView textView, read;
    CardView card;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_search,container,false);

        View view = inflater.inflate(R.layout.fragment_search,container,false);

        db = FirebaseFirestore.getInstance();

        search = view.findViewById(R.id.search_bar);
        btn = view.findViewById(R.id.searchBtn);
        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        read = view.findViewById(R.id.readMore);
        card = view.findViewById(R.id.cardView);
        card.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                View view = getActivity().getCurrentFocus();
                if (view == null) {
                    view = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                db.collection("new_events")
                        .whereEqualTo("name",search.getText().toString())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                                @javax.annotation.Nullable FirebaseFirestoreException e) {

                                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                    final String abc = doc.get("name").toString();
                                    final String img = doc.get("img").toString();
                                    //final String des = doc.get("detail").toString();
                                    final String docID = doc.getId();
                                    Log.i("hello","name is "+abc);
                                    card.setVisibility(View.VISIBLE);
                                    textView.setText(abc);
                                    Glide.with(getContext()).load(img).into(imageView);
                                    read.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent nextIntent = new Intent(getActivity(),Event_Detail.class);
                                            nextIntent.putExtra("title",abc);
                                            nextIntent.putExtra("image",img);
                                            nextIntent.putExtra("documentUID",docID);
                                            //nextIntent.putExtra("description",des);
                                            //nextIntent.putExtra("UID",);
                                            startActivity(nextIntent);
                                        }
                                    });

                                }

                            }
                        });
            }
        });

        return view;
    }
}
