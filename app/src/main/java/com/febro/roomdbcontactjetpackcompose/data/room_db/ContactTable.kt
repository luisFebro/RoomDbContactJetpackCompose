package com.febro.roomdbcontactjetpackcompose.data.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Marks a class as an entity. This class will have a mapping SQLite table in the database.
Each entity must have at least 1 field annotated with PrimaryKey. You can also use primaryKeys attribute to define the primary key.
 */
@Entity(
    tableName = "contact"
)
data class ContactTable(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
