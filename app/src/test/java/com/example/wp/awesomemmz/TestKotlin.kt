package com.example.wp.awesomemmz

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.junit.Test
import java.util.*
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

//        val g = GlobalScope.launch(Dispatchers.Main) {
//            println("${Thread.currentThread().name}---")
//        }
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
        
        myDispatcher.close()//关闭线程
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
        val user = getUserInfo()//开启新的协程/挂起函数, 外部协程会挂起
        println(user)//外部协程恢复
        val friends = getFriends(user)//开启新的协程/挂起函数, 外部协程会挂起
        println(friends)//外部协程恢复
    }
    
    @Test
    fun testCoroutine() = runBlocking {
        testCoroutineS()
    }
    
    fun asd(block: () -> Unit) {
        println("-----start------")
        block.invoke()
        println("-----end------")
    }
    
    @Test
    fun testAsd() {
        asd {
            println("----1")
        }
        asd {
            println("----2")
        }
    }
    
    private fun log(any: Any) {
        val time = Calendar.getInstance()
        val timeStr = String.format("%02d:%02d:%02d.%03d", time.get(Calendar.HOUR), time.get(Calendar.MINUTE), time.get(Calendar.SECOND), time.get(Calendar.MILLISECOND))
        println("$timeStr  $any  ----- ${Thread.currentThread().name}")
    }
    
    @Test
    fun testMainScope() {
        runBlocking { // runBlocking 是会阻塞主线程的，直到 runBlocking 内部全部子任务执行完毕
            log(1)
            val remoteInfo: String =
                withContext(Dispatchers.IO) {
                    log(2)
                    delay(1000)//开启新的协程, 外部协程会挂起
    
                    withContext(Dispatchers.Default) {//开启新的协程, 外部协程会挂起
                        log(3)
                        delay(1000)//开启新的协程, 外部协程会挂起
                    }
                    log(4)
                    
                    "result info......"
                }
            log("99 $remoteInfo")
        }
        
        log(0)
    }
    
    @Test
    fun testAsync(){
        runBlocking {
            //多个 withContext 任务是串行的， 且withContext 可直接返回耗时任务的结果。
            // withContext 是 suspend 函数, 会挂起当前线程.
            val w1 = withContext(Dispatchers.IO){
                delay(2000)
                log(1)
                123
            }
            log("end withContext1: $w1")
            val w2 = withContext(Dispatchers.IO){
                delay(1000)
                log(2)
                "asdfgh"
            }
            log("end withContext2: $w2")
            
            //多个 async 任务是并行的，async 返回的是一个Deferred<T>，需要调用其await()方法获取结果
            // withContext 不是 suspend 函数, 不会挂起当前线程.
            val task1 = async(Dispatchers.IO){
                delay(5000)
                log(3)
                "result1"
            }
            log("end task1")//会先于"result1"打印, 说明task1是没有挂起当前线程的(runBlocking所在的线程)
            val task2 = async(Dispatchers.IO){
                delay(1000)
                log(4)
                "result2"
            }
            log("end task2")
            
            //log(task1.await()) // await 是 suspend 函数, 会挂起当前线程.
            //log(task2.await())
        }
        log("end block")
    }
    
    @Test
    fun testRepeat(){
        runBlocking {
//            repeat(100_000) { // 启动大量的协程
//                launch {
//                    delay(5000L)
//                    log(".")
//                }
//            }
    
            //在 GlobalScope 中启动的活动协程并不会使进程保活。它们就像守护线程
            GlobalScope.launch {
                repeat(1000) { i ->
                    log("I'm sleeping $i ...")
                    delay(500L)
                }
//                delay(1000)
//                log("------")
            }
            delay(5000L) // 在延迟后退出
            
        }
    }
}