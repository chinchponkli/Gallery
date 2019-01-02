package com.arjit.gallery.ui.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.uber.autodispose.ScopeProvider
import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject

abstract class LifeCycleAwareViewModel : LifecycleObserver, ScopeProvider {

    private val lifecycleOnDestroyObservable: PublishSubject<Any> = PublishSubject.create()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanup() {
        lifecycleOnDestroyObservable.onNext(Any())
        lifecycleOnDestroyObservable.onComplete()
        performExtraCleanup()
    }

    open fun performExtraCleanup() {
        /* do nothing by default */
    }

    override fun requestScope(): Maybe<*> = lifecycleOnDestroyObservable.firstElement()
}
