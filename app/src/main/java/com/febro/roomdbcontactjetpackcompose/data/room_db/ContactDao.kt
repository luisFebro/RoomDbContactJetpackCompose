package com.febro.roomdbcontactjetpackcompose.data.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao // n1
interface ContactDao {
    @Upsert // either create if it is new or update it
    suspend fun upsertContact(contact: ContactTable)

    @Delete
    suspend fun deleteContact(contact: ContactTable)

    // flow makes sure to update list real time as soon as there is a change
    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactsOrderedByFirstName(): Flow<List<ContactTable>>

    @Query("SELECT * FROM contact ORDER BY lastName ASC")
    fun getContactsOrderedByLastName(): Flow<List<ContactTable>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun getContactsOrderedByPhoneNumber(): Flow<List<ContactTable>>
}


/* NOTES
N1:
Marks the class as a Data Access Object.
Data Access Objects are the main classes where you define your database interactions. They can include a variety of query methods.
The class marked with @Dao should either be an interface or an abstract class. At compile time, Room will generate an implementation of this class when it is referenced by a Database.
An abstract @Dao class can optionally have a constructor that takes a Database as its only parameter.
It is recommended to have multiple Dao classes in your codebase depending on the tables they touch

 */