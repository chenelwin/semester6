package com.example.asus.cinemaxx.Model;

public class Movie {
    private Integer id;
    private String name;
    private String genreId;
    private String description;
    private String castStar;
    private Integer length;
    private String releaseDate;
    private String director;
    private String producer;

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
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCastStar() {
        return castStar;
    }

    public void setCastStar(String castStar) {
        this.castStar = castStar;
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
}
