package com.google.sites.lostandfoundapp.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ImageEntity {
    @PrimaryKey
    private int iid;
    @ColumnInfo(name = "DESCRIPTION")
    private String description;
    @ColumnInfo(name = "LOCATION")
    private String location;
    @ColumnInfo(name = "IMAGEBYTES")
    private String imageBytes;

    public ImageEntity(final int iid, final String description, final String location, final String imageBytes) {
        this.iid = iid;
        this.description = description;
        this.location = location;
        this.imageBytes = imageBytes;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setImageBytes(final String imageBytes) {
        this.imageBytes = imageBytes;
    }

    public int getIid() {
        return iid;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getImageBytes() {
        return imageBytes;
    }
}
