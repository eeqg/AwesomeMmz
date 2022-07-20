package com.example.wp.awesomemmz

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import java.util.concurrent.Executors

/**
 * @description
 * @author wp
 * @date   2022/7/19 10:13
 */
class TestKotlin {
    @Test
    fun testAa() {
        runBlocking {
            flow<Int> {
                (0..5).forEach {
                    println("${Thread.currentThread().name}-2--$it")
                    delay(1000)
                    emit(it)
                }
            }.map {
                "NO.$it"
            }
                .collect {
                    println("${Thread.currentThread().name}---$it")
                }
        }

        val g = GlobalScope.launch(Dispatchers.Main) {
            println("${Thread.currentThread().name}---")
        }
    }
    
    @Test
    fun testFlowOn() = runBlocking {
        val myDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        
        flow {
            println("${Thread.currentThread().name}---emit")
            emit("data")
        }
            .map {
                println("${Thread.currentThread().name}---map1")
                "map: $it"
            }
            .flowOn(myDispatcher)//作用于前面flow创建与第一个map
            .map {
                println("${Thread.currentThread().name}---map2")
                "$it, ${it.length}"
            }
            .flowOn(Dispatchers.IO)//作用于第二个map
            .collect {
                println("${Thread.currentThread().name}---collect---$it")
            }
    }
    
    //挂起函数
    suspend fun getUserInfo(): String {
        withContext(Dispatchers.IO) {
            //模拟网络延时
            delay(1000)
        }
        
        return "Bob..."
    }
    
    suspend fun getFriends(user: String): String {
        withContext(Dispatchers.IO) {
            delay(1000)
        }
        return "Jack and Tom"
    }
    
    suspend fun testCoroutineS() {
        println("start")
        val user = getUserInfo()
        println(user)
        val friends = getFriends(user)
        println(friends)
    }
    
    @Test
    fun testCoroutine() = runBlocking {
        testCoroutineS()
    }
}