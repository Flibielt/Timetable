package com.example.timetable.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LessonDao {

    @Insert
    fun insert(lesson: Lesson)

    @Update
    fun update(lesson: Lesson)

    @Query("select * from lessons where id = :key")
    fun get(key: Long): Lesson?

    @Query("delete from lessons")
    fun clear()

    @Query("select * from lessons order by id DESC")
    fun getAllLessons(): LiveData<List<Lesson>>

    @Query("select * from lessons")
    fun getEveryLesson(): List<Lesson>

    @Query("select * from lessons order by id DESC limit 1")
    fun getLastAddedLesson(): Lesson?
}