package com.example.miprimeraapp_moviles_2223.ui

import com.example.miprimeraapp_moviles_2223.domain.model.Person

 data class MainState(
  val person: Person = Person(-1,"null","null",-1, -1,"null"),
  val messageError: String? = null)
