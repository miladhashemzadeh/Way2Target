package com.vampyreworld.w2t.domain.utils

sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Error(val exception: Throwable) : DataResult<Nothing>()
    data class Loading(val inLoading: Boolean) : DataResult<Nothing>()
}
suspend fun <T> safeSuspendExecute(
    block: suspend () -> T,
    onError: (Throwable) -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        onError(e)
    }
}
fun <T> safeExecute(
    block: () -> T,
    onError: (Throwable) -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        onError(e)
    }
}