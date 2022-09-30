package com.example.copilot_poweredtodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.copilot_poweredtodoapp.data.ToDoFakeData
import com.example.copilot_poweredtodoapp.ui.ToDoList
import com.example.copilot_poweredtodoapp.ui.theme.CopilotPoweredToDoAppTheme

class MainActivity : ComponentActivity() {

    private val toDoList = mutableStateOf(ToDoFakeData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CopilotPoweredToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ToDoList(
                        toDoList = toDoList.value,
                        onCheckedChange = { toDoItem, isChecked ->
                            toDoList.value = toDoList.value.map {
                                if (it == toDoItem) {
                                    it.copy(isDone = isChecked)
                                } else {
                                    it
                                }
                            }

                            println("ToDoItem: $toDoItem, isChecked: $isChecked")
                        }
                    )
                }
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
    CopilotPoweredToDoAppTheme {
        Greeting("Android")
    }
}