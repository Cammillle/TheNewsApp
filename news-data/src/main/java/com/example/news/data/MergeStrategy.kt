package com.example.news.data

interface MergeStrategy<E> {

    fun merge(
        right: E,
        left: E
    ): E

}

internal class RequestResponseMergeStrategy<T> : MergeStrategy<RequestResult<T>> {

    override fun merge(right: RequestResult<T>, left: RequestResult<T>): RequestResult<T> {
        return when {
            right is RequestResult.InProgress && left is RequestResult.InProgress ->
                merge(right, left)

            right is RequestResult.Success && left is RequestResult.InProgress ->
                merge(right, left)

            right is RequestResult.Success && left is RequestResult.Error ->
                TODO()

            else -> error("Unimplemented branch right=$right & left=$left")
        }
    }


    /**Обе ветки в прогрессе*/
    private fun merge(
        cache: RequestResult.InProgress<T>,
        server: RequestResult.InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null -> RequestResult.InProgress(server.data)
            else -> RequestResult.InProgress(cache.data)
        }
    }


    private fun merge(
        cache: RequestResult.Success<T>,
        server: RequestResult.InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null -> RequestResult.InProgress(server.data)
            else -> RequestResult.InProgress(cache.data)
        }
    }


}