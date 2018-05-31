package com.example.asus.cinemaxx.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {
    private Integer id;
    private String name;
    private String genre_id;
    private String description;
    private String cast_star;
    private Integer length;
    private String releaseDate;
    private String director;
    private String producer;
    private ArrayList<Schedule> schedules;

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        genre_id = in.readString();
        description = in.readString();
        cast_star = in.readString();
        if (in.readByte() == 0) {
            length = null;
        } else {
            length = in.readInt();
        }
        releaseDate = in.readString();
        director = in.readString();
        producer = in.readString();
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
        dest.writeString(genre_id);
        dest.writeString(description);
        dest.writeString(cast_star);
        if (length == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(length);
        }
        dest.writeString(releaseDate);
        dest.writeString(director);
        dest.writeString(producer);
        dest.writeTypedList(schedules);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
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

    public String getGenreId() {
        return genre_id;
    }

    public void setGenreId(String genreId) {
        this.genre_id = genre_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCastStar() {
        return cast_star;
    }

    public void setCastStar(String castStar) {
        this.cast_star = castStar;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }
}
