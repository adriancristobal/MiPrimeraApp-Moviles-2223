package com.example.miprimeraapp_moviles_2223.data

import com.example.miprimeraapp_moviles_2223.domain.model.Person

object Repository {
    private val persons = mutableListOf<Person>()

    fun addPerson(person: Person) =
        persons.add(person)


    fun getPersons(): List<Person> {
        return persons
    }

    fun deletePerson(position: Int) =
        persons.remove(persons[position])


}