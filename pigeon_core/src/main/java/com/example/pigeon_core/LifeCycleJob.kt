package com.example.pigeon_core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job

class LifeCycleJob(private val job: Job) : Job by job, LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.cancel()
        }
    }

    override fun cancel(cause: CancellationException?) {
        if (!job.isCancelled) {
            job.cancel()
        }
    }

}





