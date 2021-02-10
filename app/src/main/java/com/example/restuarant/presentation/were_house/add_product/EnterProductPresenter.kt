package com.example.restuarant.presentation.were_house.add_product

import com.example.restuarant.model.interactor.AddProductInteractor
import com.example.restuarant.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by Davronbek on 10,Февраль,2021
 */
class EnterProductPresenter @Inject constructor(
    private val interactor: AddProductInteractor
) :BasePresenter<EnterProductView>(){

}