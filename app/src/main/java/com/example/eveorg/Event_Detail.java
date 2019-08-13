package com.example.eveorg;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eveorg.Model.Booking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class Event_Detail extends AppCompatActivity {

    String title, image, document, description;
    TextView title_tv, desc_tv;
    ImageView image_iv;
    Button bookBtn;
    ProgressDialog mDialog;
    EditText input;

    Boolean bookingBtnState = true;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        title = getIntent().getExtras().get("title").toString();
        image = getIntent().getExtras().get("image").toString();
        document = getIntent().getExtras().get("documentUID").toString();
        //description = getIntent().getExtras().get("description").toString();
        //String UID = getIntent().getExtras().get("UID").toString();

        mDialog = new ProgressDialog(Event_Detail.this);
        mDialog.setMessage("Please Wait..");

        image_iv = (ImageView)findViewById(R.id.image_detail);
        title_tv = (TextView)findViewById(R.id.title_detail);
        desc_tv = (TextView)findViewById(R.id.desc_detail);
        bookBtn = (Button)findViewById(R.id.bookbtn);
        title_tv.setText(title);
        //desc_tv.setText(description);

        db = FirebaseFirestore.getInstance();

        Glide.with(Event_Detail.this).load(image).into(image_iv);
        mDialog.show();
//        db.collection("new_events").document(document)
//                .collection("registrations")
//                .whereEqualTo("user","70011016008")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
//                                        @Nullable FirebaseFirestoreException e) {
//                        mDialog.dismiss();
//                        bookBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                        bookBtn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                        bookBtn.setText("Booked");
//                        bookBtn.setEnabled(false);
//                        bookingBtnState = false;
//                    }
//                });

        //if (bookingBtnState) {
            bookBtn.setText("Book");
            bookBtn.setEnabled(true);
            bookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Event_Detail.this);
                    alertDialog.setTitle("Verification");
                    alertDialog.setMessage("Please enter Your SAP ID ");

                    input = new EditText(Event_Detail.this);
                    input.setTextColor(getResources().getColor(R.color.black));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialog.show();
                            final CollectionReference db_user = db.collection("users");

                            db_user.get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                                                    String db_sapid = Objects.requireNonNull(doc.get("sapID")).toString();

                                                    Booking booked = new Booking(db_sapid);

                                                    if (db_sapid.equals(input.getText().toString())) {
                                                        db.collection("new_events").document(document)
                                                                .collection("registration")
                                                                .document(input.getText().toString())
                                                                .set(booked)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()){
                                                                            mDialog.dismiss();
                                                                            bookBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                                                            bookBtn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                                                            bookBtn.setText("Booked");
                                                                            bookBtn.setEnabled(false);
                                                                        }
                                                                        else {
                                                                            Toast.makeText(Event_Detail.this, "Booking failed... Try again later.",Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(Event_Detail.this,"Database Error!!",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                        //String docID = db_user.document(db_sapid).getId();
                                                        //DocumentReference documentReference = db.collection("new_events").document();
                                                        //documentReference.update("password",pass);

                                                        break;
                                                    }

                                                }
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Event_Detail.this,"Database Error!!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();

                }
            });
        //}
//        else if (!bookingBtnState){
//            //bookBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            //bookBtn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//            bookBtn.setText("Book");
//            bookBtn.setEnabled(true);
//        }

    }
}
