package com.example.paimasapp.data

data class Card(val display: String, val options: ArrayList<String>, val answer: String)

// fun parse_cards(): List<Card>? {
    // An hour has been spent here, getting nowhere.

    // val inputStream: InputStream = Resources.getSystem().openRawResource(R.raw.testcards) // Fuck knows how to get the path

    // val document = TomlValue.from(inputStream)

    // return document.get<List<Card>>("Card")
// }
