package com.example.mycomposem3playground.cdiHilt

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppMainDB  //example of qualifier , look how it is used in LocaldataSource