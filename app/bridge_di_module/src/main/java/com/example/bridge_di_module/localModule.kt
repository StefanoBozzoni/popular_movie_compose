import com.example.datamodule.data.local.LocalDataSource
import com.example.datamodule.data.local.database.DatabaseConstants
import com.example.datamodule.data.remote.RemoteDataSource
import com.example.datamodule.data.repository.Repository
import com.example.datamodule.setup.DatabaseFactory
import com.example.domainmodule.IRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localModule = module {
    single(named(DatabaseConstants.DATABASE_NAME)) {
        DatabaseFactory().getOrCreateDatabaseInstance(androidContext())
    }
    single(named("LOCAL_DATA_SOURCE")) { LocalDataSource(get(named(DatabaseConstants.DATABASE_NAME))) }
    single(named("REMOTE_DATA_SOURCE")) { RemoteDataSource(get(named("APP_SERVICE"))) }
    single<IRepository> {
        Repository(
            remoteDataSource = get(named("REMOTE_DATA_SOURCE")),
            localDataSource = get(named("LOCAL_DATA_SOURCE"))
        )
    }
}