package com.example.miprimeraapp_moviles_2223.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.miprimeraapp_moviles_2223.databinding.ActivityMainBinding
import com.example.miprimeraapp_moviles_2223.domain.model.Person
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseAddPerson
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseDeletePerson
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseGetAllPersons
import com.example.miprimeraapp_moviles_2223.utils.StringProvider
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var listCurrentIndex: Int = 0

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider(this),
            UsecaseAddPerson(),
            UsecaseDeletePerson(),
            UsecaseGetAllPersons(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        changesStatus()
    }



    private fun changesStatus(){
        viewModel.uiState.observe(this@MainActivity) { state ->
            state.messageError?.let { messageError ->
                Toast.makeText(this@MainActivity, messageError, Toast.LENGTH_SHORT).show()
                //viewModel.errorMostrado()
                cleanText()
                Timber.tag(Constants.TAG).e(Constants.STATUS_NOT_CHANGE)

            }
            if (state.messageError == null){
                //Toast.makeText(this@MainActivity, "Person added", Toast.LENGTH_SHORT).show()
                nextPerson()
                previousPerson()
                cleanText()
                Timber.tag(Constants.TAG).i(Constants.STATUS_CHANGE)
            }
        }
    }


    private fun setOnClickListeners() {
        with(binding) {

            btnAdd.setOnClickListener {
                addPersonFinally()
            }

            btnClean.setOnClickListener {
                cleanText()
            }

            btnPrevious.setOnClickListener {
                previousPerson()
            }

            btnNext.setOnClickListener {
                nextPerson()
            }

            btnChangeView?.setOnClickListener {
                changeView()
            }
        }

    }

    private fun changeView(){
        val intent =  Intent(this@MainActivity, RecyclerViewActivity::class.java)
        //intent.putExtra(R.string.personId, viewModel.getAllPersons())
        //intent.putExtra(getString(R.string.personLArrayList), ArrayList(viewModel.getAllPersons()))
        startActivity(intent)
        Timber.tag(Constants.TAG).i(Constants.VIEW_CHANGE_TO_RV)
    }


    private fun nextPerson(){
        with(binding) {
            if (viewModel.getAllPersons().isEmpty()) {
                btnNext.isEnabled = false
                Toast.makeText(this@MainActivity, Constants.VIEWMODEL_IS_EMPTY, Toast.LENGTH_SHORT).show()
                Timber.tag(Constants.TAG).e(Constants.VIEWMODEL_IS_EMPTY)
            } else{
                listCurrentIndex++

                btnNext.isEnabled = listCurrentIndex < viewModel.getAllPersons().size
                btnPrevious.isEnabled = true

                btnAdd.isEnabled = false
                btnClean.isEnabled = false

                val person = viewModel.getAllPersons()[listCurrentIndex-1]
                loadPerson(person)
            }
        }
    }


    private fun previousPerson() {
        with(binding){
            listCurrentIndex--
            if (listCurrentIndex == 0){
                cleanText()
                btnPrevious.isEnabled = false
                btnNext.isEnabled = true

                btnAdd.isEnabled = true
                btnClean.isEnabled = true
            }else{
                btnPrevious.isEnabled = listCurrentIndex > 0
                btnNext.isEnabled = true

                btnAdd.isEnabled = false
                btnClean.isEnabled = false

                val person = viewModel.getAllPersons()[listCurrentIndex-1]
                loadPerson(person)
            }
        }

    }

    private fun loadPerson(person: Person){
        with(binding) {
            etName.editText?.setText(person.name)
            etPassword.editText?.setText(person.password)
            etPhone.editText?.setText(person.phone.toString())
            rgGender.check(person.gender)
            etEmail.editText?.setText(person.email)
        }
    }



    private fun fieldPerson(): Person{
        var p: Person
        with(binding){
            val name = etName.editText?.text.toString()
            val pass = etPassword.editText?.text.toString()
            val phone = etPhone.editText?.text.toString().toInt()
            val gender = rgGender.checkedRadioButtonId
            val email = etEmail.editText?.text.toString()
            p = Person((viewModel.getAllPersons().size + 1), name, pass, phone, gender, email)
        }
        return p
    }



    private fun addPersonFinally(){
        if (validatePerson())
            with(binding){
                val p = fieldPerson()
                viewModelAddPerson(p)
                btnNext.isEnabled = true
                Toast.makeText(this@MainActivity, Constants.PERSON_ADD, Toast.LENGTH_SHORT).show()
                Timber.tag(Constants.TAG).i(Constants.PERSON_ADD)
            }
        else{
            Toast.makeText(this@MainActivity, Constants.PERSON_NOT_ADD, Toast.LENGTH_SHORT).show()
            Timber.tag("::MYTAG").e(Constants.PERSON_NOT_ADD)
        }

    }


    private fun validatePerson(): Boolean {

        var result: Boolean = false

        if (allDataComplete()) {
            if (dataCorrect()) {
                result = true
                Timber.tag(Constants.TAG).i(Constants.PERSON_CHECK)
                /*
                if(viewModel.addPerson(p)){
                    Toast.makeText(this@MainActivity, "Person added", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "No added person. Try again", Toast.LENGTH_SHORT).show()

                }

                println(viewModel.getAllPersons())
                Toast.makeText(this@MainActivity, "Hello ${etName.editText?.text.toString()}", Toast.LENGTH_SHORT).show()
                println("ESTOY SUPER CONTRNTO CON TOODO ESTO SI QUE BIEN")
                cleanText()
                 */
            } else {
                Toast.makeText(
                    this@MainActivity,
                    Constants.INCORRECT_DATA,
                    Toast.LENGTH_LONG)
                    .show()
                Timber.tag(Constants.TAG).e(Constants.INCORRECT_DATA)
            }
        }else{
            Toast.makeText(
                this@MainActivity,
                Constants.UNFILLED_FIELDS,
                Toast.LENGTH_SHORT)
                .show()
            Timber.tag(Constants.TAG).e(Constants.UNFILLED_FIELDS)
        }

        return result
    }


    private fun viewModelAddPerson(person: Person) =
        viewModel.addPerson(person)

    private fun viewModelDeletePerson(position: Int) =
        viewModel.deletePerson(position)



    private fun cleanText() {
        with(binding) {
            etName.editText?.setText("")
            etPhone.editText?.setText("")
            etEmail.editText?.setText("")
            rgGender.clearCheck()
            etPassword.editText?.setText("")
        }
    }

    private fun allDataComplete(): Boolean {

        with(binding) {
            val name = etName.editText?.text.toString();
            val phone = etPhone.editText?.text.toString();
            val email = etEmail.editText?.text.toString();
            val idGender = rgGender.checkedRadioButtonId
            val password = etPassword.editText?.text.toString();

            val isNotEmpty: Boolean = (name.isNotEmpty() and phone.isNotEmpty()
                    and email.isNotEmpty() and (idGender != -1) and password.isNotEmpty())

            return isNotEmpty
        }
    }

    private fun dataCorrect(): Boolean {
        with(binding) {
            val patroncito: Pattern =
                Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
            val comparador: Matcher = patroncito.matcher(etEmail.editText?.text.toString())
            val dataCorrect: Boolean = (etPhone.editText?.text?.isDigitsOnly()!! and comparador.matches() and (etPhone.editText.toString().length > 8))
            return dataCorrect
        }
    }
}