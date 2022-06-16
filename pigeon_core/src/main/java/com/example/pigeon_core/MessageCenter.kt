package com.example.pigeon_core

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.pigeon_core.extention.setLifeCycle
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import java.util.concurrent.ConcurrentHashMap

object MessageCenter {
    var events = ConcurrentHashMap<Any, MutableSharedFlow<Any>>()
        private set

    var stickyEvents = ConcurrentHashMap<Any, MutableSharedFlow<Any>>()
        private set


    inline fun <reified T> post(event: T, isStick: Boolean) {
        val cls = T::class.java
        if (!isStick) {
            events.getOrElse(cls) {
                MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
            }.tryEmit(event as Any)
        } else {
            stickyEvents.getOrElse(cls) {
                MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
            }.tryEmit(event as Any)

        }

    }

    inline fun <reified T> onEvent(
        event: Class<T>,
        crossinline dos: (T) -> Unit,
        owner: LifecycleOwner,
        env: SubscribeEnv
    ) {
        initEvent(event)
        val coroutineScope: CoroutineScope = when (env) {
            SubscribeEnv.IO -> CoroutineScope(Dispatchers.IO)
            SubscribeEnv.DEFAULT -> CoroutineScope(Dispatchers.Default)
            else -> CoroutineScope(Dispatchers.Main)
        }

        coroutineScope.launch {
            events[event]?.collect {
                if (it is T) {
                    dos.invoke(it)
                }
            }

        }.setLifeCycle(owner.lifecycle)

        coroutineScope.launch {
            stickyEvents[event]?.collect {
                if (it is T) {
                    dos.invoke(it)
                }
            }
        }.setLifeCycle(owner.lifecycle)


    }

    fun <T> initEvent(event: Class<T>) {
        if (!events.containsKey(event)) {
            events[event] = MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
        }
        if (!stickyEvents.containsKey(event)) {
            stickyEvents[event] = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
        }
    }

}
