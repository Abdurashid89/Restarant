package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.extentions.isDouble
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
    private val tableAdapter = CashierTableAdapter()
    private val orderAdapter = CashierOrderAdapter()
    private var currentText = ""
    var currentMenu = 0

    @InjectPresenter
    lateinit var presenter: CashierPresenter

    @ProvidePresenter
    fun providePresenter(): CashierPresenter = scope.getInstance(CashierPresenter::class.java)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCashierBinding.bind(view)

        binding.tableMenu.setBackgroundResource(R.color.teal_1000)
        currentMenu = 1
        binding.tablesLayout.viewGroupTables.visibility = View.VISIBLE

        loadTables()
        loadButtons()

        binding.logoutMenu.setOnClickListener {
            presenter.onBackPressed()
        }
        binding.unfold.setOnClickListener {
            binding.groupButtons.translationZ = 0f

        }
        binding.btnPay.setOnClickListener {
            if (binding.tablesLayout.totalPrice.text != "0" && orderAdapter.itemCount != 0)
                binding.groupButtons.translationZ = 40f
        }
        binding.historyMenu.setOnClickListener {
            setColorMenu()
            currentMenu = 2
            it.setBackgroundResource(R.color.teal_1000)
            binding.historyLayout.cashierHistoryLayout.visibility = View.VISIBLE
            binding.tablesLayout.viewGroupTables.visibility = View.GONE
            binding.togoLayout.cashierOwnLayout.visibility = View.GONE

        }
        binding.togoMenu.setOnClickListener {
            setColorMenu()
            currentMenu = 3
            it.setBackgroundResource(R.color.teal_1000)
            binding.historyLayout.cashierHistoryLayout.visibility = View.GONE
            binding.tablesLayout.viewGroupTables.visibility = View.GONE
            binding.togoLayout.cashierOwnLayout.visibility = View.VISIBLE

        }

        binding.tableMenu.setOnClickListener {
            setColorMenu()
            binding.historyLayout.cashierHistoryLayout.visibility = View.GONE
            binding.togoLayout.cashierOwnLayout.visibility = View.GONE
            binding.tablesLayout.viewGroupTables.visibility = View.VISIBLE

            currentMenu = 1
            it.setBackgroundResource(R.color.teal_1000)

        }

    }

    private fun setColorMenu() {
        when (currentMenu) {
            1 -> binding.tableMenu.setBackgroundResource(R.color.purple_200)

            2 -> binding.historyMenu.setBackgroundResource(R.color.purple_200)

            3 ->  binding.togoMenu.setBackgroundResource(R.color.purple_200)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun loadButtons() {

        val numberList = ArrayList<String>()
        for (i in 1..9) {
            numberList.add("$i")
        }
        numberList.add(".")
        numberList.add("0")
        numberList.add("00")
        numberList.add("50")
        numberList.add("100")

        for (i in 0 until binding.groupButtons.childCount - 1) {
            binding.groupButtons.getChildAt(i + 1).setOnClickListener {
                if (binding.tablesLayout.totalPrice.text != "0" && orderAdapter.itemCount != 0) {
                    if (it == binding.btn0) {
                        if (currentText.isNotEmpty())
                            if ((currentText + numberList[i]).length < 15) currentText += numberList[i]
                    } else if (it == binding.btnDot) {
                        if (currentText.isNotEmpty() && currentText.isDouble()) {
                            if ((currentText + numberList[i]).length < 15) currentText += numberList[i]
                        }
                    } else if ((currentText + numberList[i]).length < 15) currentText += numberList[i]
                    Log.d("AAA", "currentTex : $currentText")

                    val total_price = binding.tablesLayout.totalPrice.text.toString().toLong()

                    if (currentText.isNotEmpty())
                        if (currentText.isDouble()) {
                            val tt = currentText.toLong()
                            binding.tablesLayout.priceOnCash.setText(tt.stringFormat())

                            if (tt > total_price) {
                                binding.tablesLayout.priceCashBack.setText((tt - total_price).stringFormat())
                            }
                        } else {
                            val split = currentText.split(".")
                            val ss = split[0].toLong()
                            binding.tablesLayout.priceOnCash.setText(ss.stringFormat() + "." + split[1])

                            if (ss > total_price) {
                                val s = (ss - total_price).stringFormat() + "." + split[1]
                                binding.tablesLayout.priceCashBack.setText(s)
                            } else binding.tablesLayout.priceCashBack.setText("0")
                        }
                } else Toast.makeText(requireContext(), "Please choose table number", Toast.LENGTH_SHORT).show()

            }

        }

        binding.btnDelete.setOnClickListener {
            if (currentText.length > 1) {
                currentText = currentText.substring(0, currentText.length - 1)

                val total_price = binding.tablesLayout.totalPrice.text.toString().toLong()

                if (currentText.isDouble()) {
                    val sum = currentText.toLong()
                    binding.tablesLayout.priceOnCash.setText(sum.stringFormat())

                    if (sum > total_price) {
                        val cash = (sum - total_price).stringFormat()
                        binding.tablesLayout.priceCashBack.setText(cash)
                    } else binding.tablesLayout.priceCashBack.setText("0")
                } else {
                    val spl = currentText.split(".")
                    val dd = spl[0].toLong()
                    binding.tablesLayout.priceOnCash.setText(dd.stringFormat() + "." + spl[1])

                    if (dd > total_price) {
                        val cc = (dd - total_price).stringFormat() + "." + spl[1]
                        binding.tablesLayout.priceCashBack.setText(cc)

                    } else binding.tablesLayout.priceCashBack.setText("0")
                }
            } else {
                currentText = ""
                binding.tablesLayout.priceOnCash.setText("0")

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
                binding.groupButtons.translationZ = 0f
            } else Toast.makeText(requireContext(), "Please choose table number", Toast.LENGTH_SHORT).show()

        }
    }

    private fun loadTables() {
        val tableList = ArrayList<CashierTableData>()
        val orderList = ArrayList<CashierOrderData>()
        val orderList2 = ArrayList<CashierOrderData>()
        for (i in 1..20) {
            orderList.add(CashierOrderData(i, "Meal $i", i, i, "${i * i}"))
            orderList.add(CashierOrderData(i, "Food $i", i, i, "${i * i}"))
            orderList2.add(CashierOrderData(i, "Food $i", i + 1, i + 1, "${(i + 1) * (i + 1)}"))
            orderList2.add(CashierOrderData(i, "Meal $i", i + 1, i + 1, "${(i + 1) * (i + 1)}"))
        }
        for (i in 1..20) {
            tableList.add(CashierTableData(i, "#F42B4A", if (i % 2 == 1) orderList else orderList2))
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
