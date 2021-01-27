package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWareHouseBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.presentation.were_house.WareHousePresenter
import com.example.restuarant.presentation.were_house.WareHouseView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class WareHouseFragment() : BaseFragment(), WareHouseView {
    override val layoutRes: Int = R.layout.fragment_ware_house
    private lateinit var binding: FragmentWareHouseBinding

    @InjectPresenter
    lateinit var presenter: WareHousePresenter

    @ProvidePresenter
    fun providePresenter(): WareHousePresenter = scope.getInstance(WareHousePresenter::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWareHouseBinding.bind(view)



        binding.btnLogOut.setOnClickListener {
            presenter.onBackPressed()
        }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}