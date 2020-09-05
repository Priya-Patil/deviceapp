package com.pvp.deviceapp.admin.fragment;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsModel implements Parcelable {

    int id;
    String Internal_diameter;
    String Number_of_terms;
    String Wire_diameter;
    String Start_wait_time;
    String Stop_wait_time;
    String Machine_Speed;
    String Die_Diameter;
    String isActive;
    String created;
    String title;


    protected DetailsModel(Parcel in) {
        id = in.readInt();
        Internal_diameter = in.readString();
        Number_of_terms = in.readString();
        Wire_diameter = in.readString();
        Start_wait_time = in.readString();
        Stop_wait_time = in.readString();
        Machine_Speed = in.readString();
        Die_Diameter = in.readString();
        isActive = in.readString();
        created = in.readString();
        title = in.readString();
    }

    public static final Creator<DetailsModel> CREATOR = new Creator<DetailsModel>() {
        @Override
        public DetailsModel createFromParcel(Parcel in) {
            return new DetailsModel(in);
        }

        @Override
        public DetailsModel[] newArray(int size) {
            return new DetailsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInternal_diameter() {
        return Internal_diameter;
    }

    public void setInternal_diameter(String internal_diameter) {
        Internal_diameter = internal_diameter;
    }

    public String getNumber_of_terms() {
        return Number_of_terms;
    }

    public void setNumber_of_terms(String number_of_terms) {
        Number_of_terms = number_of_terms;
    }

    public String getWire_diameter() {
        return Wire_diameter;
    }

    public void setWire_diameter(String wire_diameter) {
        Wire_diameter = wire_diameter;
    }

    public String getStart_wait_time() {
        return Start_wait_time;
    }

    public void setStart_wait_time(String start_wait_time) {
        Start_wait_time = start_wait_time;
    }

    public String getStop_wait_time() {
        return Stop_wait_time;
    }

    public void setStop_wait_time(String stop_wait_time) {
        Stop_wait_time = stop_wait_time;
    }

    public String getMachine_Speed() {
        return Machine_Speed;
    }

    public void setMachine_Speed(String machine_Speed) {
        Machine_Speed = machine_Speed;
    }

    public String getDie_Diameter() {
        return Die_Diameter;
    }

    public void setDie_Diameter(String die_Diameter) {
        Die_Diameter = die_Diameter;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DetailsModel(int id, String internal_diameter, String number_of_terms, String wire_diameter, String start_wait_time, String stop_wait_time, String machine_Speed, String die_Diameter, String isActive, String created, String title) {
        this.id = id;
        Internal_diameter = internal_diameter;
        Number_of_terms = number_of_terms;
        Wire_diameter = wire_diameter;
        Start_wait_time = start_wait_time;
        Stop_wait_time = stop_wait_time;
        Machine_Speed = machine_Speed;
        Die_Diameter = die_Diameter;
        this.isActive = isActive;
        this.created = created;
        this.title = title;
    }

    protected DetailsModel() {

    }

    @Override
    public String toString() {
        return "DetailsModel{" +
                "id=" + id +
                ", Internal_diameter='" + Internal_diameter + '\'' +
                ", Number_of_terms='" + Number_of_terms + '\'' +
                ", Wire_diameter='" + Wire_diameter + '\'' +
                ", Start_wait_time='" + Start_wait_time + '\'' +
                ", Stop_wait_time='" + Stop_wait_time + '\'' +
                ", Machine_Speed='" + Machine_Speed + '\'' +
                ", Die_Diameter='" + Die_Diameter + '\'' +
                ", isActive='" + isActive + '\'' +
                ", created='" + created + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Internal_diameter);
        parcel.writeString(Number_of_terms);
        parcel.writeString(Wire_diameter);
        parcel.writeString(Start_wait_time);
        parcel.writeString(Stop_wait_time);
        parcel.writeString(Machine_Speed);
        parcel.writeString(Die_Diameter);
        parcel.writeString(isActive);
        parcel.writeString(created);
        parcel.writeString(title);
    }
}