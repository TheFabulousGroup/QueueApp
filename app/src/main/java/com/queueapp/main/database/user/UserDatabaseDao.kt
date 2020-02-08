package com.queueapp.main.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from users_table WHERE userId = :id")
    fun get(id: Long): User?

    @Query("DELETE FROM users_table")
    fun clear()

    @Query("Select * FROM users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * from users_table WHERE username = :selectedId AND password = :selectedPass")
    fun correctUser(selectedId: String, selectedPass: String): User?
}