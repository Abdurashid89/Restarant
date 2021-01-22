package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.stringFormat
import com.example.restuarant.model.entities.CashierOrderData
import com.example.restuarant.model.entities.CashierTableData
import com.example.restuarant.presentation.cashier.CashierPresenter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * Created by shohboz on 18,Январь,2021
 */
class CashierFragment : BaseFragment(), CashierView {
    override val layoutRes: Int = R.layout.fragment_cashier

    private lateinit var binding: FragmentCashierBinding
    var menu_table_visible = true
    private val tableAdapter = CashierTableAdapter()
    private val orderAdapter = CashierOrderAdapter()
    private var currentText = ""
    var currentMenu = 0

    @InjectPresenter
    lateinit var presenter: CashierPresenter

    @ProvidePresenter
    fun providePresenter(): CashierPresenter = scope.getInstance(CashierPresenter::class.java)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCashierBinding.bind(view)

        binding.tableMenu.setBackgroundColor(R.color.red)
        currentMenu = 1
        binding.tablesLayout.viewGroupTables.visibility = View.VISIBLE

        loadTables()
        loadButtons()

        binding.logoutMenu.setOnClickListener {
            presenter.onBackPressed()
        }
        binding.historyMenu.setOnClickListener {
            setColorMenu()
            currentMenu = 2
            it.setBackgroundColor(R.color.red)

            binding.historyLayout.cashierHistoryLayout.visibility = View.VISIBLE
            binding.tablesLayout.viewGroupTables.visibility = View.GONE
            binding.ownLayout.cashierOwnLayout.visibility = View.GONE

        }
        binding.ownMenu.setOnClickListener {
            setColorMenu()
            currentMenu = 3
            it.setBackgroundColor(R.color.red)
            binding.historyLayout.cashierHistoryLayout.visibility = View.GONE
            binding.tablesLayout.viewGroupTables.visibility = View.GONE
            binding.ownLayout.cashierOwnLayout.visibility = View.VISIBLE

        }

        binding.tableMenu.setOnClickListener {
            setColorMenu()
            binding.historyLayout.cashierHistoryLayout.visibility = View.GONE
            binding.ownLayout.cashierOwnLayout.visibility = View.GONE
            binding.tablesLayout.viewGroupTables.visibility = View.VISIBLE

            currentMenu = 1
            it.setBackgroundColor(R.color.red)

        }

    }

    private fun setColorMenu() {
        when (currentMenu) {
            1 -> {
                context?.resources?.getColor(R.color.purple_200)
                    ?.let { binding.tableMenu.setBackgroundColor(it) }
            }
            2 -> {
                context?.resources?.getColor(R.color.purple_200)
                    ?.let { binding.historyMenu.setBackgroundColor(it) }
            }
            3 -> {
                context?.resources?.getColor(R.color.purple_200)
                    ?.let { binding.ownMenu.setBackgroundColor(it) }
            }
        }
    }

    private fun loadButtons() {
        val numberList = ArrayList<String>()
        for (i in 1..9) {
            numberList.add("$i")
        }
        numberList.add("00")
        numberList.add("0")

        for (i in 0 until binding.cashKeypatGroup.childCount - 1) {
            binding.cashKeypatGroup.getChildAt(i).setOnClickListener {
                if (binding.tablesLayout.totalPrice.text != "0" && orderAdapter.itemCount != 0) {
                    currentText += numberList[i]
                    binding.tablesLayout.priceOnCash.setText(stringFormat(currentText.toInt()))
                    val cur = currentText.toInt()
                    val back = binding.tablesLayout.totalPrice.text.toString().toInt()
                    if (cur > back)
                        binding.tablesLayout.priceCashBack.setText(stringFormat((cur - back)))
                    else binding.tablesLayout.priceCashBack.setText("0")
                }

            }
        }
        binding.btnDelete.setOnClickListener {
            if (currentText.length >= 2) {
                currentText = currentText.substring(0, currentText.length - 1)
                binding.tablesLayout.priceOnCash.setText(stringFormat(currentText.toInt()))

                val cur = currentText.toInt()
                val back = binding.tablesLayout.totalPrice.text.toString().toInt()
                if (cur > back)
                    binding.tablesLayout.priceCashBack.setText(stringFormat((cur - back)))
                else binding.tablesLayout.priceCashBack.setText("0")
            } else {
                currentText = ""
                binding.tablesLayout.priceOnCash.setText("0")
                binding.tablesLayout.priceCashBack.setText("0")
            }

        }
        binding.tablesLayout.btnPrint.setOnClickListener {
            if (binding.tablesLayout.totalPrice.text.toString() != "0" && orderAdapter.itemCount != 0) {
                binding.tablesLayout.priceOnCash.setText("0")
                orderAdapter.submitList(null)
                currentText = ""
                binding.tablesLayout.totalPrice.text = "0"
                binding.tablesLayout.tableNumber.text = "0"
                binding.tablesLayout.priceCashBack.setText("0")
            }

        }
    }

    private fun loadTables() {
        val tableList = ArrayList<CashierTableData>()
        val orderList = ArrayList<CashierOrderData>()
        for (i in 1..10) {
            orderList.add(CashierOrderData(i, "Meal $i", i, i, "${i * i}"))
            orderList.add(CashierOrderData(i, "Food $i", i + 1, i + 1, "${(i + 1) * (i + 1)}"))
        }
        for (i in 1..20) {
            tableList.add(CashierTableData(i,"#F42B4A", orderList))
        }
        binding.tableList.adapter = tableAdapter
        tableAdapter.submitList(tableList)
        tableAdapter.setOnClickListener {
            binding.tablesLayout.tableNumber.text = it.id.toString()
            binding.tablesLayout.cashierOrderList.adapter = orderAdapter
            binding.tablesLayout.priceCashBack.setText("0")
            binding.tablesLayout.priceOnCash.setText("0")
            currentText = ""
            var total = 0
            it.currentOrder.forEach {
                total += it.total.toInt()
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