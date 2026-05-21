package com.vampyreworld.w2t.sharedui.arch

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.rx.observer

fun <T : Any> Store<*, T, *>.asValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state

        override fun subscribe(observer: (T) -> Unit): Cancellation {
            val disposable = this@asValue.states(observer(onNext = observer))
            return Cancellation {
                disposable.dispose()
            }
        }
    }
