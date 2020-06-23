package com.qflow.main.utils.enums

enum class ValidationFailureType {

    PASSWORDS_NOT_THE_SAME,
    EMAIL_OR_PASSWORD_EMPTY,
    CAPACITY_TOO_SMALL,
    QUEUE_LOCK,
    QUEUE_CLOSE,
    FULL_CAPACITY,
    QUEUE_STOP,
    QUEUE_ADVANCE_CLOSE,
    QUEUE_CLOSE_CLOSED,
    QUEUE_RESUME

}