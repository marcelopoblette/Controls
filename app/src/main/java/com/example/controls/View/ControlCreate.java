package com.example.controls.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class ControlCreate extends AppCompatActivity {

    private android.widget.EditText rut, fullName, dateTime, bloodPresure, heartFreq, saturation, breathFreq, temperture, hgt, notes;
    private android.widget.ProgressBar progressBar;
    private long backPressedTime;
    private android.widget.Toast backToast;



    com.google.firebase.database.FirebaseDatabase firebaseDatabase;
    com.google.firebase.database.DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( com.example.controls.R.layout.activity_control_create);



        rut = (android.widget.EditText) findViewById(com.example.controls.R.id.rut);
        fullName = (android.widget.EditText) findViewById(com.example.controls.R.id.FullName);
        dateTime = (android.widget.EditText) findViewById(com.example.controls.R.id.dateTime);
        bloodPresure = (android.widget.EditText) findViewById(com.example.controls.R.id.bloodPresure);
        heartFreq = (android.widget.EditText) findViewById(com.example.controls.R.id.heartFreq);
        saturation = (android.widget.EditText) findViewById(com.example.controls.R.id.saturation);
        breathFreq = (android.widget.EditText) findViewById(com.example.controls.R.id.breathFreq);
        temperture = (android.widget.EditText) findViewById(com.example.controls.R.id.temperture);
        hgt = (android.widget.EditText) findViewById(com.example.controls.R.id.hgt);
        notes = (android.widget.EditText) findViewById(com.example.controls.R.id.notes);
        progressBar = (android.widget.ProgressBar) findViewById( com.example.controls.R.id.progressBar);

        initializeFireBase();
    }

    public void vibrating(){
        android.os.Vibrator vibrator = (android.os.Vibrator) getSystemService( android.content.Context.VIBRATOR_SERVICE );
        vibrator.vibrate( 300 );
    }

    private void initializeFireBase() {
        com.google.firebase.FirebaseApp.initializeApp(this);

        firebaseDatabase = com.google.firebase.database.FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            backToast.cancel();
            startActivity(new android.content.Intent(ControlCreate.this, Welcome.class));
            super.onBackPressed();


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
                startActivity(new android.content.Intent(ControlCreate.this, Welcome.class));
                finish();
                break;

//            case com.example.controls.R.id.icon_search:
//                startActivity(new android.content.Intent(ControlCreate.this, com.example.controls.View.ControlSearch.class));
//                finish();
//                break;

            case com.example.controls.R.id.icon_just_save:
                vibrating();
                saveControl();
                break;

                default:
                    break;
        }
        return true;
    }


    public boolean rutValidator(String rut) {
        boolean validator = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validator = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validator;
    }

    private void saveControl() {
//        declaring editTexts

       String taken_rut = rut.getText().toString().trim();
       String taken_fullName = fullName.getText().toString().trim();
       java.util.Date date = new java.util.Date();
       java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy HH" +
               ":mm");
       String taken_dateTime = simpleDateFormat.format( date );
       String taken_bloodPresure = bloodPresure.getText().toString().trim();
       String taken_heartFreq = heartFreq.getText().toString().trim();
       String taken_saturation = saturation.getText().toString().trim();
       String taken_breathFreq = breathFreq.getText().toString().trim();
       String taken_temperture = temperture.getText().toString().trim();
       String taken_hgt = hgt.getText().toString().trim();
       String taken_notes = notes.getText().toString().trim();


//       Validating empty fiels

       if (taken_rut.isEmpty()){
           vibrating();
            rut.setError("Debe Ingresar el Rut del Paciente");
            rut.requestFocus();
            return;
        }

       if (rutValidator( taken_rut )== false){
           vibrating();
           rut.setError("Debe Ingresar un Rut válido");
           rut.requestFocus();
           return;
       }


//
        if (taken_fullName.isEmpty()){
            vibrating();
            fullName.setError("Debe Ingresar el Nombre del Paciente");
            fullName.requestFocus();
            return;
        }

        if (taken_dateTime.isEmpty()){
            vibrating();
            dateTime.setError("Debe Ingresar la Fecha");
            dateTime.requestFocus();
            return;
        }

        if (taken_bloodPresure.isEmpty()){
            vibrating();
            bloodPresure.setError("Debe Ingresar la Presión Arterial del Paciente");
            bloodPresure.requestFocus();
            return;
        }

        if (taken_heartFreq.isEmpty()){
            vibrating();
            heartFreq.setError("Debe Ingresar la Frecuencia Cardiaca del Paciente");
            heartFreq.requestFocus();
            return;
        }

        if (taken_saturation.isEmpty()){
            vibrating();
            saturation.setError("Debe Ingresar la Saturación del Paciente");
            saturation.requestFocus();
            return;
        }

        if (taken_breathFreq.isEmpty()){
            vibrating();
            breathFreq.setError("Debe Ingresar su la Frecuencia Respiratoria del Paciente");
            breathFreq.requestFocus();
            return;
        }

        if (taken_temperture.isEmpty()){
            vibrating();
            temperture.setError("Debe Ingresar la Temperatura del Paciente");
            temperture.requestFocus();
            return;
        }

        if (taken_hgt.isEmpty()){
            vibrating();
            hgt.setError("Debe Ingresar el HemoGlucoTest del Paciente");
            hgt.requestFocus();
            return;
        }

        if (taken_notes.isEmpty()){
            vibrating();
            notes.setError("Sino va a ingresar una Observación Coloque un Punto.");
            notes.requestFocus();
            return;
        }


        progressBar.setVisibility(android.view.View.VISIBLE);

//        bringing Control Class
        com.example.controls.Models.Control control = new com.example.controls.Models.Control();
        control.setUid(java.util.UUID.randomUUID().toString());
        control.setRut(taken_rut);
        control.setFullName(taken_fullName);
        control.setDateTime(taken_dateTime);
        control.setBloodPresure(taken_bloodPresure);
        control.setHeartFreq(taken_heartFreq);
        control.setSaturation(taken_saturation);
        control.setBreathFreq(taken_breathFreq);
        control.setTemperature(taken_temperture);
        control.setHgt(taken_hgt);
        control.setNotes(taken_notes);

//        filling Database with Persistance

        databaseReference.child("controls").child(control.getUid()).setValue(control);
        progressBar.setVisibility(android.view.View.GONE);
        android.widget.Toast.makeText(ControlCreate.this, "El Control Se Ha Guardado Exitosamente!", android.widget.Toast.LENGTH_LONG).show();
        startActivity(new android.content.Intent(ControlCreate.this, ControList.class));
        finish();

    }
}