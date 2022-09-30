package com.example.copilot_poweredtodoapp.ui

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// Floating Action Button to add a new To-Do Item with a "+" icon and a click listener
@Composable
fun AddToDoFAB(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, "Add") },
        text = { Text(text = "Add Item") },
    )
}

@Preview
@Composable
fun AddToDoFABPreview() {
    AddToDoFAB(onClick = {})
}