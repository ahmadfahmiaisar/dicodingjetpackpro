package com.example.submission.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission.domain.entity.tvshow.TvShowEntity

@Dao
abstract class TvShowDao {
    @Query("SELECT * FROM tvshows")
    abstract fun getTvShow(): LiveData<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTvShow(tvShowEntity: List<TvShowEntity>)

    @Query("SELECT * FROM tvshows WHERE isFavorite = 1")
    abstract suspend fun getAllTvFavorite(): List<TvShowEntity>

    @Query("UPDATE tvshows SET isFavorite = :favorite WHERE tvId = :idTv")
    abstract suspend fun updateFavoriteTv(favorite: Boolean, idTv: Int)
}