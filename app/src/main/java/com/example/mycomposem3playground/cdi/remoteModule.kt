package com.example.mycomposem3playground.cdi

import com.example.mycomposem3playground.cdi.EnvironmentConfig.BASE_DOMAIN
import com.example.mycomposem3playground.cdi.EnvironmentConfig.allowedSSlFingerprints
import com.example.mycomposem3playground.data.local.LocalDataSource
import com.example.mycomposem3playground.data.repository.IRepository
import com.example.mycomposem3playground.data.repository.Repository
import com.example.mycomposem3playground.data.setup.AppServiceFactory
import com.example.mycomposem3playground.data.setup.HttpClientFactory
import com.example.mycomposem3playground.data.setup.ServiceFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule = module {
    single(named("HTTP_CLIENT")) { HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints) }
    single(named("SERVICE_FACTORY")) { ServiceFactory(EnvironmentConfig.BASE_URL) }
    single(named("APP_SERVICE")) {
        AppServiceFactory(get(named("HTTP_CLIENT"))).getAppService(get(named("SERVICE_FACTORY")))
    }
    single<IRepository> { Repository(get(named("APP_SERVICE")), localDataSource = get(named("LOCAL_DATA_SOURCE"))) }
}