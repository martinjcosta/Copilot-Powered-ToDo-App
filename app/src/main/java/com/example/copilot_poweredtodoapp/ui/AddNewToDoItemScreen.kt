package com.example.copilot_poweredtodoapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Full Screen that allows users to create a new ToDoItem
// There is a text field for the title and description
// We assume the ToDoItem is not done by default so there is no checkbox for that
// There is a save button that will save the ToDoItem and navigate back to the ToDoList
@Composable
fun AddNewToDoItemScreen(
    modifier: Modifier = Modifier,
    onSave: (title: String, description: String) -> Unit,
    onCancel: () -> Unit
) {
    val titleState = remember {
        mutableStateOf(TextFieldValue())
    }

    val descriptionState = remember {
        mutableStateOf(TextFieldValue())
    }

    Card(
        modifier = modifier
            .width(600.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add New To-Do Item",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(vertical = 16.dp)
            )
            TextField(
                modifier = Modifier
                    .height(80.dp)
                    .padding(8.dp),
                value = titleState.value,
                onValueChange = { titleState.value = it },
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text("Title") }
            )

            TextField(
                modifier = Modifier
                    .height(200.dp)
                    .padding(8.dp)
                    .padding(bottom = 16.dp),
                value = descriptionState.value,
                shape = RoundedCornerShape(8.dp),
                onValueChange = { descriptionState.value = it },
                placeholder = { Text("Description") }
            )

            Row {
                // Green Save button
                Button(
                    modifier =
                    Modifier
                        .width(100.dp)
                        .padding(8.dp)
                        .padding(bottom = 16.dp),
                    onClick = { onSave(titleState.value.text, descriptionState.value.text) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF00C853)
                    )
                ) {
                    Text(text = "Save")

                }

                // Green Cancel Button
                Button(
                    modifier =
                    Modifier
                        .width(100.dp)
                        .padding(8.dp)
                        .padding(bottom = 16.dp),
                    onClick = { onCancel() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF00C853)
                    )
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}


@Preview
@Composable
fun AddNewToDoItemScreenPreview() {
    AddNewToDoItemScreen(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        onSave = { _, _ -> },
        onCancel = { }
    )
}
