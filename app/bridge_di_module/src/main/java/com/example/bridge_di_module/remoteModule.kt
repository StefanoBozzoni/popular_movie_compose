package com.example.bridge_di_module

import com.example.bridge_di_module.EnvironmentConfig.BASE_DOMAIN
import com.example.bridge_di_module.EnvironmentConfig.allowedSSlFingerprints
import com.example.datamodule.setup.AppServiceFactory
import com.example.datamodule.setup.HttpClientFactory
import com.example.datamodule.setup.ServiceFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule = module {
    single(named("HTTP_CLIENT")) { HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints, BuildConfig.DEBUG) }
    single(named("SERVICE_FACTORY")) { ServiceFactory(EnvironmentConfig.BASE_URL) }
    single(named("APP_SERVICE")) {
        AppServiceFactory(get(named("HTTP_CLIENT"))).getAppService(get(named("SERVICE_FACTORY")))
    }
}