package com.example.controls.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controls.Controller.AdapterControl;
import com.example.controls.Models.Control;
import com.example.controls.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class ControlSearch extends AppCompatActivity {



	private FirebaseDatabase firebaseDatabase;
	private DatabaseReference databaseReference;
	private ArrayList<Control> list;
	private RecyclerView recyclerView;
	private SearchView searchView;
	private AdapterControl adapter;

	private LinearLayoutManager linearLayoutManager;

	private long backPressedTime;
	private android.widget.Toast backToast;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( com.example.controls.R.layout.activity_control_search );

		databaseReference = FirebaseDatabase.getInstance().getReference().child("controls");
		recyclerView = findViewById(R.id.recycleView);
		searchView = findViewById(R.id.searchView);






		linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
		list = new ArrayList<>();
		adapter = new AdapterControl(list);
		recyclerView.setAdapter(adapter);



		databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()){
					for (DataSnapshot snapshot : dataSnapshot.getChildren()){

						Control control = snapshot.getValue(Control.class);
						list.add(control);
					}
					adapter.notifyDataSetChanged();

				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				search(newText);
				return true;
			}
		});
	} //onCreateEnd

	public void vibrating(){
		android.os.Vibrator vibrator = (android.os.Vibrator) getSystemService( android.content.Context.VIBRATOR_SERVICE );
		vibrator.vibrate( 300 );
	}

	public void onBackPressed() {
		if (backPressedTime + 3000 > System.currentTimeMillis()) {
			backToast.cancel();
			startActivity(new android.content.Intent(ControlSearch.this, Welcome.class));
			super.onBackPressed();
			return;

		} else {
			vibrating();
			backToast = android.widget.Toast.makeText(getBaseContext(), "Presione de nuevo para salir", android.widget.Toast.LENGTH_SHORT);
			backToast.show();
		}
		backPressedTime = System.currentTimeMillis();
	}

	private void search(String newText) {
		ArrayList<Control> newList = new ArrayList<>();
		for (Control object : list){
			if (object.getRut().toLowerCase().contains(newText.toLowerCase())){

				newList.add(object);
			}
		}
		AdapterControl adapterControl = new AdapterControl(newList);
		recyclerView.setAdapter(adapterControl
		);

	}

	//    metodo para traer el menu
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate( R.menu.menu_control_search,menu);
		return super.onCreateOptionsMenu(menu);
	}

	//    metodo para configurar las opciones del menú
	@Override
	public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {

		switch (item.getItemId()){

			case com.example.controls.R.id.icon_home:
				vibrating();
				startActivity(new android.content.Intent(ControlSearch.this, Welcome.class));
				finish();
				break;

			case R.id.icon_edit:
				startActivity(new Intent(ControlSearch.this, com.example.controls.View.ControList.class));
				finish();
				break;

			case com.example.controls.R.id.icon_add:
				vibrating();
				startActivity(new android.content.Intent(ControlSearch.this, com.example.controls.View.ControlCreate.class));
				finish();
				break;

			case R.id.icon_delete:
				vibrating();
				startActivity(new Intent(ControlSearch.this,ControlEdit.class));

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