package com.yoy.foodiemap.utils

import java.util.concurrent.Executors


private val IO_EXECUTOR = Executors.newSingleThreadExecutor()


fun runOnIoThread(f: () -> Unit){
    IO_EXECUTOR.execute(f)
}