package com.example.copilot_poweredtodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.copilot_poweredtodoapp.data.ToDoItem
import com.example.copilot_poweredtodoapp.data.ToDoListRepositoryImpl
import com.example.copilot_poweredtodoapp.ui.AddNewToDoItemScreen
import com.example.copilot_poweredtodoapp.ui.AddToDoFAB
import com.example.copilot_poweredtodoapp.ui.ToDoList
import com.example.copilot_poweredtodoapp.ui.ToDoListVM
import com.example.copilot_poweredtodoapp.ui.theme.CopilotPoweredToDoAppTheme

class MainActivity : ComponentActivity() {

    private val toDoListRepository = ToDoListRepositoryImpl()

    private val viewModel = ToDoListVM(
        toDoListRepository
    )

    private val toDoList
        get() = viewModel.toDoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CopilotPoweredToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (!viewModel.addNewToDoItemState.value.show) {
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
                                },
                                onMove = { from, to ->
                                    toDoList.value = toDoList.value.move(from, to)
                                },
                                onDismiss = { toDoItem ->
                                    toDoList.value = toDoList.value.filter { it != toDoItem }
                                },
                                onEdit = { toDoItem ->
                                    viewModel.addNewToDoItemState.value = viewModel.addNewToDoItemState.value.copy(
                                        show = true,
                                        toDoItem = toDoItem
                                    )
                                }
                            )

                            // Floating Action Button in bottom right corner of screen with padding
                            AddToDoFAB(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp),
                                onClick = {
                                    viewModel.addNewToDoItemState.value =
                                        viewModel.addNewToDoItemState.value.copy(
                                            show = true,
                                            toDoItem = null
                                        )
                                }
                            )
                        } else {
                            AddNewToDoItemScreen(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp),
                                onSave = { title, description ->

                                    viewModel.addToDoItem(
                                        ToDoItem(
                                            title = title,
                                            description = description,
                                            isDone = false
                                        )
                                    )
                                },
                                onCancel = {
                                    viewModel.cancelAddNewToDoItem()
                                },
                                toDoItem = viewModel.addNewToDoItemState.value.toDoItem
                            )

                            AnimatedVisibility(
                                visible = viewModel.addNewToDoItemState.value.showToast,
                                enter = androidx.compose.animation.fadeIn(),
                                exit = androidx.compose.animation.fadeOut(),
                                modifier = Modifier.align(Alignment.BottomCenter)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                        .background(Color.White)
                                        .height(100.dp),
                                    text = "Title and description must be non-empty and only use English words",
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Use AnimatedVisibility to show/hide a loading indicator
                            AnimatedVisibility(
                                visible = viewModel.addNewToDoItemState.value.loading,
                                enter = androidx.compose.animation.fadeIn(),
                                exit = androidx.compose.animation.fadeOut(),
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
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