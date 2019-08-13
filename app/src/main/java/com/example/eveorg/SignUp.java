package com.example.eveorg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eveorg.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText fullName, sapID, confirm_sapID, emailID, number, pass, confirm_pass;
    Button btnSignUp;
    ProgressDialog mDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = (EditText)findViewById(R.id.fullName);
        sapID = (EditText)findViewById(R.id.userSapId);
        confirm_sapID = (EditText)findViewById(R.id.confirmSapId);
        emailID = (EditText)findViewById(R.id.emailid);
        number = (EditText)findViewById(R.id.mobileNumber);
        pass = (EditText)findViewById(R.id.password);
        confirm_pass = (EditText)findViewById(R.id.confirmPassword);
        btnSignUp = (Button)findViewById(R.id.signUpBtn);

        db = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String db_name = fullName.getText().toString();
                String db_sapID = sapID.getText().toString();
                String conf_SAP = confirm_sapID.getText().toString();
                String db_email = emailID.getText().toString();
                String db_mobile = number.getText().toString();
                String db_password = pass.getText().toString();
                String conf_pass = confirm_pass.getText().toString();

                mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Wait..");
                mDialog.show();

                if (db_name.isEmpty() || db_sapID.isEmpty() || conf_SAP.isEmpty() || db_email.isEmpty() || db_mobile.isEmpty() || db_password.isEmpty() || conf_pass.isEmpty()) {
                //if (db_sapID.isEmpty()) {
                    Toast.makeText(SignUp.this,"Either of the field is Empty..",Toast.LENGTH_LONG).show();
                    mDialog.dismiss();
                }
                else {
                    if (db_sapID.equals(conf_SAP) && db_password.equals(conf_pass)) {

                        mDialog.show();
                        User add_user = new User(db_sapID,db_password,db_name,db_email,db_mobile);
                        DocumentReference new_user = db.collection("users").document(conf_SAP);
                        new_user.set(add_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Registered successfully!!",Toast.LENGTH_LONG).show();
                                    mDialog.dismiss();
                                    startActivity(new Intent(SignUp.this,LoginNew.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUp.this, "Registration failed ... Retry.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        mDialog.dismiss();
                        Toast.makeText(SignUp.this,"SAP ID / Password doesn't match..",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public void loginOnClick (View view) {
        startActivity(new Intent(SignUp.this,LoginNew.class));
        finish();
    }
}
