package com.example.mymodule

import javax.inject.Inject

class Nation @Inject constructor() {
    val nationCode: String = ""
    val descr: String = ""
    fun printHello() {
        System.out.println("hello")
    }
}