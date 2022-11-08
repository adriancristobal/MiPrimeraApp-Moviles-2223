package com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson

import androidx.core.text.isDigitsOnly
import com.example.miprimeraapp_moviles_2223.data.Repository
import com.example.miprimeraapp_moviles_2223.domain.model.Person
import java.util.regex.Matcher
import java.util.regex.Pattern

class UsecaseAddPerson {

    fun addPerson(person: Person) =
        Repository.addPerson(person)

}