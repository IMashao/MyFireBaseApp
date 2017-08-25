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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth =FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() !=null) {
            //profile activity here
            //finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        buttonRegister =(Button)findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);
        editTextEmail =(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        textViewSignup =(TextView)findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }


    private void registerUser()
    {
        String email =editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();

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

        progressDialog.setMessage("Registerng user ...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {
                            //user is successful and logged in
                                //profile activity here
                                //finish();
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Could not register please try again later",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }




    @Override
    public void onClick(View view) {

        if(view ==buttonRegister)
        {
            registerUser();
        }


        if(view ==textViewSignup)
        {

            //will open the login form
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
