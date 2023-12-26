package com.febro.roomdbcontactjetpackcompose

import com.febro.roomdbcontactjetpackcompose.data.room_db.ContactTable
import com.febro.roomdbcontactjetpackcompose.data.room_db.SortType

data class ContactState(
    val contacts: List<ContactTable> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
