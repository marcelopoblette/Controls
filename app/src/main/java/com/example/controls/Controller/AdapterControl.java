package com.example.controls.Controller;

import android.widget.TextView;

import com.example.controls.Models.Control;
import com.example.controls.R;

public class AdapterControl extends androidx.recyclerview.widget.RecyclerView.Adapter<AdapterControl.viewholdercontrols> {

	java.util.List<com.example.controls.Models.Control> controlList;

	public AdapterControl(java.util.List<com.example.controls.Models.Control> controlList) {
		this.controlList = controlList;
	}

	@androidx.annotation.NonNull
	@Override
	public com.example.controls.Controller.AdapterControl.viewholdercontrols onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {

		android.view.View view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.controls.R.layout.row_controls, parent, false);
		viewholdercontrols holder = new viewholdercontrols(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@androidx.annotation.NonNull com.example.controls.Controller.AdapterControl.viewholdercontrols holder, int position) {

		Control control = controlList.get(position);

//		holder.uid.setText(control.getUid());
		holder.rut.setText(control.getRut());
		holder.fullName.setText(control.getFullName());
		holder.dateTime.setText(control.getDateTime());
		holder.bloodPresure.setText(control.getBloodPresure());
		holder.heartFreq.setText(control.getHeartFreq());
		holder.saturation.setText(control.getSaturation());
		holder.breathFreq.setText(control.getBreathFreq());
		holder.temperture.setText(control.getTemperature());
		holder.hgt.setText(control.getHgt());
		holder.notes.setText(control.getNotes());

	}

	@Override
	public int getItemCount() {

		return controlList.size();
	}

	public class viewholdercontrols extends androidx.recyclerview.widget.RecyclerView.ViewHolder {


		TextView uid, rut, fullName, dateTime, bloodPresure, heartFreq, saturation, breathFreq, temperture, hgt, notes;

		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy   hh:mm");
		String time = simpleDateFormat.format( date );

		public viewholdercontrols(@androidx.annotation.NonNull android.view.View itemView) {
			super( itemView );


			rut = itemView.findViewById(R.id.rut);
			fullName = itemView.findViewById(R.id.fullName);

			dateTime = itemView.findViewById(R.id.dateTime);


			bloodPresure = itemView.findViewById(R.id.bloodPresure);
			heartFreq = itemView.findViewById(R.id.heartFreq);
			saturation = itemView.findViewById(R.id.saturation);
			breathFreq = itemView.findViewById(R.id.breathFreq);
			temperture = itemView.findViewById(R.id.temperture);
			hgt = itemView.findViewById(R.id.hgt);
			notes = itemView.findViewById(R.id.notes);




		}
	}
}
