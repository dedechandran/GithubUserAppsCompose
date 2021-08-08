package com.compose.githubuserapps.di

import com.compose.githubuserapps.data.GithubUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideGithubUserService(retrofit: Retrofit): GithubUserService {
        return retrofit.create(GithubUserService::class.java)
    }
}