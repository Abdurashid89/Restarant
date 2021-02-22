package com.example.restuarant.model.entities

data class Times(var year: String, var month: String, var day: String) {

    fun toPattern(): Times {
        val m = if (month.toInt() < 10) "0${month.toInt()}" else (month)
        val d = if (day.toInt() < 10) "0$day" else day

        return Times(year, m, d)
    }

    override fun toString(): String {
        return "$day.$month.$year"
    }
}