package com.myboost.internal.util

sealed class Either<out E, out V> {
    data class Error<out E>(val error: E) : Either<E, Nothing>()
    data class Value<out V>(val value: V) : Either<Nothing, V>()
}

fun <V> value(value: V): Either<Nothing, V> = Either.Value(value)
fun <E> error(error: E): Either<E, Nothing> = Either.Error(error)

suspend fun <E, V, A> Either<E, V>.fold(failure: suspend (E) -> A, success: suspend (V) -> A): A = when (this) {
    is Either.Error -> failure(this.error)
    is Either.Value -> success(this.value)
}
