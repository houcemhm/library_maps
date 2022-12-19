package com.hoss.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
private TextInputEditText userNameEdt ,pwdEdt,confPwEdt;
private Button registerBtn;
private ProgressBar loadingPG;
private FirebaseAuth mAuth;
private TextView loginTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userNameEdt=findViewById(R.id.idEdtUserName);
        pwdEdt=findViewById(R.id.idEdtUserPassword);
        confPwEdt=findViewById(R.id.idEdtUserPasswordConf);
        registerBtn=findViewById(R.id.BtnRegister);
        loadingPG=findViewById(R.id.idpBLoiding);
        mAuth =FirebaseAuth.getInstance();
        loginTV=findViewById(R.id.idTVLogin);

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
             }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loadingPG.setVisibility(View.VISIBLE);
            String userName=userNameEdt.getText().toString();
            String pwd =pwdEdt.getText().toString();
            String cnfPwd=confPwEdt.getText().toString();
            if(!pwd.equals(cnfPwd)){
                Toast.makeText(RegistrationActivity.this,"Please enter a valid password",Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(userName) ||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(cnfPwd)){
                Toast.makeText(RegistrationActivity.this,"Please add all your credentials",Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.createUserWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loadingPG.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegistrationActivity.this,"User Registred",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            loadingPG.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
            }
        });


    }
}