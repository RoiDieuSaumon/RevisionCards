package com.saumon.revisioncards.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

// Cours
@Entity(
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "id",
                childColumns = "subjectId",
                onDelete = CASCADE
        ),
        indices = {
                @Index(value = {"subjectId"})
        }
)
public class Lesson {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int position;
    private long subjectId;

    public Lesson(String name, int position, long subjectId) {
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }
}
