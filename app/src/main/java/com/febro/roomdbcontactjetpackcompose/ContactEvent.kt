package com.febro.roomdbcontactjetpackcompose

import com.febro.roomdbcontactjetpackcompose.data.room_db.ContactTable
import com.febro.roomdbcontactjetpackcompose.data.room_db.SortType

// tracks every event (user interaction like tapping) from contact screen
/*
In summary, sealed interfaces provide a way to create a limited hierarchy of classes or interfaces, ensuring that all possible subclasses are known and handled appropriately.
 */

/* diff object vs data class
the main difference is that object is used for singleton instances without additional data, while data class is used when you need to hold and represent data with automatically generated methods for common operations.

 */

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