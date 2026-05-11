package com.vampyreworld.w2t.core.arch

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.rx.Disposable
import com.arkivanov.mvikotlin.rx.observer

fun <T : Any> Store<*, T, *>.asValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state

        override fun subscribe(observer: (T) -> Unit): com.arkivanov.decompose.value.Disposable {
            val disposable = this@asValue.states(observer(onNext = observer))
            return com.arkivanov.decompose.value.Disposable {
                disposable.dispose()
            }
        }
    }
