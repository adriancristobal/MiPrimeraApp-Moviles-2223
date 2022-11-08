package com.example.miprimeraapp_moviles_2223.ui

import com.example.miprimeraapp_moviles_2223.domain.model.Person

data class RecyclerState (
    var list: List<Person> = ArrayList(),
    var messageError: String? = null,
    )
