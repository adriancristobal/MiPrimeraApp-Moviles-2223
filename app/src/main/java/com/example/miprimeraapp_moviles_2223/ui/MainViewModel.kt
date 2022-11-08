package com.example.miprimeraapp_moviles_2223.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miprimeraapp_moviles_2223.R
import com.example.miprimeraapp_moviles_2223.domain.model.Person
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseAddPerson
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseDeletePerson
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseGetAllPersons
import com.example.miprimeraapp_moviles_2223.utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addPersonUC: UsecaseAddPerson,
    private val deletePersonUC: UsecaseDeletePerson,
    private val getAllPersonsUC: UsecaseGetAllPersons,
    ) : ViewModel() {

    //el ViewModel hace que se mantengan los datos si movemos la pantalla, etc
        private val _uiState = MutableLiveData<MainState>()
        val uiState: LiveData<MainState> get() = _uiState

        fun addPerson(personAdded: Person): Unit {
                if (!addPersonUC.addPerson(personAdded)) {
                    _uiState.value = MainState(
                        messageError = stringProvider.getString(R.string.errorPersonAdded),
                    )
                } else {
                    _uiState.value = MainState(
                        person = personAdded,
                        //messageError = Constants.CONFIRM,
                    )
                }
        }


        fun getAllPersons() : List<Person> {
            val personList = getAllPersonsUC.getAllPersons()
            return personList
        }


        fun deletePerson(position : Int){
            if (!deletePersonUC.deletePerson(position)) {
                _uiState.value = MainState(
                        messageError = stringProvider.getString(R.string.errorPersonDelete),
                )
            }
        }

        fun errorMostrado() {
            _uiState.value = MainState(
                messageError = null
                //messageError = Constants.CONFIRM,
            )
        }

    }






class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPersonUC: UsecaseAddPerson,
    private val deletePersonUC: UsecaseDeletePerson,
    private val getAllPersonsUC: UsecaseGetAllPersons,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addPersonUC,
                deletePersonUC,
                getAllPersonsUC,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}