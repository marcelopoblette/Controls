package com.example.controls.Models;

public class Control {
    private String uid;
    private String rut;
    private String fullName;
    private String dateTime;
    private String bloodPresure;
    private String heartFreq;
    private String saturation;
    private String breathFreq;
    private String temperature;
    private String hgt;
    private String notes;

    public Control() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBloodPresure() {
        return bloodPresure;
    }

    public void setBloodPresure(String bloodPresure) {
        this.bloodPresure = bloodPresure;
    }

    public String getHeartFreq() {
        return heartFreq;
    }

    public void setHeartFreq(String heartFreq) {
        this.heartFreq = heartFreq;
    }

    public String getSaturation() {
        return saturation;
    }

    public void setSaturation(String saturation) {
        this.saturation = saturation;
    }

    public String getBreathFreq() {
        return breathFreq;
    }

    public void setBreathFreq(String breathFreq) {
        this.breathFreq = breathFreq;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHgt() {
        return hgt;
    }

    public void setHgt(String hgt) {
        this.hgt = hgt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return
                rut+"\n"+
                "Nombre\n"+fullName+""+"\n"+
                "Fecha y Hora\n"+dateTime+" hrs"+"\n"+//+dateTime+
                "Presión Arterial\n"+bloodPresure+"  mmHg"+"\n"+
                "Frecuencia Cardiaca\n"+heartFreq+"  x min"+"\n"+
                "Saturación\n"+saturation+"  %"+"\n"+
                "Frecuencia Respiratoria\n"+breathFreq+"  x min"+"\n"+
                "Temperatura \n"+temperature+"  °C"+"\n"+
                "HemoGlucoTest\n"+hgt+"  mg/dl"+"\n"+
                "Observaciones\n"+notes+"\n";

    }

}
