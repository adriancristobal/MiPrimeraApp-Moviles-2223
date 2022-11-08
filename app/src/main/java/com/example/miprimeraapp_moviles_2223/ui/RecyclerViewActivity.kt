package com.example.miprimeraapp_moviles_2223.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.miprimeraapp_moviles_2223.databinding.ActivityRecyclerViewBinding
import com.example.miprimeraapp_moviles_2223.domain.model.Person
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseAddPerson
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseDeletePerson
import com.example.miprimeraapp_moviles_2223.domain.usecases.usecasesPerson.UsecaseGetAllPersons
import com.example.miprimeraapp_moviles_2223.utils.StringProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding


    private val viewModel: RecyclerViewModel by viewModels {
        RecyclerViewModelFactory(
            StringProvider(this),
            UsecaseAddPerson(),
            UsecaseDeletePerson(),
            UsecaseGetAllPersons(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chargeImage()
        //initRvPersonsToAdapter()
        changesStatusRecyclerView()
        viewModel.getAllPersons()

    }

    private fun chargeImage() {
        //este metodo no funsiona
        //binding.imageView.load(assets.open("pepinilloYcacahuete.jpg"))
        binding.imageView.load(Uri.parse("file:///android_asset/pepinilloYcacahuete.jpg"))
    }

    private fun changesStatusRecyclerView() {
        viewModel.uiState.observe(this@RecyclerViewActivity) { state ->
            with(binding) {

                val listPerson: List<Person> = state.list

                listPerson.let {
                    rvPersons.addItemDecoration(
                        DividerItemDecoration(
                            rvPersons.context,
                            LinearLayoutManager.HORIZONTAL
                        )
                    )
                    rvPersons.adapter = PersonAdapter(it, ::onDeletePerson)
                    rvPersons.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
                }
            }
        }
    }


/*
        private fun initRvPersonsToAdapter() {
            with(binding) {

                val listPerson: List<Person> = viewModel.getAllPersons()

                listPerson.let {
                    rvPersons.addItemDecoration(
                        DividerItemDecoration(
                            rvPersons.context,
                            LinearLayoutManager.HORIZONTAL
                        )
                    )
                    rvPersons.adapter = PersonAdapter(it, ::onDeletePerson)
                    rvPersons.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
                }
            }
        }
 */

        private fun onDeletePerson(position: Int) {
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle("CONFIRMATION")
                .setMessage("Are you sure to delete this person?")
                .setNegativeButton("NO") { view, _ ->
                    view.dismiss()
                }
                .setPositiveButton("YES") { view, _ ->
                    viewModel.deletePerson(position)
                    binding.rvPersons.adapter?.notifyItemRemoved(position)
                    view.dismiss()
                }
                .setCancelable(false)
                .create()

            dialog.show()

        }
    }