package com.example.copilot_poweredtodoapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.copilot_poweredtodoapp.data.ToDoItem
import com.example.copilot_poweredtodoapp.data.ToDoListRepository

class ToDoListVM(
    private val toDoListRepository: ToDoListRepository
) : ViewModel() {

    val addNewToDoItemState = mutableStateOf(AddNewToDoItemState())

    val toDoList = mutableStateOf(toDoListRepository.getToDoList())

    fun addToDoItem(toDoItem: ToDoItem) {
        addNewToDoItemState.value = addNewToDoItemState.value.titleChanged(toDoItem.title)
        addNewToDoItemState.value = addNewToDoItemState.value.descriptionChanged(toDoItem.description)
        addNewToDoItemState.value = addNewToDoItemState.value.validateForm()

        if (addNewToDoItemState.value.isFormValid) {
            toDoList.value = toDoList.value + toDoItem
            addNewToDoItemState.value = addNewToDoItemState.value.copy(show = false)
        }
    }

    /*fun updateToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.updateToDoItem(toDoItem)
    }

    fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.deleteToDoItem(toDoItem)
    }*/

    fun cancelAddNewToDoItem() {
        addNewToDoItemState.value = addNewToDoItemState.value.copy(show = false)
    }
}