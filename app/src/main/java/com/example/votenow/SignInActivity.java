package com.example.votenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText txtEmail,txtPassword;
    private Button loginButton,registerButton,forgotButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotButton = findViewById(R.id.forgotButton);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog pd = new ProgressDialog(SignInActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                try{
                    String str_email = txtEmail.getText().toString();
                    String str_password  = txtPassword.getText().toString();



                    if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                        Toast.makeText(SignInActivity.this, "Empty Credential$", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    else {
                        auth.signInWithEmailAndPassword(str_email , str_password).addOnCompleteListener(SignInActivity.this , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignInActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    Intent intent = new Intent(SignInActivity.this , BottomNavigation.class);
                                    startActivity(intent);
//                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
//                                    reference.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            pd.dismiss();
//                                            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                                            pd.dismiss();
//                                        }
//                                    });
                                }
                                else {
                                    pd.dismiss();
                                    Toast.makeText(SignInActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });



        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });



    }

}