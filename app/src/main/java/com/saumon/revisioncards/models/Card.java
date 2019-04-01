package com.saumon.revisioncards.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

// Fiche
@Entity(
        foreignKeys = @ForeignKey(
                entity = Part.class,
                parentColumns = "id",
                childColumns = "partId"
        )
)
public class Card {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String text1;
    private String text2;
    private int sideToShow;
    private long position;
    private long partId;

    public Card(String name, String text1, String text2, long position, long partId) {
        this.name = name;
        this.text1 = text1;
        this.text2 = text2;
        this.position = 1;
        this.position = position;
        this.partId = partId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getSideToShow() {
        return sideToShow;
    }

    public void setSideToShow(int sideToShow) {
        this.sideToShow = sideToShow;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPartId() {
        return partId;
    }

    public void setPartId(long partId) {
        this.partId = partId;
    }
}
