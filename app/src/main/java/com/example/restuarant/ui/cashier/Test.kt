package com.example.restuarant.ui.cashier

/**
 * Created by shohboz on 29,Январь,2021
 */

fun main(){
    val a = A().person
    val a2 = A().person // og'r

    val b = B(Person(2,"Anvar")) // normal
    val b2 = B(Person(3,"Zokir"))

    
}

data class Person(
    val id:Int,
    val name:String
)

class A{
    val person = Person(1,"Davron")
}

class B(val p:Person)