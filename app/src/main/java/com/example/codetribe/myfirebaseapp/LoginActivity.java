package com.example.codetribe.myfirebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonLogin;
    private EditText editTextEmail;
    private  EditText editTextPasssowrd;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth =FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null)
        {
            //profile activity here
           // finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }


        editTextEmail =(EditText)findViewById(R.id.editTextEmail);
        editTextPasssowrd =(EditText)findViewById(R.id.editTextPassword);
        buttonLogin =(Button) findViewById(R.id.buttonLogin);
        textViewSignup =(TextView)findViewById(R.id.textViewSignin);

        buttonLogin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }


    private void userLogin()
    {
        String email =editTextEmail.getText().toString().trim();
        String password =editTextPasssowrd.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Passwordcannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Login in ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            //start profile activity
                           // finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {

        if(view ==buttonLogin)
        {
            userLogin();
        }

        if(view ==textViewSignup)
        {
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
