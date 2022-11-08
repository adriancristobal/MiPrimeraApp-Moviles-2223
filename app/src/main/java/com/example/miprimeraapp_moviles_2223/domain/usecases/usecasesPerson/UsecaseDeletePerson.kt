package com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson

import com.example.miprimeraapp_moviles_2223.data.Repository
import com.example.miprimeraapp_moviles_2223.domain.model.Person

class UsecaseDeletePerson {

    fun deletePerson(position: Int) =
        Repository.deletePerson(position)
}