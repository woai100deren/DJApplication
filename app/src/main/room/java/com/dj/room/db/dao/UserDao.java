package com.dj.room.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dj.room.db.table.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    User findByName(String username);

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    Flowable<User> asynFindByName(String username);
}
