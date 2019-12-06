package com.example.timetable.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimetableDao {

    @Insert
    fun insert(timetable: Timetable)

    @Update
    fun update(timetable: Timetable)

    @Query("select * from timetable where id = :key")
    fun get(key: Long): Timetable?

    @Query("delete from timetable")
    fun clear()

    @Query("select * from timetable order by id desc")
    fun getAllTimetable(): LiveData<List<Timetable>>
}