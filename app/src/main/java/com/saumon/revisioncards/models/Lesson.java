package com.saumon.revisioncards.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

// Cours
@Entity(
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "id",
                childColumns = "subjectId"
        )
)
public class Lesson {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long position;
    private long subjectId;

    public Lesson(String name, long position, long subjectId) {
        this.name = name;
        this.position = position;
        this.subjectId = subjectId;
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

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }
}
