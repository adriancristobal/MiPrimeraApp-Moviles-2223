package com.example.miprimeraapp_moviles_2223.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miprimeraapp_moviles_2223.R
import com.example.miprimeraapp_moviles_2223.databinding.ItemPersonBinding
import com.example.miprimeraapp_moviles_2223.domain.model.Person

class PersonAdapter(
    private var listPerson: List<Person>,
    private val onPersonDelete: (Int) -> Unit
    ) : RecyclerView.Adapter<PersonsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //ProductoViewHolder(ItemProductoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false))
        return PersonsViewHolder(layoutInflater.inflate(R.layout.item_person, parent, false))
    }

    //este es el que sabe la posiciond e cada item para cuando lo queramos borrar por ejemplo

    //pregunta: si este es el que sabe la posicion del item, porque tenemos que pasar un ID del MainActivity
    //al recycler.
    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        holder.render(listPerson[position], onPersonDelete)
    }

    override fun getItemCount(): Int = listPerson.size

    fun refreshList(list: List<Person>){
        listPerson = list
        notifyDataSetChanged()
    }
}


class PersonsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemPersonBinding.bind(view)

    fun render(person: Person, onPersonDelete: (Int) -> Unit) {

        with(binding){
            tvNamePersonItem.text = person.name
            tvPhonePersonItem.text = person.phone.toString()
            ivPersonDelete.setOnClickListener {
                //la posicion la sabe el propio viewholder
                onPersonDelete(absoluteAdapterPosition)
            }
        }

    }
}