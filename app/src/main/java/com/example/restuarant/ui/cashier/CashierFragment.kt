package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.databinding.FragmentSignupBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.CashierOrderData
import com.example.restuarant.model.entities.CashierTableData
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.presentation.cashier.CashierPresenter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.presentation.signup.SignUpPresenter
import com.example.restuarant.presentation.signup.SignUpView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * Created by shohboz on 18,Январь,2021
 */
class CashierFragment : BaseFragment(), CashierView {
    override val layoutRes: Int = R.layout.fragment_cashier

    private lateinit var binding : FragmentCashierBinding
    var menu_table_visible = true
    private val tableAdapter = CashierTableAdapter()
    private val orderAdapter = CashierOrderAdapter()

    @InjectPresenter
    lateinit var presenter: CashierPresenter

    @ProvidePresenter
    fun providePresenter(): CashierPresenter = scope.getInstance(CashierPresenter::class.java)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCashierBinding.bind(view)

        binding.tableMenu.setBackgroundColor(R.color.green)
        binding.tablesLayout.viewGroupTables.visibility = View.VISIBLE

        loadTables()

        binding.logoutMenu.setOnClickListener {
            presenter.onBackPressed()
        }
        binding.tableMenu.setOnClickListener {
            if(!menu_table_visible){
                view.setBackgroundColor(R.color.green)
                binding.tablesLayout.viewGroupTables.visibility = View.VISIBLE
            }
        }

    }

    private fun loadTables() {
        val tableList = ArrayList<CashierTableData>()
        val orderList = ArrayList<CashierOrderData>()
        for(i in 1..10){
            orderList.add(CashierOrderData(i,"Meal $i",i*1.0,i*10.0,"${i*10}"))
        }
        for(i in 1..20){
            tableList.add(CashierTableData(i,orderList))
        }
        binding.tableList.adapter = tableAdapter
        tableAdapter.submitList(tableList)
        tableAdapter.setOnClickListener {
            binding.tablesLayout.tableNumber.text = it.id.toString()
            binding.tablesLayout.cashierOrderList.adapter = orderAdapter
            var total = 0.0
            it.currentOrder.forEach {
                total += it.total.toDouble()
            }
            binding.tablesLayout.totalPrice.text = total.toString()
            orderAdapter.submitList(null)
            orderAdapter.submitList(it.currentOrder)
        }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
//        binding.progressBarRegister.loading.visible(status)
    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }


}