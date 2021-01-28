package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.extentions.isNotDouble
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.stringFormat
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.CashierOrderData
import com.example.restuarant.model.entities.CashierTableData
import com.example.restuarant.model.entities.TableResData
import com.example.restuarant.presentation.cashier.CashierPresenter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.ui.cashier.check.Check2
import com.example.restuarant.ui.cashier.check.CheckDialog
import com.example.restuarant.ui.cashier.check.Item2
import com.example.restuarant.ui.global.BaseFragment
import com.example.restuarant.ui.global.BaseWatcher
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import java.lang.NullPointerException

/**
 * Created by shohboz on 18,Январь,2021
 */
class CashierFragment : BaseFragment(), CashierView, SwipeRefreshLayout.OnRefreshListener {
    override val layoutRes: Int = R.layout.fragment_cashier

    private var _bn: FragmentCashierBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    private val tableAdapter = CashierTableAdapter()
    private val orderAdapter = CashierOrderAdapter()
    val orderList = ArrayList<CashierOrderData>()
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
        _bn = FragmentCashierBinding.bind(view)

        bn.swiperefresh.setOnRefreshListener(this)
        val v = object : BaseWatcher {

        }

        bn.tableMenu.setBackgroundResource(R.color.teal_1000)
        currentMenu = 1
        bn.tablesLayout.viewGroupTables.visibility = View.VISIBLE

        loadTables()
        loadButtons()

        bn.logoutMenu.setOnClickListener {
            makeLoadingVisible(true)
            presenter.dialogOpen(false)
        }
        bn.unfold.setOnClickListener {
            bn.groupButtons.translationZ = 0f

        }
        bn.btnPay.setOnClickListener {
            if (bn.tablesLayout.totalPrice.text != "0" && orderAdapter.itemCount != 0)
                bn.groupButtons.translationZ = 40f
            else showSnackMessage(getString(R.string.choose_table))
        }
        bn.historyMenu.setOnClickListener {
            bn.groupButtons.translationZ = 0f
            setColorMenu()
            currentMenu = 2
            it.setBackgroundResource(R.color.teal_1000)
            bn.historyLayout.cashierHistoryLayout.visibility = View.VISIBLE
            bn.tablesLayout.viewGroupTables.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.GONE

        }
        bn.togoMenu.setOnClickListener {
            bn.groupButtons.translationZ = 0f
            setColorMenu()
            currentMenu = 3
            it.setBackgroundResource(R.color.teal_1000)
            bn.historyLayout.cashierHistoryLayout.visibility = View.GONE
            bn.tablesLayout.viewGroupTables.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.VISIBLE

        }

        bn.tableMenu.setOnClickListener {
            bn.groupButtons.translationZ = 0f
            setColorMenu()
            bn.historyLayout.cashierHistoryLayout.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.GONE
            bn.tablesLayout.viewGroupTables.visibility = View.VISIBLE

            currentMenu = 1
            it.setBackgroundResource(R.color.teal_1000)

        }

    }

    private fun setColorMenu() {
        when (currentMenu) {
            1 -> bn.tableMenu.setBackgroundResource(R.color.purple_200)

            2 -> bn.historyMenu.setBackgroundResource(R.color.purple_200)

            3 -> bn.togoMenu.setBackgroundResource(R.color.purple_200)
        }
    }

    @SuppressLint("SetTextI18n", "TimberArgCount")
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

        for (i in 0 until bn.groupButtons.childCount - 1) {

            bn.groupButtons.getChildAt(i + 1).setOnClickListener {

                if (bn.tablesLayout.totalPrice.text != "0" && orderAdapter.itemCount != 0) {

                    if (it == bn.btn0 || it == bn.btn50) {

                        if (currentText.isNotEmpty() && (currentText + numberList[i]).length < 15) currentText += numberList[i]

                    } else {
                        if (it == bn.btnDot) {
                            if (currentText.isNotEmpty() && currentText.isNotDouble() && (currentText + numberList[i]).length < 15) {
                                currentText += numberList[i]
                            }
                        } else if ((currentText + numberList[i]).length < 15) currentText += numberList[i]
                    }

//                    Log.d("AAA", "currentTex : $currentText")
                    Timber.d(currentText)

                    val total_price = bn.tablesLayout.totalPrice.text.toString().toLong()

                    if (currentText.isNotEmpty())
                        if (currentText.isNotDouble()) {
                            val tt = currentText.toLong()
                            bn.tablesLayout.priceOnCash.setText(tt.stringFormat())

                            if (tt > total_price) bn.tablesLayout.priceCashBack.setText((tt - total_price).stringFormat())

                        } else {
                            val split = currentText.split(".")
                            val ss = split[0].toLong()
                            bn.tablesLayout.priceOnCash.setText(ss.stringFormat() + "." + split[1])

                            if (ss > total_price) {
                                val s = (ss - total_price).stringFormat() + "." + split[1]
                                bn.tablesLayout.priceCashBack.setText(s)
                            } else bn.tablesLayout.priceCashBack.setText("0")

                        }
                } else showSnackMessage(getString(R.string.choose_table))


            }

        }


        bn.btnDelete.setOnClickListener {
            if (currentText.length > 1) {
                currentText = currentText.substring(0, currentText.length - 1)

                val total_price = bn.tablesLayout.totalPrice.text.toString().toLong()

                if (currentText.isNotDouble()) {
                    val sum = currentText.toLong()
                    bn.tablesLayout.priceOnCash.setText(sum.stringFormat())

                    if (sum > total_price) {
                        val cash = (sum - total_price).stringFormat()
                        bn.tablesLayout.priceCashBack.setText(cash)
                    } else bn.tablesLayout.priceCashBack.setText("0")
                } else {
                    val spl = currentText.split(".")
                    val dd = spl[0].toLong()
                    bn.tablesLayout.priceOnCash.setText(dd.stringFormat() + "." + spl[1])

                    if (dd > total_price) {
                        val cc = (dd - total_price).stringFormat() + "." + spl[1]
                        bn.tablesLayout.priceCashBack.setText(cc)

                    } else bn.tablesLayout.priceCashBack.setText("0")
                }
            } else {
                currentText = ""
                bn.tablesLayout.priceOnCash.setText("0")

            }
        }
        bn.tablesLayout.btnPrint.setOnClickListener {
            val data = Item2(orderList)
            val itemNameList = data.getItemNameList()
            val price = data.getItemNameList()
            val check = Check2().setData(itemNameList,price)
            val dialog = CheckDialog(requireContext(),check,"text/html", "UTF-8")
            dialog.setOnClickListener {
                dialog.dismiss()
                dialog._bn = null
                showSnackMessage(it)
            }

            if (bn.tablesLayout.totalPrice.text.toString() != "0" && orderAdapter.itemCount != 0) {
                bn.tablesLayout.priceOnCash.setText("0")
                orderAdapter.submitList(null)
                currentText = ""
                bn.tablesLayout.totalPrice.text = "0"
                bn.tablesLayout.tableNumber.text = "0"
                bn.tablesLayout.priceCashBack.setText("0")
                bn.groupButtons.translationZ = 0f
            } else showSnackMessage(getString(R.string.choose_table))

        }
    }

    private fun loadTables() {
        val tableList = ArrayList<CashierTableData>()
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
        bn.tableList.adapter = tableAdapter
        tableAdapter.setOnClickListener {
            bn.tablesLayout.tableNumber.text = it.id.toString()
            bn.tablesLayout.cashierOrderList.adapter = orderAdapter
            bn.tablesLayout.priceCashBack.setText("0")
            bn.tablesLayout.priceOnCash.setText("0")
            currentText = ""
            val total = "654 321"
//            it.currentOrder.forEach {
//                total += it.total.toInt()
//            }
            bn.tablesLayout.totalPrice.text = total
            orderAdapter.submitList(null)
            orderAdapter.submitList(orderList)
        }
        orderAdapter.submitList(orderList)

    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
            bn.progressBarGlobal.loading.visible(status)
            bn.layoutGlobal.isClickable = !status
    }

    override fun openErrorDialog(message: String, status: Boolean) {
        bn.tableProgress.visibility = View.GONE
        bn.btnPay.visibility = View.GONE
        bn.swiperefresh.isRefreshing = false
        showSnackMessage(message)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun submitTables(list: List<TableResData>) {
        if(list.isNotEmpty())
            bn.btnPay.visibility = View.VISIBLE

        bn.tableProgress.visibility = View.GONE

        bn.swiperefresh.isRefreshing = false
        tableAdapter.submitList(list)
    }

    override fun onRefresh() {
        presenter.getTables()
    }

    override fun openDialog(status: Boolean) {
        if(status){
            // send start price to server
            val dialog = CashOpenExitDialog(requireContext(),getString(R.string.open_cash),"Start price")
            dialog.setOnClickListener {
                makeLoadingVisible(false)
                dialog.dismiss()
                dialog._bn = null
            }
            dialog.show()
        }else{
            // send start price to server and exit screen
            val dialog = CashOpenExitDialog(requireContext(),getString(R.string.exit_cash),"End price")
            dialog.setOnClickListener {
                makeLoadingVisible(false)
                dialog.dismiss()
                dialog._bn = null
                presenter.onBackPressed()
            }
            dialog.show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}
