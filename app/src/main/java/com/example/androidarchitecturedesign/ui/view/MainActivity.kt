package com.example.androidarchitecturedesign.ui.view

import android.R.id.message
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable



import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
fun Greeting(name: String, modifier: Modifier = Modifier,viewModel: NameViewModel) {



    Box(modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
Column {

    val uiState by viewModel.state.collectAsState()
            Button(onClick = {

                viewModel.fetchUsers()

            },
                modifier = Modifier.wrapContentSize()

            ) {

                Text(
                    text = "Hello $name!",

                    )
            }

            when (uiState) {
                is UiState.Idle->{}
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Error -> {
                    val message = (uiState as UiState.Error).error
                    Text("Error: $message")
                }

                is UiState.Success<*> -> {
                    val users = (uiState as UiState.Success).data
                    LazyColumn { items(users) { user -> Text(user.name) } }


                }


            }

        }}


}
