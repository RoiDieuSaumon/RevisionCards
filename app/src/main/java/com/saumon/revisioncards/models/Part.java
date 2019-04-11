package com.saumon.revisioncards.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

// Partie
@Entity(
        foreignKeys = @ForeignKey(
                entity = Lesson.class,
                parentColumns = "id",
                childColumns = "lessonId"
        )
)
public class Part {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int position;
    private long lessonId;

    public Part(String name, int position, long lessonId) {
        this.name = name;
        this.position = position;
        this.lessonId = lessonId;
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

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }
}
