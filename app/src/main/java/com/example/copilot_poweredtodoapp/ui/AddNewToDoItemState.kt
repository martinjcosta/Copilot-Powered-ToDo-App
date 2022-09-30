package com.example.copilot_poweredtodoapp.ui

import android.util.Log
import com.example.copilot_poweredtodoapp.data.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.*

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
    suspend fun validateForm() = copy(isFormValid = isTitleValid && isDescriptionValid && doesNotContainTypos())
}

suspend fun AddNewToDoItemState.doesNotContainTypos(): Boolean {
    val title = title.lowercase(Locale.ROOT)
    val description = description.lowercase(Locale.ROOT)

    val result = title.containsOnlyEnglishWords() && description.containsOnlyEnglishWords()

    // Log result
    Log.d("isEnglishWord", "doesNotContainTypos: $result")
    return result
}

// Returns false if the string contains words not found in a standard English dictionary
// Uses a web service to check if the words are valid
suspend fun String.containsOnlyEnglishWords(): Boolean {
    val words = this.split(" ")
    val invalidWords = words.filter { !it.isEnglishWord() }
    return invalidWords.isEmpty()
}

// Given a String representing a single word, returns true if the word is found in a standard English dictionary
// Uses a web service to check if the word is valid
suspend fun String.isEnglishWord(): Boolean {
    val url = "https://api.dictionaryapi.dev/api/v2/entries/en/$this"
    val response: HttpResponse = client.request(url) {
    }

    // Log details about the response using Android logging
    Log.d("isEnglishWord", "Response status: ${response.status}")

    return response.status.value == 200
}

// Parse the response from the web service
// Example response: [{"word":"legion","phonetic":"/ˈliːdʒən/","phonetics":[{"text":"/ˈliːdʒən/","audio":""}],"meanings":[{"partOfSpeech":"noun","definitions":[{"definition":"The major unit or division of the Roman army, usually comprising 3000 to 6000 infantry soldiers and 100 to 200 cavalry troops.","synonyms":[],"antonyms":[]},{"definition":"A combined arms major military unit featuring cavalry, infantry, and artillery","synonyms":[],"antonyms":[]},{"definition":"A large military or semi-military unit trained for combat; any military force; an army, regiment; an armed, organized and assembled militia.","synonyms":[],"antonyms":[]},{"definition":"(often Legion or the Legion) A national organization or association of former servicemen, such as the American Legion.","synonyms":[],"antonyms":[]},{"definition":"A large number of people; a multitude.","synonyms":[],"antonyms":[]},{"definition":"(often plural) A great number.","synonyms":[],"antonyms":[]},{"definition":"A group of orders inferior to a class; in scientific classification, a term occasionally used to express an assemblage of objects intermediate between an order and a class.","synonyms":[],"antonyms":[]}],"synonyms":["host","mass","multitude","sea","throng"],"antonyms":[]},{"partOfSpeech":"verb","definitions":[{"definition":"To form into legions.","synonyms":[],"antonyms":[]}],"synonyms":[],"antonyms":[]},{"partOfSpeech":"adjective","definitions":[{"definition":"Numerous; vast; very great in number","synonyms":["multitudinous","numerous"],"antonyms":[],"example":"Russia’s labor and capital resources are woefully inadequate to overcome the state’s needs and vulnerabilities, which are legion."}],"synonyms":["multitudinous","numerous"],"antonyms":[]}],"license":{"name":"CC BY-SA 3.0","url":"https://creativecommons.org/licenses/by-sa/3.0"},"sourceUrls":["https://en.wiktionary.org/wiki/legion"]}]
// Return true if the field "title" is not null and the value of the field "title" is "No Definitions Found"
suspend fun HttpResponse.isEnglishWord(): Boolean {
    val response = this.receive<String>()
    val title = response.substringAfter("\"title\":").substringBefore(",")
    return title != "\"No Definitions Found\""
}