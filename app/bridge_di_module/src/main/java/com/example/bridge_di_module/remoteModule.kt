package com.example.bridge_di_module

import com.example.bridge_di_module.EnvironmentConfig.BASE_DOMAIN
import com.example.bridge_di_module.EnvironmentConfig.allowedSSlFingerprints
import com.example.datamodule.setup.AppServiceFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule = module {
    single(named("HTTP_CLIENT")) { com.example.datamodule.setup.HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints) }
    single(named("SERVICE_FACTORY")) { com.example.datamodule.setup.ServiceFactory(EnvironmentConfig.BASE_URL) }
    single(named("APP_SERVICE")) {
        AppServiceFactory(get(named("HTTP_CLIENT"))).getAppService(get(named("SERVICE_FACTORY")))
    }
}