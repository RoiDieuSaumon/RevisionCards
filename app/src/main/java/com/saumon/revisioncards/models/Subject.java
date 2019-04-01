package com.saumon.revisioncards.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

// Matière
@Entity
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long position;

    public Subject(String name, long position) {
        this.name = name;
        this.position = position;
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

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
