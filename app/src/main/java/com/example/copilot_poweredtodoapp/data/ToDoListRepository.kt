package com.example.copilot_poweredtodoapp.data

interface ToDoListRepository {
    fun getToDoList(): List<ToDoItem>
}

class ToDoListRepositoryImpl : ToDoListRepository {
    private val toDoList = ToDoFakeData.toMutableList()

    override fun getToDoList(): List<ToDoItem> {
        return toDoList
    }
}