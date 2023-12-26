package com.febro.roomdbcontactjetpackcompose.data.room_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactTable::class],
    version = 1,
)
abstract class ContactDB: RoomDatabase() {
    abstract val dao: ContactDao
}