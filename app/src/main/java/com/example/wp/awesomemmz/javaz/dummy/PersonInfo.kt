package com.example.wp.awesomemmz.javaz.dummy

/**
 * Created by wp on 2020/9/3.
 */
class PersonInfo() {
    private var mName: String? = null
    private var mAge: Int? = null
    private var mAddress: String? = null
    private var mOo: String? = null

    constructor(name: String, age: Int, address: String) : this(name, age, address, null) {
    }

    private constructor(name: String, age: Int, address: String, oo: String?) : this() {
        this.mName = name
        this.mAge = age
        this.mAddress = address
        this.mOo = oo
    }

    private fun getPhoneInfo(): String {
        return "$mName: 138****1567"
    }

    private fun getPhoneInfo2(extra: String): String {
        return "$mName: 138****1567--$extra"
    }

    override fun toString(): String {
        return "Person {name = $mName, age = $mAge, address = $mAddress, oo = $mOo}"
    }
}