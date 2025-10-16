package com.example.news.data

sealed class RequestResult<E>(val data: E? = null) {

    class InProgress<E>(data: E? = null) : RequestResult<E>(data)

    class Success<E>(data: E?) : RequestResult<E>(data)

    class Error<E>(data: E? = null, val error: Throwable? = null) : RequestResult<E>(data)
}

fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    return when (this) {
        is RequestResult.Success -> {
            val outData: O = mapper(checkNotNull(data))
            RequestResult.Success(checkNotNull(outData))
        }

        is RequestResult.Error -> RequestResult.Error(data?.let(mapper))

        is RequestResult.InProgress -> RequestResult.InProgress(data?.let(mapper))
    }
}

fun <T> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error()
        else -> error("Impossible branch")
    }
}