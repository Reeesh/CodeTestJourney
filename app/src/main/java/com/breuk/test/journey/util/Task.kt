package com.breuk.test.journey.util

sealed class Task<T>(val data: T? = null, val exception: Throwable? = null) {
    class Loading<T>(data: T? = null) : Task<T>(data)
    class Success<T>(data: T?) : Task<T>(data)
    class Error<T>(exception: Throwable, data: T? = null) : Task<T>(data, exception)
}