package com.google.sites.lostandfoundapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.google.sites.lostandfoundapp.database.entities.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM USERENTITY LIMIT 1")
    UserEntity getUser();
    @Query("SELECT * FROM USERENTITY WHERE NAMEFIRST LIKE :firstName AND NAMELAST LIKE :lastName LIMIT 1")
    UserEntity findByName(final String firstName, final String lastName);
    @Query("SELECT * FROM USERENTITY WHERE UID = :uid LIMIT 1")
    UserEntity findById(final int uid);
    @Insert
    void insertUser(UserEntity userEntity);
    @Update
    void updateUser(UserEntity userEntity);
}
