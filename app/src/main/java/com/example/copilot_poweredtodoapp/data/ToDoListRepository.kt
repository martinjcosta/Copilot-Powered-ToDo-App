package com.example.copilot_poweredtodoapp.data

interface ToDoListRepository {
    fun getToDoList(): List<ToDoItem>
    fun updateToDoItem(toDoItem: ToDoItem)
    fun deleteToDoItem(toDoItem: ToDoItem)
}

class ToDoListRepositoryImpl : ToDoListRepository {
    private val toDoList = ToDoFakeData.toMutableList()

    override fun getToDoList(): List<ToDoItem> {
        return toDoList
    }

    override fun updateToDoItem(toDoItem: ToDoItem) {
        val index = toDoList.indexOfFirst { it.title == toDoItem.title }
        toDoList[index] = toDoItem
    }

    override fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoList.remove(toDoItem)
    }
}