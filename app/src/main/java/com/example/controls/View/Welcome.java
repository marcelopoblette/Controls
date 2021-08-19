package com.example.controls.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controls.Models.User;
import com.example.controls.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity implements android.view.View.OnClickListener {

    private long backPressedTime;
    private Toast backToast;

    private FirebaseUser user;
    private DatabaseReference reference;

    private android.widget.ImageButton logout, searchControl, addControl, watchControls;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( com.example.controls.R.layout.activity_welcome);


        logout = (android.widget.ImageButton) findViewById( com.example.controls.R.id.logout);
        logout.setOnClickListener(this);

        searchControl = (android.widget.ImageButton) findViewById( com.example.controls.R.id.searchControl);
        searchControl.setOnClickListener(this);

        watchControls = (ImageButton)  findViewById(R.id.editControls);
        watchControls.setOnClickListener(this);

        addControl = (android.widget.ImageButton) findViewById( com.example.controls.R.id.addControl);
        addControl.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userId = user.getUid();

        final TextView greetingTextView = (TextView) findViewById( com.example.controls.R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById( com.example.controls.R.id.tilFullName);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullName = userProfile.fullName;

                    greetingTextView.setText("Bienvenid@, \n" + fullName + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Welcome.this, "Hay un Error en la Autenticación, Por Favor, Intente Nuevamenete", Toast.LENGTH_LONG).show();
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
            finishAffinity();
            System.exit(0);

            return;

        } else {
            vibrating();
            backToast = Toast.makeText(getBaseContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT);
            backToast.show();

        }
        backPressedTime = System.currentTimeMillis();

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(com.example.controls.R.menu.menu_welcome,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    metodo para configurar las opciones del menú
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {

        switch (item.getItemId()){

            case R.id.icon_add:
                vibrating();
                startActivity(new Intent(Welcome.this, ControlCreate.class));
                finish();
                break;

            case R.id.icon_edit:
                startActivity(new Intent(Welcome.this, com.example.controls.View.ControList.class));
                finish();
                break;

            case R.id.icon_search:
                startActivity(new Intent(Welcome.this, com.example.controls.View.ControlSearch.class));
                finish();
                break;

            case com.example.controls.R.id.icon_exit:
                vibrating();
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
                System.exit(0);

                break;

                default:break;
        }
        return true;
    }

    @Override
    public void onClick(android.view.View v) {

        switch (v.getId()){
            case com.example.controls.R.id.logout:
                vibrating();
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
                System.exit(0);
                break;

            case R.id.editControls:
                startActivity(new Intent(Welcome.this, com.example.controls.View.ControList.class));
                finish();
                break;

            case com.example.controls.R.id.searchControl:
                startActivity(new Intent(Welcome.this, com.example.controls.View.ControlSearch.class));
                finish();
                break;

            case com.example.controls.R.id.addControl:
                vibrating();
                startActivity(new Intent(this, ControlCreate.class));
                finish();
                break;
        }

    }
}