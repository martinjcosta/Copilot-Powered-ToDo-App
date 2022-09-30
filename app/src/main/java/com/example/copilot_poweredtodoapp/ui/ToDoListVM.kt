package com.example.copilot_poweredtodoapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.copilot_poweredtodoapp.data.ToDoItem
import com.example.copilot_poweredtodoapp.data.ToDoListRepository

class ToDoListVM(
    private val toDoListRepository: ToDoListRepository
) : ViewModel() {

    val addNewToDoItemState = mutableStateOf(AddNewToDoItemState())

    val toDoList = toDoListRepository.getToDoList()

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.addToDoItem(toDoItem)
    }

    fun updateToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.updateToDoItem(toDoItem)
    }

    fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.deleteToDoItem(toDoItem)
    }
}