package com.example.controls.View;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controls.Models.Control;
import com.example.controls.R;

public class ControList extends AppCompatActivity {

	private android.widget.ProgressBar progressBar;

	private android.widget.ListView controlListView;
	private EditText searchText;

	private long backPressedTime;
	private android.widget.Toast backToast;

	com.google.firebase.database.FirebaseDatabase firebaseDatabase;
	com.google.firebase.database.DatabaseReference databaseReference;

	private java.util.List<com.example.controls.Models.Control> controlList = new java.util.ArrayList<com.example.controls.Models.Control>();
	android.widget.ArrayAdapter<com.example.controls.Models.Control> controlArrayAdapter;

	com.example.controls.Models.Control controlSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( com.example.controls.R.layout.activity_contro_list );

		progressBar = (android.widget.ProgressBar) findViewById( com.example.controls.R.id.progressBar);
		controlListView = findViewById( com.example.controls.R.id.controlListView);

		controlListView.setBackgroundColor(Color.WHITE);

		searchText = (EditText) findViewById(R.id.searchText);


		controlListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
				controlSelected = (com.example.controls.Models.Control) parent.getItemAtPosition(position);
				android.content.Intent controlItemSelected = new android.content.Intent(getApplicationContext(), ControlEdit.class );
				controlItemSelected.putExtra("uid", controlSelected.getUid());
				controlItemSelected.putExtra("rut", controlSelected.getRut());
				controlItemSelected.putExtra("name", controlSelected.getFullName());
				controlItemSelected.putExtra("dateTime", controlSelected.getDateTime());
				controlItemSelected.putExtra("bloodPresure", controlSelected.getBloodPresure());
				controlItemSelected.putExtra("heartFreq", controlSelected.getHeartFreq());
				controlItemSelected.putExtra("saturation", controlSelected.getSaturation());
				controlItemSelected.putExtra("breathFreq", controlSelected.getBreathFreq());
				controlItemSelected.putExtra("temperture", controlSelected.getTemperature());
				controlItemSelected.putExtra("hgt", controlSelected.getHgt());
				controlItemSelected.putExtra("notes", controlSelected.getNotes());

				startActivity(controlItemSelected);
			}
		});

		initializeFireBase();
		listControls();


	}
	public void vibrating(){
		android.os.Vibrator vibrator = (android.os.Vibrator) getSystemService( android.content.Context.VIBRATOR_SERVICE );
		vibrator.vibrate( 300 );
	}

	@Override
	public void onBackPressed() {
		if (backPressedTime + 3000 > System.currentTimeMillis()) {
			backToast.cancel();
			startActivity(new android.content.Intent(ControList.this, Welcome.class));
			super.onBackPressed();
			return;

		} else {
			vibrating();
			backToast = android.widget.Toast.makeText(getBaseContext(), "Presione de nuevo para salir", android.widget.Toast.LENGTH_SHORT);
			backToast.show();
		}
		backPressedTime = System.currentTimeMillis();
	}

	private void listControls() {
		databaseReference.child("controls").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(@androidx.annotation.NonNull com.google.firebase.database.DataSnapshot snapshot) {
				controlList.clear();
				for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()){
					com.example.controls.Models.Control control = dataSnapshot.getValue(com.example.controls.Models.Control.class);
					controlList.add(control);



					controlArrayAdapter = new android.widget.ArrayAdapter<com.example.controls.Models.Control>(ControList.this, android.R.layout.simple_list_item_1, controlList);
					controlListView.setAdapter(controlArrayAdapter);
					controlListView.setBackgroundColor(Color.WHITE);

					searchText.addTextChangedListener(new TextWatcher() {
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {

						}

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							controlArrayAdapter.getFilter().filter(s);

						}

						@Override
						public void afterTextChanged(Editable s) {

						}
					});

				}
			}

			@Override
			public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError error) {

			}
		});
	}

	private void initializeFireBase() {
		com.google.firebase.FirebaseApp.initializeApp(this);
		firebaseDatabase = com.google.firebase.database.FirebaseDatabase.getInstance();

		databaseReference = firebaseDatabase.getReference();
	}

	//    metodo para traer el menu
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate( com.example.controls.R.menu.menu_control_list,menu);
		return super.onCreateOptionsMenu(menu);
	}

	//    metodo para configurar las opciones del menú
	@Override
	public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {

		switch (item.getItemId()){

			case com.example.controls.R.id.icon_home:
				vibrating();
				android.widget.Toast.makeText(this, "Inicio", android.widget.Toast.LENGTH_SHORT).show();
				startActivity(new android.content.Intent(ControList.this, Welcome.class));
				finish();
				break;
			case com.example.controls.R.id.icon_search:
				startActivity(new android.content.Intent(ControList.this, com.example.controls.View.ControlSearch.class));
				finish();
				break;

			case com.example.controls.R.id.icon_add:
				vibrating();
				startActivity(new android.content.Intent(ControList.this, com.example.controls.View.ControlCreate.class));
				finish();
				break;

			case com.example.controls.R.id.icon_exit:
				vibrating();
				com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
				finishAffinity();
				System.exit(0);
				break;

				default:
					break;
		}
		return true;
	}
}