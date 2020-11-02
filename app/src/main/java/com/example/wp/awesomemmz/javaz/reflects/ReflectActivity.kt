package com.example.wp.awesomemmz.javaz.reflects

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.wp.awesomemmz.APP
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ActivityReflectBinding
import com.example.wp.awesomemmz.javaz.dummy.PersonInfo
import com.example.wp.resource.utils.LogUtils
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ReflectActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityReflectBinding

    val className = "com.example.wp.awesomemmz.javaz.dummy.PersonInfo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_reflect)

        dataBinding.run {
            btnConstructor.setOnClickListener {
                val c1: Constructor<*>? = getConstructor(className)
                val c2: Constructor<*>? = getConstructor(className, String::class.java, Int::class.java, String::class.java)
                val c3: Constructor<*>? = getConstructor(className, String::class.java, Int::class.java, String::class.java, String::class.java)
                try {
                    LogUtils.d("c1: " + c1?.newInstance())

                    val o = c2?.newInstance("boo", 15, "xiamen...")
                    LogUtils.d("c2: $o")
                    APP.toast("$o")

                    LogUtils.d("c3: ${c3?.newInstance("boo", 199, "xiamen...", "7987867")}")
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }

            btnField.setOnClickListener {
                //PersonInfo::class.java.newInstance()
                val person = PersonInfo("www", 19, "china")
                val fields = getFields(className, "mAddress")
                LogUtils.d("----fields: $fields")
                try {
                    if (fields != null) {
                        LogUtils.d("-----address: ${fields.get(person) as String}")
                        APP.toast(fields.get(person) as String)
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }

            btnMethod.setOnClickListener {
                val person = PersonInfo("bb", 28, "aaaassssdddd")
                val method = getMethod(className, "getPhoneInfo")
                val method2 = getMethod(className, "getPhoneInfo2", String::class.java)

                try {
                    val phone: String = method?.invoke(person) as String
                    APP.toast(phone)

                    val phone2: String = method2?.invoke(person, "xxxxxxksdfasd") as String
                    LogUtils.d("-----$phone2")
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 获得指定的构造方法
     */
    private fun getConstructor(className: String, vararg clzs: Class<*>?): Constructor<*>? {
        try {
            val aClass = Class.forName(className)
            val declaredConstructor: Constructor<*> = aClass.getDeclaredConstructor(*clzs)
            print(declaredConstructor)
            //   if Constructor is not public,you should call this
            declaredConstructor.isAccessible = true
            return declaredConstructor
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获得成员变量
     */
    private fun getFields(className: String, fieldName: String? = null): Field? {
        try {
            val clazz = Class.forName(className)
            //if (fieldName != null) {
            //    return clazz.declaredFields //获得所有的 Field 变量
            //} else {
                val field = clazz.getDeclaredField(fieldName) //获得指定的成员变量
                field.isAccessible = true
                return field
            //}

        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取指定的 Method
     */
    private fun getMethod(className: String, methodName: String, vararg clzs: Class<*>): Method? {
        try {
            val clazz = Class.forName(className)
            val method = clazz.getDeclaredMethod(methodName, *clzs)
            method.isAccessible = true
            return method
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }
}