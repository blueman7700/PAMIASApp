package com.example.paimasapp.structs

import android.content.res.AssetManager
import android.content.res.Resources
import cc.ekblad.toml.TomlValue
import cc.ekblad.toml.serialization.from
import cc.ekblad.toml.transcoding.get
import java.io.InputStream

data class Card(val display: String, val options: ArrayList<String>, val answer: String)

// fun parse_cards(): List<Card>? {
    // An hour has been spent here, getting nowhere.

    // val inputStream: InputStream = Resources.getSystem().openRawResource(R.raw.testcards) // Fuck knows how to get the path

    // val document = TomlValue.from(inputStream)

    // return document.get<List<Card>>("Card")
// }
