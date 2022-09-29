package com.example.copilot_poweredtodoapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.copilot_poweredtodoapp.data.ToDoFakeData
import com.example.copilot_poweredtodoapp.data.ToDoItem


// My To-do Item Compose UI with a checkbox, title, and description
@Composable
fun ToDoItem(
    toDoItem: ToDoItem,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Checkbox(
                checked = toDoItem.isDone,
                onCheckedChange = onCheckedChange
            )
            Column(
                modifier = Modifier.align(
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    text = toDoItem.title,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = toDoItem.description,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

// To-Do List that takes a list of ToDoItem and places them in a LazyColumn
@Composable
fun ToDoList(
    toDoList: List<ToDoItem>,
    onCheckedChange: (Int, Boolean) -> Unit
) {
    LazyColumn {
        items(items = toDoList, itemContent = { toDoItem ->
            ToDoItem(
                toDoItem = toDoItem,
                onCheckedChange = { isChecked ->
                    onCheckedChange(toDoList.indexOf(toDoItem), isChecked)
                }
            )
        })
    }
}


@Preview
@Composable
fun ToDoListPreview() {
    ToDoList(
        toDoList = ToDoFakeData,
        onCheckedChange = { _, _ -> }
    )
}

@Preview
@Composable
fun ToDoItemPreview() {
    ToDoItem(
        toDoItem = ToDoFakeData[0],
        onCheckedChange = { }
    )
}