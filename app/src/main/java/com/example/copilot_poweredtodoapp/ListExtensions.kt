package com.example.copilot_poweredtodoapp

fun <T> List<T>.move(from: Int, to: Int): List<T> {
    val item = this[from]
    return this.toMutableList().apply {
        removeAt(from)
        add(to, item)
    }
}