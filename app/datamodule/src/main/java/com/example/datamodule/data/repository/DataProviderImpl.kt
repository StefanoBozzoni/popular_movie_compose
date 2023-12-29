package com.example.datamodule.data.repository

import com.example.domainmodule.model.IDataProvider

internal class DatProviderImpl<T : Any> : IDataProvider<T> {
    private lateinit var myData: T
    override fun getMyData(): T {
        return myData
    }

    override fun setMyData(pdata: T) {
        myData = pdata
    }
}