package com.example.submission.data.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission.domain.entity.movie.MovieEntity

@Dao
abstract class MovieDao {
    @Query("SELECT * FROM movie ORDER BY movieId ASC")
    abstract fun getAllMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie ORDER BY movieId ASC limit :pageSize")
    abstract suspend fun getMovie(pageSize: Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(movieEntity: List<MovieEntity>)

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    abstract suspend fun getAllMovieFavorite(): List<MovieEntity>

    @Query("UPDATE movie SET isFavorite = :favorite WHERE movieId = :idMovie")
    abstract suspend fun updateFavoriteMovie(favorite: Boolean, idMovie: Int)
}