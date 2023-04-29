package com.github.raziu75.tricount.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.raziu75.tricount.data.local.entity.TransactionEntity
import com.github.raziu75.tricount.data.local.entity.relation.TransactionParticipantCrossRef
import com.github.raziu75.tricount.data.local.entity.relation.TransactionWithParticipants

@Dao
interface TricountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: TransactionEntity): Long

    @Transaction
    @Query("SELECT * FROM `transaction` WHERE transaction_id = :transactionId")
    suspend fun getTransactionWithParticipants(transactionId: Long): TransactionWithParticipants?

    @Query("UPDATE `transaction` SET payer_id = :payerId WHERE transaction_id = :transactionId")
    suspend fun updateTransactionPayer(transactionId: Long, payerId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createParticipant(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransactionParticipantsCrossRef(refs: List<TransactionParticipantCrossRef>)
}