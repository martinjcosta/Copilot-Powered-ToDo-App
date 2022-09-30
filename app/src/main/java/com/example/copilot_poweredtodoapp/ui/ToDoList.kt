package com.example.copilot_poweredtodoapp.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.copilot_poweredtodoapp.R
import com.example.copilot_poweredtodoapp.data.ToDoFakeData
import com.example.copilot_poweredtodoapp.data.ToDoItem
import org.burnoutcrew.reorderable.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DismissableToDoItem(
    toDoItem: ToDoItem,
    onCheckedChange: (Boolean) -> Unit,
    state: ReorderableLazyListState,
    onDismiss: (ToDoItem) -> Unit,
    onEdit: (ToDoItem) -> Unit
) {
    val dismissState = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss(toDoItem)
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            // Animate visibility of the background based on the horizontal position of the item as it is dismissed
            val alpha by animateFloatAsState(
                targetValue = if (dismissState.dismissDirection == null) 0f else {
                    dismissState.progress.fraction / 3.0f
                }
            )

            DismissBackground(
                alpha = alpha,
            )
        },
        dismissContent = {
            ToDoItem(
                toDoItem = toDoItem,
                onCheckedChange = onCheckedChange,
                state = state,
                onEdit = onEdit
            )
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}

// My To-do Item Compose UI with a checkbox, title, and description
@Composable
fun ToDoItem(
    toDoItem: ToDoItem,
    onCheckedChange: (Boolean) -> Unit,
    state: ReorderableLazyListState,
    onEdit: (ToDoItem) -> Unit
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

            // Pencil icon that allows the user to edit the ToDoItem
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit",
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onEdit(toDoItem) }
            )

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
    onMove: (from: Int, to: Int) -> Unit,
    onDismiss: (ToDoItem) -> Unit,
    onEdit: (ToDoItem) -> Unit
) {
    val state = rememberReorderableLazyListState(onMove = { from, to -> onMove(from.index, to.index) } )

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
    ) {
        items(items = toDoList, key = { it.title }, itemContent = { toDoItem ->
            ReorderableItem(reorderableState = state, key = toDoItem.title) {
                DismissableToDoItem(
                    toDoItem = toDoItem,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(toDoItem, isChecked)
                    },
                    state = state,
                    onDismiss = onDismiss,
                    onEdit = onEdit
                )
            }
        })
    }
}

@Composable
fun DismissBackground(
    alpha: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(12.dp)
            .background(
                // Light grey color
                color = MaterialTheme.colors.onSurface.copy(alpha = alpha),
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Delete",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete"
            )
        }
    }
}

@Preview
@Composable
fun ToDoListPreview() {
    ToDoList(
        toDoList = ToDoFakeData,
        onCheckedChange = { _, _ -> },
        onMove = { _, _ -> },
        onDismiss = { },
        onEdit = { }
    )
}

@Preview
@Composable
fun ToDoItemPreview() {
    ToDoItem(
        toDoItem = ToDoFakeData[0],
        onCheckedChange = { },
        state = rememberReorderableLazyListState(onMove = { _, _ -> }),
        onEdit = { }
    )
}

@Preview
@Composable
fun DismissableToDoItemPreview() {
    DismissableToDoItem(
        toDoItem = ToDoFakeData[0],
        onCheckedChange = { },
        state = rememberReorderableLazyListState(
            onMove = { _, _ -> }
        ),
        onDismiss = { },
        onEdit = { }
    )
}

@Preview
@Composable
fun DismissBackgroundPreview() {
    DismissBackground(
        alpha = 0.1f
    )
}