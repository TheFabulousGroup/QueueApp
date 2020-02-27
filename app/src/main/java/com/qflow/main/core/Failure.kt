package com.qflow.main.core
/*
* @author  Iván Fernández Rico, Globalincubator
*/
sealed class Failure {
    object NetworkConnection : Failure()
    class ServerErrorCode(val code: Int): Failure()
    class ServerException(val throwable: Throwable): Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()

    class NullResult: FeatureFailure()


}