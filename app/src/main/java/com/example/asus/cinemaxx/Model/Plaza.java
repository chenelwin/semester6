package com.example.asus.cinemaxx.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plaza implements Parcelable {
    private Integer id;
    private String name;
    private String city;
    private String street;
    private String phoneNumber;
    private ArrayList<Schedule> schedules;

    protected Plaza(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        city = in.readString();
        street = in.readString();
        phoneNumber = in.readString();
        schedules = in.createTypedArrayList(Schedule.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(street);
        dest.writeString(phoneNumber);
        dest.writeTypedList(schedules);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Plaza> CREATOR = new Creator<Plaza>() {
        @Override
        public Plaza createFromParcel(Parcel in) {
            return new Plaza(in);
        }

        @Override
        public Plaza[] newArray(int size) {
            return new Plaza[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }
}
