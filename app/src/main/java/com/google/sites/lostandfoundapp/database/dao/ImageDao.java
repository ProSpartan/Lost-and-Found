package com.google.sites.lostandfoundapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.google.sites.lostandfoundapp.database.entities.ImageEntity;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("SELECT * FROM IMAGEENTITY")
    List<ImageEntity> getImages();
    @Insert
    void insertImage(ImageEntity image);
    @Delete
    void deleteImage(ImageEntity image);
    @Update
    void updateImage(ImageEntity image);
}
