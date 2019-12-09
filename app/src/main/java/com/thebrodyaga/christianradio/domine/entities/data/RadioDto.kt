package com.thebrodyaga.christianradio.domine.entities.data

data class RadioDto constructor(
    val radioName: String,
    val radioUrl: String,
    val radioImage: String,
    val smallRadioImage: String,
    val isFavorite: Boolean = false
)