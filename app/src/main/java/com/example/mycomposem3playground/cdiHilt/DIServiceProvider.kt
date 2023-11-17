package com.example.mycomposem3playground.cdiHilt

import android.content.Context
import androidx.room.Room
import com.example.mycomposem3playground.cdiHilt.EnvironmentConfig.BASE_DOMAIN
import com.example.mycomposem3playground.cdiHilt.EnvironmentConfig.allowedSSlFingerprints
import com.example.mycomposem3playground.data.local.LocalDataSource
import com.example.mycomposem3playground.data.local.database.AppDatabase
import com.example.mycomposem3playground.data.local.database.DatabaseConstants
import com.example.mycomposem3playground.data.remote.AppService
import com.example.mycomposem3playground.domain.repository.IRepository
import com.example.mycomposem3playground.domain.repository.Repository
import com.example.mycomposem3playground.data.setup.AppServiceFactory
import com.example.mycomposem3playground.data.setup.HttpClientFactory
import com.example.mycomposem3playground.data.setup.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIServiceProvider {
    @Singleton
    @AppMainDB
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DatabaseConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClientFactory(): HttpClientFactory {
        return HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints)
    }

    @Singleton
    @Provides
    fun provideServiceFactory(): ServiceFactory {
        return ServiceFactory(EnvironmentConfig.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideService(httpClientFactory: HttpClientFactory, serviceFactory : ServiceFactory): AppService {
        return AppServiceFactory(httpClientFactory).getAppService(serviceFactory)
    }

    @Singleton
    @Provides
    fun provideRepository(appService: AppService, localDataSource: LocalDataSource): IRepository {
        return Repository(appService, localDataSource)
    }
}
