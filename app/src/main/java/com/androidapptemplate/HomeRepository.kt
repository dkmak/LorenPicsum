package com.androidapptemplate

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface HomeRepository {
    fun getPictures(limit: Int): Flow<Result<List<PictureData>>>
}

class HomeRepositoryImpl @Inject constructor(
    val apiClient: ApiClient
): HomeRepository {
    override fun getPictures(limit: Int): Flow<Result<List<PictureData>>> = flow {
        val response = apiClient.getPictures(limit)
        emit(Result.success(response))
    }.catch { throwable ->
        Log.d("HomeRepository", "${throwable.message}")
        emit(Result.failure(throwable))
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository
}