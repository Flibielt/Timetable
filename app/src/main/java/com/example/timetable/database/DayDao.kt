package com.example.timetable.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DayDao {

    @Insert
    fun insert(day: Day)

    @Update
    fun update(day: Day)

    @Query("select * from days where id = :key")
    fun get(key: Long): Day?

    @Query("delete from days")
    fun clear()

    @Query("select * from days order by id DESC")
    fun getAllDays(): LiveData<List<Day>>
}