package com.example.domainmodule.model

interface IDataProvider<T> {
    fun getMyData(): T
    fun setMyData(pdata: T)
}