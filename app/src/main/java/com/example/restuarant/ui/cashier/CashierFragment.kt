package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.extentions.*
import com.example.restuarant.model.entities.*
import com.example.restuarant.presentation.cashier.CashierPresenter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.ui.cashier.check.CheckDialog
import com.example.restuarant.ui.cashier.check.Item
import com.example.restuarant.ui.global.BaseFragment
import com.example.restuarant.ui.waiter.adapters.CategoryAdapter
import com.example.restuarant.ui.waiter.adapters.CategoryItemAdapter
import com.example.restuarant.ui.waiter.adapters.OrderAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber

/**
 * Created by shohboz on 18,Январь,2021
 */
class CashierFragment : BaseFragment(), CashierView, SwipeRefreshLayout.OnRefreshListener {
    override val layoutRes: Int = R.layout.fragment_cashier

    private var _bn: FragmentCashierBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    private val tableAdapter = CashierTableAdapter()
    private val orderAdapter = CashierOrderAdapter()
    private val historyAdapter = CashierHistoryAdapter()
    private val orderList = ArrayList<CashierOrderData>()
    private val historyList = ArrayList<CashierHistoryData>()
    private val categoryAdapter = CategoryAdapter()
    private val goodsCategoryAdapter = CategoryItemAdapter()
    private val orderAdapter2 = OrderAdapter()
//

    private lateinit var progressBar: ProgressBar
    private var currentText = ""
    private var totalSum = 0.0
    var currentMenu = 0
    private var totalPrice = ""
    var historyOpened = false
    private var orderId = 0
    var check: Check? = null

    @InjectPresenter
    lateinit var presenter: CashierPresenter

    @ProvidePresenter
    fun providePresenter(): CashierPresenter = scope.getInstance(CashierPresenter::class.java)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentCashierBinding.bind(view)

        progressBar = bn.tableProgress

        bn.swiperefresh.setOnRefreshListener(this)

        bn.tableMenu.setBackgroundResource(R.color.teal_1000)
        currentMenu = 1
        bn.tablesLayout.cashierOrderList.adapter = orderAdapter
        bn.tableList.adapter = tableAdapter
        bn.historyLayout.listHistoryCashier.adapter = historyAdapter
        bn.togoLayout.categoryRv.adapter = categoryAdapter
        bn.togoLayout.menuRv.adapter = goodsCategoryAdapter
        bn.togoLayout.orderRv.adapter = orderAdapter2

        loadHistory()
        loadButtons()
        loadTables()

        tableAdapter.setOnClickListener { tab ->
            if (tab.active) {
//                orderAdapter.clearList()
                orderList.clear()
                showSnackMessage("This table is empty!!!")
            } else {
                presenter.loadOrderByTableId(tab.id)
                bn.tablesLayout.tableNumber.text = tab.id.toString()
            }
        }

        bn.logoutMenu.setOnClickListener {
            presenter.onBackPressed()
//            makeLoadingVisible(true)
//            presenter.dialogOpen(false)
        }
        bn.unfold.setOnClickListener {
            bn.groupButtons.translationZ = 0f

        }
        bn.btnPay.setOnClickListener {
            if (notNull()) bn.groupButtons.translationZ = 40f
            else showSnackMessage(getString(R.string.choose_table))
        }
        bn.historyMenu.setOnClickListener {
            bn.groupButtons.translationZ = 0f
            historyOpened = true
            setColorMenu()
            currentMenu = 2
            it.setBackgroundResource(R.color.teal_1000)
            bn.cashierContainer.visibility = View.GONE
            bn.historyLayout.cashierHistoryLayout.visibility = View.VISIBLE
//            bn.tablesLayout.viewGroupTables.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.GONE
            Timber.d("historyListSize:${historyList.size}")
            historyAdapter.submitList(historyList)
            Timber.d("historyAdapterListSize:${historyAdapter.itemCount}")

        }
        bn.togoMenu.setOnClickListener {

            presenter.getMenu()
            historyOpened = false
            bn.groupButtons.translationZ = 0f
            setColorMenu()
            currentMenu = 3
            it.setBackgroundResource(R.color.teal_1000)
            bn.historyLayout.cashierHistoryLayout.visibility = View.GONE
            bn.cashierContainer.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.VISIBLE

        }

        bn.tableMenu.setOnClickListener {
            bn.groupButtons.translationZ = 0f
            historyOpened = false
            setColorMenu()
            bn.historyLayout.cashierHistoryLayout.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.GONE
            bn.cashierContainer.visibility = View.VISIBLE
            currentMenu = 1
            it.setBackgroundResource(R.color.teal_1000)

        }

        bn.tablesLayout.btnWithCash.setOnClickListener {
            bn.tablesLayout.priceOnCash.setText(bn.tablesLayout.totalPrice.text.toString())
        }

        bn.tablesLayout.btnSendPay.setOnClickListener {
            if (orderId != -1) {
                presenter.sendPay(orderId.toLong(), check!!.html)
                bn.tablesLayout.btnSendPay.isEnabled = false
            }
        }
//
        bn.tablesLayout.btnWithCash.setOnClickListener {
            bn.tablesLayout.priceOnCash.setText(totalPrice)
        }

        categoryAdapter.setOnClickListener {
            presenter.getMenuItems(it.id)
        }

        goodsCategoryAdapter.setOnClickListener {
            val waiterOrderData = WaiterOrderData(it.id, it.name, it.price, 1, it.price)
            if (orderAdapter2.getAllOrder().isEmpty()) {
                orderAdapter2.addProduct(waiterOrderData)
            } else {
                var isHave = false
                for (i in orderAdapter2.getAllOrder().indices) {
                    if (waiterOrderData.id == orderAdapter2.getAllOrder()[i].id) {
                        orderAdapter2.plus(i)
                        isHave = true
                    }
                }
                if (!isHave) orderAdapter2.addProduct(waiterOrderData)

                if (orderAdapter2.itemCount != 0) {
                    bn.togoLayout.orderRv.smoothScrollToPosition(orderAdapter2.itemCount - 1)
                }
            }
            presenter.totalSum()
        }

        orderAdapter2.setOnPlusClickListener {
            orderAdapter2.plus(it)
            presenter.totalSum()
        }

        orderAdapter2.setOnMinusClickListener {
            orderAdapter2.minus(it)
            presenter.totalSum()
        }

    }

    private fun loadHistory() {
        for (i in 0 until 10) {
            historyList.add(CashierHistoryData(i, "50 000", "Card", "0", "more"))
            historyList.add(CashierHistoryData(i, "45 000", "Cash", "100", "more"))
        }
        Timber.d("loadedHistoryListSize:${historyList.size}")
    }

    private fun setColorMenu() {
        when (currentMenu) {
            1 -> bn.tableMenu.setBackgroundResource(R.color.green)

            2 -> bn.historyMenu.setBackgroundResource(R.color.green)

            3 -> bn.togoMenu.setBackgroundResource(R.color.green)
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

                if (notNull()) {

                    if (it == bn.btn0 || it == bn.btn50) {

                        if (currentText.isNotEmpty() && (currentText + numberList[i]).length < 15) currentText += numberList[i]

                    } else {
                        if (it == bn.btnDot) {
                            if (currentText.isNotEmpty() && currentText.isNotDouble() && (currentText + numberList[i]).length < 15) {
                                currentText += numberList[i]
                            }
                        } else if ((currentText + numberList[i]).length < 15) currentText += numberList[i]
                    }

                    Timber.d(currentText)

                    val total_price =
                        bn.tablesLayout.totalPrice.text.toString().replace(" ", "").toDouble()

                    if (currentText.isNotEmpty()) {
                        if (currentText.isNotDouble()) {
                            val tt = currentText.toLong()
                            bn.tablesLayout.priceOnCash.setText(tt.stringFormat())

                            if (tt.toDouble() > total_price) bn.tablesLayout.priceCashBack.setText((tt - total_price).formatDouble())
                            else bn.tablesLayout.priceCashBack.setText("0")

                        } else {
                            val split = currentText.split(".")
                            val ss = split[0].toLong()
                            bn.tablesLayout.priceOnCash.setText(ss.stringFormat() + "." + split[1])

                            if (ss.toDouble() > total_price) {
                                val s = (ss - total_price).formatDouble()
                                bn.tablesLayout.priceCashBack.setText(s)
                            } else bn.tablesLayout.priceCashBack.setText("0")

                        }
                    }
                } else showSnackMessage(getString(R.string.choose_table))


            }

        }


        bn.btnDelete.setOnClickListener {
            if (currentText.length > 1) {
                currentText = currentText.substring(0, currentText.length - 1)

                val totalPrice =
                    bn.tablesLayout.totalPrice.text.toString().replace(" ", "").replace(".", "")
                        .toLong()

                if (currentText.isNotDouble()) {
                    val sum = currentText.toLong()
                    bn.tablesLayout.priceOnCash.setText(sum.stringFormat())

                    if (sum > totalPrice) {
                        val cash = (sum - totalPrice).stringFormat()
                        bn.tablesLayout.priceCashBack.setText(cash)
                    } else bn.tablesLayout.priceCashBack.setText("0")
                } else {
                    val spl = currentText.split(".")
                    val dd = spl[0].toLong()
                    bn.tablesLayout.priceOnCash.setText(dd.stringFormat() + "." + spl[1])

                    if (dd > totalPrice) {
                        val cc = (dd - totalPrice).stringFormat() + "." + spl[1]
                        bn.tablesLayout.priceCashBack.setText(cc)

                    } else bn.tablesLayout.priceCashBack.setText("0")
                }
            } else {
                currentText = ""
                bn.tablesLayout.priceOnCash.setText("0")

            }
        }
        bn.tablesLayout.btnPrint.setOnClickListener {
            bn.tablesLayout.btnSendPay.isEnabled = true
            if (notNull()) {
                val data = Item(orderAdapter.getOrderList)
                val itemNameList = data.getItemNameList()
                val price = data.getPriceList()
                check = Check(
                    "Abdurashid", itemNameList,
                    price,
                    totalPrice,
                    bn.tablesLayout.priceCashBack.text.toString(),
                    bn.tablesLayout.priceOnCash.text.toString(), "SSD Intership",
                    "NAQD"
                )
                val dialog = CheckDialog(requireContext(), check!!.html, "text/html", "UTF-8")
                Timber.d(check!!.html)
                dialog.setOnClickListener {
                    dialog._bn = null
                    dialog.dismiss()
                    bn.tablesLayout.priceOnCash.setText("0")
                    orderAdapter.submitList(arrayListOf())
                    currentText = ""
                    bn.tablesLayout.totalPrice.text = "0"
                    bn.tablesLayout.tableNumber.text = "0"
                    bn.tablesLayout.priceCashBack.setText("0")
                    bn.groupButtons.translationZ = 0f
                }
                dialog.show()


            } else showSnackMessage(getString(R.string.choose_table))

        }
    }

    private fun loadTables() {
        for (i in 1..20) {
            orderList.add(CashierOrderData(i, "Meal $i", i.toDouble(), "$i", "${i * i}"))
            orderList.add(CashierOrderData(i, "Food $i", i.toDouble(), "$i", "${i * i}"))
        }
        tableAdapter.setOnClickListener { tab ->
            if (!tab.active) {
                presenter.loadOrderByTableId(tab.id)
                bn.tablesLayout.tableNumber.text = tab.name.toString()
            } else {
                showSnackMessage("Ma'lumot yo'q")
            }

        }


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

    @SuppressLint("LogNotTimber")
    override fun addTableOrder(objectData: OrderGetData) {
        totalPrice = objectData.orderPrice.formatDouble()
        Timber.d("${objectData.orderPrice.formatDouble()}")

        /*objectData.menuSelection.forEach {
            orderId = it.id
            orderList.add(
                CashierOrderData(
                    it.id,
                    it.menu.name,
                    it.count.toDouble(),
                    it.menu.price.toString(),
                    (it.count.toLong() * it.menu.price.toLong()).toString()
                )
            )
        }*/

//        bn.tablesLayout.tableNumber.text = tab.id.toString()
        bn.tablesLayout.priceCashBack.setText("0")
        bn.tablesLayout.priceOnCash.setText("0")
        currentText = ""
        bn.tablesLayout.totalPrice.text = objectData.orderPrice.formatDouble()
        orderList.clear()

        objectData.menuSelection.forEach {
            orderList.add(
                CashierOrderData(
                    it.id,
                    it.menu.name,
                    it.count.toDouble(),
                    it.menu.price.formatDouble(),
                    (it.count * it.menu.price).formatDouble()
                )
            )
        }
        orderAdapter.submitList(orderList)



        orderAdapter.submitList(orderList)
        orderList.clear()
    }

    override fun showProgress(isShow: Boolean) {
        bn.tablesLayout.progressBarLoadOrder.visible(isShow)
    }

    override fun getItemsById(list: List<CategoryItemData>) {
        goodsCategoryAdapter.submitList(list)
    }

    override fun getMenu(list: ResData<List<CategoryData>>) {
//        menuList = list.objectData as ArrayList<CategoryData>
        categoryAdapter.submitList(list.objectData)
    }

    override fun totalSum() {
        Timber.d(orderAdapter.itemCount.toString())
        orderAdapter2.getAllOrder().forEach { totalSum += it.productTotalPrice }
        bn.togoLayout.totalSumTv.text = totalSum.formatDouble()
        totalSum = 0.0
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun submitTables(list: List<TableData>) {
        Timber.d(list.size.toString())
        bn.tableProgress.visibility = View.GONE
        bn.swiperefresh.isRefreshing = false
        if (list.isNotEmpty()) {
            tableAdapter.submitList(list as ArrayList<TableData>)
            bn.btnPay.visibility = View.VISIBLE
        } else {
            bn.btnPay.visibility = View.GONE
        }
    }

    override fun onRefresh() {
        presenter.getTables()
    }

    override fun openDialog(status: Boolean) {
        if (status) {
            // send start price to server
            val dialog =
                CashOpenExitDialog(requireContext(), getString(R.string.open_cash), "Start price")
            dialog.setOnClickListener {
                makeLoadingVisible(false)
                dialog.dismiss()
                dialog._bn = null
            }
            dialog.show()
        } else {
            // send start price to server and exit screen
            val dialog =
                CashOpenExitDialog(requireContext(), getString(R.string.exit_cash), "End price")
            dialog.setOnClickListener {
                makeLoadingVisible(false)
                dialog.dismiss()
                dialog._bn = null
                presenter.onBackPressed()
            }
            dialog.show()
        }

    }

    private fun notNull(): Boolean {
        return bn.tablesLayout.totalPrice.text != "0" && orderAdapter.itemCount != 0
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}
