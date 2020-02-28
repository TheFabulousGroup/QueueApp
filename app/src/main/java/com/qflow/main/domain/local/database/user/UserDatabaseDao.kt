package com.qflow.main.domain.local.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Here we define the querys for the table user
 * */
@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(userDB: UserDB)

    @Update
    fun update(userDB: UserDB)

    @Query("SELECT * from users_table WHERE userId = :id")
    fun get(id: Long): UserDB?

    @Query("DELETE FROM users_table")
    fun clear()

    @Query("Select * FROM users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<UserDB>>

    @Query("SELECT * from users_table WHERE username = :selectedId AND password = :selectedPass")
    fun correctUser(selectedId: String, selectedPass: String): UserDB?
}