package com.example.submission.data.database.dao

import androidx.room.*
import com.example.submission.data.database.entity.MovieEntity

@Dao
abstract class MovieDao {
    @Query("SELECT * FROM movie")
    abstract suspend fun getMovie(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    abstract suspend fun getAllMovieFavorite(): List<MovieEntity>

    @Query("UPDATE movie SET isFavorite = :favorite WHERE movieId = :idMovie")
    abstract suspend fun updateFavoriteMovie(favorite: Boolean, idMovie: Int)
}