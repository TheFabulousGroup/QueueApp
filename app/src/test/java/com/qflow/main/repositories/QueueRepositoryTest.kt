package com.qflow.main.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.repository.QueueRepository
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class QueueRepositoryTest {

    @Mock
    private var mockAppDatabase: AppDatabase = mock(AppDatabase::class.java)
/*
    val queueRepository = QueueRepository.General(
        mockAppDatabase,
        QueueAdapter,
        FirebaseFirestore.getInstance(),
        FirebaseAuth.getInstance(),
        FirebaseFunctions.getInstance()
    )*/

//    @Test
//    fun createQueue(){
//
//        queueRepository.createQueue("Prueba1", "Soy una prueba", "1", "")
//
//    }
}