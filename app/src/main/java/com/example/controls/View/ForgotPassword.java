package com.example.controls.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    private long backPressedTime;
    private android.widget.Toast backToast;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( com.example.controls.R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById( com.example.controls.R.id.email);
        resetPasswordButton = (Button) findViewById( com.example.controls.R.id.resetPassword);
        progressBar = (ProgressBar) findViewById( com.example.controls.R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();
            }
        });

    }
    public void vibrating(){
        android.os.Vibrator vibrator = (android.os.Vibrator) getSystemService( android.content.Context.VIBRATOR_SERVICE );
        vibrator.vibrate( 300 );
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            startActivity(new android.content.Intent(ForgotPassword.this, MainActivity.class));
            return;

        } else {
            vibrating();
            backToast = android.widget.Toast.makeText(getBaseContext(), "Presione de nuevo para salir", android.widget.Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void resetPassword() {

        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()){
            vibrating();
            emailEditText.setError("Debe Ingresar su E-mail");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            vibrating();
            emailEditText.setError("Debe Ingresar un E-mail Válido");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(ForgotPassword.this, "Enviamos un Mensaje a su Correo para Restablecer su Contraseña", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                    finish();
                }else{
                    vibrating();
                    Toast.makeText( com.example.controls.View.ForgotPassword.this,"Verifique sus Datos de Ingreso o Regístrese, Por Favor", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}