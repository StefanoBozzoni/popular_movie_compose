package com.example.mycomposem3playground.cdiHilt

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)  //not visibile to reflection otherwhise it would be RUNTIME
annotation class AppMainDB  //example of qualifier , look how it is used in LocaldataSource