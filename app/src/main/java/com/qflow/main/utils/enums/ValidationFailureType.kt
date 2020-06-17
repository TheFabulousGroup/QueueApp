package com.qflow.main.utils.enums

enum class ValidationFailureType {

    PASSWORDS_NOT_THE_SAME,
    EMAIL_OR_PASSWORD_EMPTY,
    CAPACITY_TOO_SMALL,
    QUEUE_LOCK,
    FULL_CAPACITY, //si esta llena no puedes avanzar
    QUEUE_STOP, // si esta parada no puedes avanzar
    QUEUE_CLOSE,//si esta cerrada no puedes avanzar
    QUEUE_RESUME// SI LA COLA ESTA EN PAUSA NO SE PUEDE AVANZAR
}