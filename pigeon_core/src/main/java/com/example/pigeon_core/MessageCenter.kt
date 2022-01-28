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
    var stickyEvents = ConcurrentHashMap<Any, MutableSharedFlow<Any>>()


    inline fun <reified T> post(event: T, isStick: Boolean) {
        val cls = T::class.java
        if (!isStick) {
            events[cls]?.tryEmit(event as Any) ?: run {
                events[cls] = MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
                events[cls]!!.tryEmit(event as Any)
            }
        } else {
            stickyEvents[cls]?.tryEmit(event as Any) ?: run {
                stickyEvents[cls] = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
                stickyEvents[cls]!!.tryEmit(event as Any)
            }
        }


    }

    inline fun <reified T> onEvent(
        event: Class<T>,
        crossinline dos: (T) -> Unit,
        owner: LifecycleOwner,
        env:SubscribeEnv
    ) {
        if (!events.containsKey(event)) {
            events[event] = MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
        }
        if (!stickyEvents.containsKey(event)) {
            stickyEvents[event] = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
        }
        val coroutineScope:CoroutineScope = when (env) {
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
            stickyEvents[event]?.collect {
                if (it is T) {
                    dos.invoke(it)
                }
            }
        }.setLifeCycle(owner.lifecycle)

    }
}
