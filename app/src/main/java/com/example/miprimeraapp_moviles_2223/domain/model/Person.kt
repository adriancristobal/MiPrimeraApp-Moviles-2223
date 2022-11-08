package com.example.miprimeraapp_moviles_2223.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(val idList: Int, val name: String,val password: String,
                  val phone: Int, val gender: Int, val email: String) : Parcelable
