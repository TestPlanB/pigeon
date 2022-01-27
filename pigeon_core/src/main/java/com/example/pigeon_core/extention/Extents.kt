package com.example.pigeon_core.extention

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.pigeon_core.LifeCycleJob
import com.example.pigeon_core.MessageCenter
import com.example.pigeon_core.SubscribeEnv
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

inline fun <reified T> LifecycleOwner.subscribeEvent(
    event: Class<T>,
    env: SubscribeEnv = SubscribeEnv.MAIN,
    crossinline dos: (T) -> Unit
) = MessageCenter.onEvent(event, dos, this,env)

fun Job.setLifeCycle(
    owner: Lifecycle,
) {
    val proxyJob = LifeCycleJob(this)
    owner.addObserver(proxyJob)
}