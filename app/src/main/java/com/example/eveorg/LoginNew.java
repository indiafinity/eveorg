package com.example.eveorg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class LoginNew extends AppCompatActivity {

    FirebaseFirestore db;
    Button btnSignIn;
    EditText sapID, pass;
    ProgressDialog mDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (Button)findViewById(R.id.loginBtn);
        sapID = (EditText)findViewById(R.id.login_sapid);
        pass = (EditText)findViewById(R.id.login_password);

        db = FirebaseFirestore.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuserID = sapID.getText().toString();
                String tpassID = pass.getText().toString();

                mDialog = new ProgressDialog(LoginNew.this);
                mDialog.setMessage("Please Wait..");
                mDialog.show();

                if (tuserID.isEmpty() || tpassID.isEmpty()) {
                    mDialog.dismiss();
                    Toast.makeText(LoginNew.this, "SAP ID or Password can't be Empty..", Toast.LENGTH_LONG).show();
                }
                else {
                    ReadSingleUser(tuserID,tpassID);
                }
            }
        });


    }

    private void ReadSingleUser(final String userID, final String passID) {
        final CollectionReference db_user = db.collection("users");

        db_user.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                                String db_name, db_sapid, db_pass, db_email, db_phone;
                                db_name = doc.get("name").toString();
                                db_sapid = doc.get("sapID").toString();
                                db_pass = doc.get("password").toString();
                                //db_email = doc.get("email").toString();
                                //db_phone = doc.get("mobile").toString();

                                Bundle bundle = new Bundle();

                                if (db_sapid == null || db_pass == null) {
                                    mDialog.dismiss();
                                    Toast.makeText(LoginNew.this,"User Not Found...",Toast.LENGTH_LONG).show();
                                    recreate();

                                }
                                else {
                                    if (userID.equals(db_sapid) && passID.equals(db_pass)) {
                                        mDialog.dismiss();
                                        Toast.makeText(LoginNew.this, "Welcome back "+db_name+" !!",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginNew.this,MainActivity.class);
                                        intent.putExtra("UID",db_sapid);
                                        intent.putExtra("UserName",db_pass);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    }
                                    else if (userID.equals(db_sapid) && !passID.equals(db_pass)) {
                                        mDialog.dismiss();
                                        Toast.makeText(LoginNew.this,"Incorrect Password..",Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                    else {
                                        recreate();
                                    }
                                /*
                                else if (!userID.equals(db_sapid) && !passID.equals(db_pass)) {
                                    mDialog.dismiss();
                                    Toast.makeText(LoginNew.this,"User Not Found...",Toast.LENGTH_LONG).show();
                                    break;
                                } */
                                }
                            }
                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginNew.this,"Database Error!!",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()) {
            case R.id.show_hide_password:
                if (checked) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }

    }

    public void signUponClick(View view) {
        startActivity(new Intent(LoginNew.this,SignUp.class));
        //finish();
    }

    public void forgetPasswordOnClick(View view) {
        // add activity for forgot password
        startActivity(new Intent(LoginNew.this,Forget_Password.class));
    }

}