package com.example.submission.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission.data.database.entity.MovieEntity

@Dao
abstract class MovieDao {
    @Query("SELECT * FROM movie")
    abstract suspend fun getMovie(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    abstract suspend fun getAllMovieFavorite(): List<MovieEntity>
}