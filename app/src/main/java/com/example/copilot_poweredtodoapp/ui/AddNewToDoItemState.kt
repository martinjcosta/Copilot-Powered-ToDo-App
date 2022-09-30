package com.example.copilot_poweredtodoapp.ui

data class AddNewToDoItemState(
    var show: Boolean = false,
    val title: String = "",
    val description: String = "",
    val isTitleValid: Boolean = false,
    val isDescriptionValid: Boolean = false,
    val isFormValid: Boolean = false
) {
    fun titleChanged(title: String) = copy(title = title, isTitleValid = title.isNotBlank())
    fun descriptionChanged(description: String) =
        copy(description = description, isDescriptionValid = description.isNotBlank())
    fun validateForm() = copy(isFormValid = isTitleValid && isDescriptionValid)
}