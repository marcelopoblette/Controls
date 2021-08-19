package com.example.controls.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controls.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText editTextFullname, editTextEmail, editTextPassword;
    private android.widget.Button registerUser;
    private ProgressBar progressBar;
    private android.widget.ImageButton showPassword;

    private long backPressedTime;
    private android.widget.Toast backToast;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( com.example.controls.R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById( com.example.controls.R.id.greeting);
        banner.setOnClickListener(this);

        editTextFullname = (EditText) findViewById( com.example.controls.R.id.tilFullName);
        editTextEmail = (EditText) findViewById( com.example.controls.R.id.email);
        editTextPassword = (EditText) findViewById( com.example.controls.R.id.password);
        registerUser = (android.widget.Button)  findViewById( com.example.controls.R.id.registerUser );
        registerUser.setOnClickListener( this );
        progressBar = (ProgressBar) findViewById( com.example.controls.R.id.progressBar);

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
            super.onBackPressed();
            startActivity(new android.content.Intent(RegisterUser.this, MainActivity.class));
            return;
        } else {
            vibrating();
            backToast = android.widget.Toast.makeText(getBaseContext(), "Presione de nuevo para salir", android.widget.Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    //    metodo para traer el menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(com.example.controls.R.menu.menu_just_save, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //    metodo para configurar las opciones del menú
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {

        switch (item.getItemId()){

            case com.example.controls.R.id.icon_home:
                vibrating();
                android.widget.Toast.makeText(this, "Inicio", android.widget.Toast.LENGTH_SHORT).show();
                startActivity(new android.content.Intent(RegisterUser.this, MainActivity.class));
                finish();
                break;

            case com.example.controls.R.id.icon_just_save:
                vibrating();
                registerUser();
                break;

            default:break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case com.example.controls.R.id.greeting:
                vibrating();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case com.example.controls.R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        final String fullName = editTextFullname.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (fullName.isEmpty()){
            vibrating();
            editTextFullname.setError("Debe Ingresar su Nombre Completo");
            editTextFullname.requestFocus();
            return;
        }

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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, email);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "Se Ha Registrado Exitosamente!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                        finish();



                                    } else {
                                        vibrating();
                                        Toast.makeText(RegisterUser.this, "El registro No se ha realizado, Intente nuevamente", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                                    });
                        } else {
                            vibrating();
                            Toast.makeText(RegisterUser.this, "El registro No se ha realizado, Intente nuevamente", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}