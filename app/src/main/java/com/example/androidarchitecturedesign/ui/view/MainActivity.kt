package com.example.androidarchitecturedesign.ui.view

import android.R.id.message
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.androidarchitecturedesign.ui.theme.AndroidArchitectureDesignTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NameViewModel by viewModels()


        enableEdgeToEdge()
        setContent {
            AndroidArchitectureDesignTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: NameViewModel) {

    val query by viewModel.searchQuery.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            val uiState by viewModel.state.collectAsState()
            var searchQuery by remember { mutableStateOf(" ") }

            TextField(value = query, onValueChange = {  viewModel.searchQueryChanged(it)

        })

            Spacer(modifier= Modifier.padding(16.dp))

            Text("Edit test textfied is ${query}")

            Spacer(modifier= Modifier.padding(16.dp))
            Button(
                onClick = {

                    viewModel.fetchUsers()
                          },


            ) {
                Text(
                    text = "Hello $name!",

                    )
            }

            Spacer(modifier= Modifier.padding(16.dp))

            when (uiState) {
                is UiState.Idle -> {}
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Error -> {
                    val message = (uiState as UiState.Error).error
                    Text("Error: $message")
                }

                is UiState.Success<*> -> {
                    val users = (uiState as UiState.Success).data
                    LazyColumn(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) { items(users) { user -> Text(user.name) } }


                }


            }

        }
    }


}
