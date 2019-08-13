package com.example.eveorg;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Forget_Password extends AppCompatActivity {

    EditText email, sapID, pass, conf_pass;
    TextView tv;
    Button back, submit;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        db = FirebaseFirestore.getInstance();


        back = (Button) findViewById(R.id.backtoLoginBtn);
        submit = (Button) findViewById(R.id.forget_button);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = (EditText)findViewById(R.id.registered_emailid);
                sapID = (EditText)findViewById(R.id.registered_sapid);
                pass = (EditText)findViewById(R.id.update_pass);
                conf_pass = (EditText)findViewById(R.id.update_conf_pass);

                String in_mail = email.getText().toString();
                String in_sap = sapID.getText().toString();
                String in_pass = pass.getText().toString();
                String in_conf_pass = conf_pass.getText().toString();

                if (in_mail.isEmpty() && in_sap.isEmpty()) {
                    //email.setHighlightColor(getResources().getColor(R.color.red));
                    //sapID.setHighlightColor(getResources().getColor(R.color.red));
                    Toast.makeText(Forget_Password.this, "Fields can't be empty..",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (in_pass.equals(in_conf_pass) ) {
                        submitTask(in_mail, in_sap, in_pass);
                    } else {
                        Toast.makeText(Forget_Password.this, "Password fields can't be empty..", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void submitTask(final String email, final String sapid, final String pass){
        final CollectionReference db_user = db.collection("users");

        db_user.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                                String db_email = Objects.requireNonNull(doc.get("email")).toString();
                                String db_sapid = Objects.requireNonNull(doc.get("sapID")).toString();

                                if (email.equals(db_email) && sapid.equals(db_sapid)) {
                                    String docID = db_user.document(sapid).getId();
                                    DocumentReference documentReference = db.collection("users").document(sapid);
                                    documentReference.update("password",pass);
                                    //tv.setText(String.valueOf(docID));
                                    break;
                                }
                                else if (sapid.equals(db_sapid) && !email.equals(db_email)) {
                                    Toast.makeText(Forget_Password.this, "Invalid EMAIL ID or SAP ID",Toast.LENGTH_SHORT).show();
                                    break;
                                }

                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Forget_Password.this,"Database Error!!",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()) {
            case R.id.show_hide_password:
                if (checked) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    conf_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    conf_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }

    }
}
