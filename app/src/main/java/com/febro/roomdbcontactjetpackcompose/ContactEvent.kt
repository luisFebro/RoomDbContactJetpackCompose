package com.febro.roomdbcontactjetpackcompose

import com.febro.roomdbcontactjetpackcompose.data.room_db.ContactTable
import com.febro.roomdbcontactjetpackcompose.data.room_db.SortType

// tracks every event (user interaction like tapping) from contact screen
sealed interface ContactEvent {
    object SaveContact: ContactEvent
    data class SetFirstName(val firstName: String): ContactEvent
    data class SetLastName(val lastName: String): ContactEvent
    data class SetPhoneNumber(val phoneNumber: String): ContactEvent

    object ShowDialog: ContactEvent
    object HideDialog: ContactEvent
    data class SortContacts(val sortType: SortType): ContactEvent
    data class DeleteContact(val contact: ContactTable): ContactEvent
}