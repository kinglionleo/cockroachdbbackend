package com.example.restservice;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Entity
@Table(name = "REVIEW")
public class Review implements Serializable{

    @Id
    @Column(name = "id")
    public long id;

    public long getId() {
        return id;
    }

    // --------------------

    @Column(name = "location")
    public String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // --------------------

    @Column(name = "toiletRating")
    public int toiletRating;

    public int getToiletRating() {
        return toiletRating;
    }

    public void setToiletRating(int toiletRating) {
        this.toiletRating = toiletRating;
    }

    // ---------------------

    @Column(name = "sinkRating")
    public int sinkRating;

    public int getSinkRating() {
        return sinkRating;
    }

    public void setSinkRating(int sinkRating) {
        this.sinkRating = sinkRating;
    }

    // ---------------------

    @Column(name = "noiseRating")
    public int noiseRating;

    public int getNoiseRating() {
        return noiseRating;
    }

    public void setNoiseRating(int noiseRating) {
        this.noiseRating = noiseRating;
    }

    // ---------------------

    @Column(name = "comment")
    public String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // --------------------

    // Convenience constructor.
    public Review(long id, String location, int toiletRating, int sinkRating, int noiseRating, String comment) {
        this.id = id;
        this.location = location;
        this.toiletRating = toiletRating;
        this.sinkRating = sinkRating;
        this.noiseRating = noiseRating;
        this.comment = comment;
    }

    // Hibernate needs a default (no-arg) constructor to create model objects.
    public Review() {
    }
}