package com.febro.roomdbcontactjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.febro.roomdbcontactjetpackcompose.data.room_db.ContactDB
import com.febro.roomdbcontactjetpackcompose.ui.theme.RoomDbContactJetpackComposeTheme
import com.febro.roomdbcontactjetpackcompose.ui.theme.screens.contact.ContactScreen


// ref: https://www.youtube.com/watch?v=bOd3wO0uFr8
class MainActivity : ComponentActivity() {

    /*
    In this code, lazy is used as a delegate to achieve lazy initialization. Lazy initialization means that the value assigned to db will only be computed once it's accessed for the first time, and after that, it will be reused.
    So, the Room.databaseBuilder and build() operations won't be executed until db is actually used somewhere in your code. It's a way to defer the cost of creating the database until it's really needed, which can be more efficient in certain situations.
     */
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDB::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ContactViewModel(db.dao) as T
                }

            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDbContactJetpackComposeTheme {
                val state by viewModel.state.collectAsState()

                ContactScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomDbContactJetpackComposeTheme {
        Greeting("Android")
    }
}