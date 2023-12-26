package com.febro.roomdbcontactjetpackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febro.roomdbcontactjetpackcompose.data.room_db.ContactDao
import com.febro.roomdbcontactjetpackcompose.data.room_db.ContactTable
import com.febro.roomdbcontactjetpackcompose.data.room_db.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactViewModel(
    private val dao: ContactDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _contacts = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.FIRST_NAME -> dao.getContactsOrderedByFirstName()
            SortType.LAST_NAME -> dao.getContactsOrderedByLastName()
            SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactState())

    // combine 3 flows into single one. ref: https://youtu.be/bOd3wO0uFr8?t=1623s
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    // ref: https://youtu.be/bOd3wO0uFr8?t=1223s
    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                _state.update {
                    it.copy( //  Use the .copy() function to copy an object, allowing you to alter some of its properties while keeping the rest unchanged.
                        isAddingContact = false
                    )
                }
            }
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                    return
                }

                val contact = ContactTable(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )

                viewModelScope.launch {
                    dao.upsertContact(contact)
                }

                _state.update {
                    it.copy(
                        isAddingContact = false,
                        firstName = "",
                        lastName = "",
                        phoneNumber = ""
                    )
                }
            }
            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }
            }
            is ContactEvent.SetLastName -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }
        }
    }
}