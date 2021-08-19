package com.example.controls.Controller;

public class Persistance extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();

		com.google.firebase.database.FirebaseDatabase.getInstance().setPersistenceEnabled(true);


	}
}
