package com.example.controls.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long backPressedTime;
    private Toast backToast;

    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private android.widget.ImageButton showPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @android.annotation.SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( com.example.controls.R.layout.activity_main);

        register = (TextView) findViewById( com.example.controls.R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById( com.example.controls.R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById( com.example.controls.R.id.email);
        editTextPassword = (EditText) findViewById( com.example.controls.R.id.password);

        progressBar = (ProgressBar) findViewById( com.example.controls.R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById( com.example.controls.R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        showPassword = (android.widget.ImageButton) findViewById( com.example.controls.R.id.showPassword );
        showPassword.setOnTouchListener(new android.view.View.OnTouchListener() {
            public boolean onTouch(View v, android.view.MotionEvent event) {

                switch ( event.getAction() ) {
                    case android.view.MotionEvent.ACTION_DOWN:
                        editTextPassword.setInputType( android.text.InputType.TYPE_CLASS_TEXT);
                        break;
                    case android.view.MotionEvent.ACTION_UP:
                        editTextPassword.setInputType( android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
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
            progressBar.setVisibility(View.GONE);
            super.onBackPressed();

            return;

        } else {
            vibrating();
            backToast = Toast.makeText(getBaseContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT);

            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case com.example.controls.R.id.register:

                startActivity(new Intent(this, RegisterUser.class));
                finish();
                break;

            case com.example.controls.R.id.signIn:
                userLogin();
                break;

            case com.example.controls.R.id.forgotPassword:

              startActivity(new Intent(this, ForgotPassword.class));
               finish();
               break;

            case com.example.controls.R.id.showPassword:

        }
    }


    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            vibrating();
            editTextEmail.setError("Debe Ingresar su E-mail");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            vibrating();
            editTextEmail.setError("Debe Ingresar un E-mail Válido");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            vibrating();
            editTextPassword.setError("Debe Ingresar su Password");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 8){
            vibrating();
            editTextPassword.setError("Su Password Debe Contener 8 Caracteres o Más");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //redirect to ControlRegister
                    startActivity(new Intent(MainActivity.this, Welcome.class));
                    progressBar.setVisibility(View.GONE);
                    finish();



                }else{
                    vibrating();
                    Toast.makeText(MainActivity.this,"Verifique sus Datos de Ingreso \no Regístrese, Por Favor", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }
}