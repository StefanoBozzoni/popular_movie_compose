import androidx.room.Room
import com.example.datamodule.data.local.LocalDataSource
import com.example.datamodule.data.local.database.DatabaseConstants
import com.example.datamodule.data.repository.Repository

import com.example.domainmodule.IRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localModule = module {
    single(named(DatabaseConstants.DATABASE_NAME)) {
        Room.databaseBuilder(androidContext(), com.example.datamodule.data.local.database.AppDatabase::class.java, com.example.datamodule.data.local.database.DatabaseConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single(named("LOCAL_DATA_SOURCE")) { LocalDataSource(get(named(DatabaseConstants.DATABASE_NAME))) }
    single<IRepository> { Repository(get(named("APP_SERVICE")), localDataSource = get(named("LOCAL_DATA_SOURCE"))) }
}