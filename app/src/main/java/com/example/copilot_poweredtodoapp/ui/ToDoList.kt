package com.example.copilot_poweredtodoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.copilot_poweredtodoapp.R
import com.example.copilot_poweredtodoapp.data.ToDoFakeData
import com.example.copilot_poweredtodoapp.data.ToDoItem
import org.burnoutcrew.reorderable.*


// My To-do Item Compose UI with a checkbox, title, and description
@Composable
fun ToDoItem(
    toDoItem: ToDoItem,
    onCheckedChange: (Boolean) -> Unit,
    state: ReorderableLazyListState
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
            Spacer(modifier = Modifier.weight(1f))


            // Handle that allows the user to drag and drop the item
            // At the end of the ToDoItem
            Image(
                modifier = Modifier
                    .detectReorder(state)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_drag_handle),
                contentDescription = "Drag Handle"
            )
        }
    }
}

// To-Do List that takes a list of ToDoItem and places them in a LazyColumn
@Composable
fun ToDoList(
    toDoList: List<ToDoItem>,
    onCheckedChange: (ToDoItem, Boolean) -> Unit,
    onMove: (from: Int, to: Int) -> Unit
) {
    val state = rememberReorderableLazyListState(onMove = { from, to -> onMove(from.index, to.index) } )

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
    ) {
        items(items = toDoList, key = { it.title }, itemContent = { toDoItem ->
            ReorderableItem(reorderableState = state, key = toDoItem.title) {
                ToDoItem(
                    toDoItem = toDoItem,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(toDoItem, isChecked)
                    },
                    state = state
                )
            }
        })
    }
}

@Preview
@Composable
fun ToDoListPreview() {
    ToDoList(
        toDoList = ToDoFakeData,
        onCheckedChange = { _, _ -> },
        onMove = { _, _ -> }
    )
}

@Preview
@Composable
fun ToDoItemPreview() {
    ToDoItem(
        toDoItem = ToDoFakeData[0],
        onCheckedChange = { },
        state = rememberReorderableLazyListState(onMove = { _, _ -> })
    )
}