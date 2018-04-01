package com.google.sites.lostandfoundapp.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "NAMEFIRST")
    private String firstName;
    @ColumnInfo(name = "NAMELAST")
    private String lastName;
    @ColumnInfo(name = "EMAIL")
    private String email;

    public UserEntity(final int uid, final String firstName, final String lastName, final String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setUid(final int uid) {
        this.uid = uid;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
