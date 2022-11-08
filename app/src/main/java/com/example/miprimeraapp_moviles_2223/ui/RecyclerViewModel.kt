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

class RecyclerViewModel (
    private val stringProvider: StringProvider,
    private val addPersonUC: UsecaseAddPerson,
    private val deletePersonUC: UsecaseDeletePerson,
    private val getAllPersonsUC: UsecaseGetAllPersons,
    ) : ViewModel() {
        private val _uiState = MutableLiveData<RecyclerState>()
        val uiState: LiveData<RecyclerState> get() = _uiState

        fun getAllPersons(){
            val personList = getAllPersonsUC.getAllPersons()
            _uiState.value = RecyclerState(
                list = personList
            )
        }

        fun deletePerson(position : Int){
            if (!deletePersonUC.deletePerson(position)) {
                _uiState.value = RecyclerState(
                    messageError = stringProvider.getString(R.string.errorPersonDelete),
                )
            }
        }
    }


class RecyclerViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPersonUC: UsecaseAddPerson,
    private val deletePersonUC: UsecaseDeletePerson,
    private val getAllPersonsUC: UsecaseGetAllPersons,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecyclerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecyclerViewModel(
                stringProvider,
                addPersonUC,
                deletePersonUC,
                getAllPersonsUC,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}