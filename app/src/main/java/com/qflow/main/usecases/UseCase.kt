package com.qflow.main.usecases

import kotlinx.coroutines.*
import com.qflow.main.core.Failure

/**
 * UseCase main structure
 * */
abstract class UseCase<out Type, in Params, in Scope> where Type : Any, Scope : CoroutineScope {



    abstract suspend fun run(params: Params): Either<Failure, Type>

    fun execute(onResult: (Either<Failure, Type>) -> Unit, params: Params, scope: Scope) {

        scope.launch {

            val deferred = async { run(params) }

            withContext(Dispatchers.Main)
            {
                onResult.invoke(deferred.await())
            }
        }
    }

    class None
}

abstract class UseCaseSync<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    suspend fun execute(params: Params): Either<Failure, Type> = run(params)

}

sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()
    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRht get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }

    fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
            when (this) {
                is Left -> Left(a)
                is Right -> fn(b)
            }
}
