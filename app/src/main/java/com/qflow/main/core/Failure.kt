package com.qflow.main.core

import com.qflow.main.utils.enums.ValidationFailureType


/**
 * The type of failures we can find in our model
 * */
sealed class Failure {
    object NetworkConnection : Failure()
    class ServerErrorCode(val code: Int) : Failure()
    class ServerException(val throwable: Throwable) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()


    //Todo Ruben: Feature failure
    class NullResult : FeatureFailure()
    class ValidationFailure(val validationFailureType: ValidationFailureType) : FeatureFailure()
    object LoginNotSuccessful : FeatureFailure()
    object JoinNotSuccessful : FeatureFailure()

}