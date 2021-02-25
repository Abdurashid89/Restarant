package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.*
import com.example.restuarant.model.entities.*
import com.example.restuarant.model.entities.check.PaidCheck
import com.example.restuarant.presentation.cashier.CashierPresenter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.ui.cashier.check.CheckDialog
import com.example.restuarant.ui.cashier.check.Item
import com.example.restuarant.ui.global.BaseFragment
import com.example.restuarant.ui.waiter.adapters.CategoryAdapter
import com.example.restuarant.ui.waiter.adapters.CategoryItemAdapter
import com.example.restuarant.ui.waiter.adapters.OrderAdapter
import com.example.restuarant.ui.waiter.callback.SwipeToDeleteCallback
import com.labo.kaji.fragmentanimations.CubeAnimation
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by shohboz on 18,Январь,2021
 */
class CashierFragment : BaseFragment(), CashierView, SwipeRefreshLayout.OnRefreshListener,
    ITextWatcher {
    override val layoutRes: Int = R.layout.fragment_cashier
    val calendar = Calendar.getInstance()

    //    lateinit var times
    var startDay: Long = 0
    var endDay: Long = 0

    private var _bn: FragmentCashierBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")

    // lists
    private val orderList = ArrayList<CashierOrderData>()
    private val historyList = ArrayList<OrderGetData>()
    private val filterList = ArrayList<OrderGetData>()
    private var tableList = ArrayList<TableData>()

    // adapters
    private val tableAdapter = CashierTableAdapter()
    private val orderAdapter = CashierOrderAdapter()
    private val historyAdapter = CashierHistoryAdapter()
    private val orderList2 = ArrayList<MenuSelect>()
    private val categoryAdapter = CategoryAdapter()
    private val goodsCategoryAdapter = CategoryItemAdapter()
    private val orderAdapter2 = OrderAdapter()
    private val tableAdapter3 = CashierTableAdapter3()

    // views
    private lateinit var progressBar: ProgressBar

    // temp
    private var tableId = -1
    private var isFirst = true
    private var tableOrderId = -1L
    private var currentText = ""
    private var totalSum = 0.0
    var currentMenu = 0
    private var totalPrice = ""
    private var orderPrice = 0.0
    var historyOpened = false
    var check: Check? = null

    @InjectPresenter
    lateinit var presenter: CashierPresenter


    @ProvidePresenter
    fun providePresenter(): CashierPresenter = scope.getInstance(CashierPresenter::class.java)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor", "TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _bn = FragmentCashierBinding.bind(view)
//        firstMillisecond = convertDateToLong(Date().time.toString())
        bn.historyLayout.tvEndDay.text = formatDate()
        bn.historyLayout.tvStartDay.text = formatDate()

        progressBar = bn.tableProgress
//
        bn.swiperefresh.setOnRefreshListener(this)

        bn.tableMenu.setBackgroundResource(R.color.teal_1000)
        currentMenu = 1
        bn.tablesLayout.cashierOrderList.adapter = orderAdapter
        bn.tableList.adapter = tableAdapter
        bn.historyLayout.listHistoryCashier.adapter = historyAdapter
        bn.togoLayout.categoryRv.adapter = categoryAdapter
        bn.togoLayout.menuRv.adapter = goodsCategoryAdapter
        bn.togoLayout.orderRv.adapter = orderAdapter2
        bn.togoLayout.tableListRv.adapter = tableAdapter3

        Log.d("millisecond", startDay.toString())

        loadTables()
        loadButtons()
        tableAdapter.setOnClickListener { tab ->
            if (tab.active) {
//                orderAdapter.clearList()
                orderList.clear()
                showSnackMessage("This table is empty!!!")
            } else {
                presenter.loadOrderByTableId(tab.id, 1)
                bn.tablesLayout.tableNumber.text = tab.id.toString()
            }
        }

//
//        bn.togoLayout.orderRv.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        val swipeHelper = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipeAdapter = bn.togoLayout.orderRv.adapter as OrderAdapter
                swipeAdapter.removeAt(viewHolder.adapterPosition)
                totalSum()
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(bn.togoLayout.orderRv)


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
            bn.historyLayout.tvEndDay.text = convertLongToTime(Date().time)
            historyAdapter.getAllHistory().forEach {
                historyList.add(it)

            }

            //all history loaded
            presenter.loadHistory()
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
            Timber.d("historyAdapterListSize:${historyAdapter.itemCount}")
//            filterList.clear()
        }

        bn.togoMenu.setOnClickListener {
            tableAdapter3.submitList(tableList)
            presenter.getMenu()
            historyOpened = false
            bn.groupButtons.translationZ = 0f
            setColorMenu()
            currentMenu = 3
            it.setBackgroundResource(R.color.teal_1000)
            bn.historyLayout.cashierHistoryLayout.visibility = View.GONE
            bn.cashierContainer.visibility = View.GONE
            bn.togoLayout.cashierOwnLayout.visibility = View.VISIBLE
            bn.togoLayout.togoOrderConstraint.visibility = View.GONE
            bn.togoLayout.tableListRv.visibility = View.VISIBLE
        }

        bn.tablesLayout.priceOnCash.setOnClickListener {
            if (notNull())
                bn.groupButtons.translationZ = 40f
            else showMessage("Please choose table number")
        }

        tableAdapter3.setOnClickListener {
            if (it.active) {
                isFirst = true
                tableId = it.id
                clearList(false)
                bn.togoLayout.togoOrderConstraint.visibility = View.VISIBLE
                bn.togoLayout.tableListRv.visibility = View.GONE
                bn.togoLayout.tableNumber.text = it.name.toString()
            } else {
                tableId = it.id
                presenter.loadOrderByTableId(it.id, 2)
                orderAdapter2.clear()
                bn.togoLayout.togoOrderConstraint.visibility = View.VISIBLE
                bn.togoLayout.tableListRv.visibility = View.GONE
                bn.togoLayout.tableNumber.text = it.name.toString()
            }


        }

        bn.tableMenu.setOnClickListener {
            bn.tablesLayout.tvEmpty.visibility = View.VISIBLE
            bn.tablesLayout.cashierOrderList.isVisible = false
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
            val paidPrice = bn.tablesLayout.priceOnCash.text.toString().replace(" ", "").toDouble()
            val cashBack = bn.tablesLayout.priceCashBack.text.toString().replace(" ", "").toDouble()
            if (paidPrice < orderPrice) {
                showSnackMessage("Iltimos yetarli mablag' kiriting!")
            } else {

                presenter.sendPay(
                    PaidCheck(
                        tableOrderId,
                        paidPrice,
                        cashBack,
                        "NAQD",
                        createCheck()
                    )
                )

            }

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

        bn.togoLayout.btnPrint.setOnClickListener {
            if (isFirst) {
                if (orderAdapter2.getAllOrder().isNotEmpty()) {
                    orderAdapter2.getAllOrder().forEach {
                        orderList2.add(MenuSelect(it.productCount, it.id))
                    }
                    presenter.sendOrder(
                        OrderSendData(
                            "Toshkent",
                            "ON_THE_WAY",
                            totalSum,
                            "DELIVERY",
                            "UN PAID",
                            tableId,
                            null,
                            orderList2,
                            1
                        )
                    )
                } else {
                    showSnackMessage("Avval mahsulot tanlang!")
                }
            } else {
                orderList2.clear()
                if (orderAdapter2.getAllOrder().isNotEmpty()) {
                    orderAdapter2.getAllOrder().forEach {
                        orderList2.add(MenuSelect(it.productCount, it.id))
                    }
                }
                presenter.orderUpdate(
                    OrderUpdateData(
                        tableOrderId, "Toshkent",
                        "ON_THE_WAY",
                        totalSum,
                        "DELIVERY",
                        "UN PAID",
                        tableId,
                        null,
                        orderList2,
                        1
                    )
                )
            }
        }

        bn.togoLayout.btnBack.setOnClickListener {
            bn.togoLayout.cashierOwnLayout.visibility = View.VISIBLE
            bn.togoLayout.togoOrderConstraint.visibility = View.GONE
            bn.togoLayout.tableListRv.visibility = View.VISIBLE
        }

        bn.historyLayout.tvToday.setOnClickListener {
            historyList.forEach {
                val date =
                    it.createdAt.replace("T", ".").replace("-", ".").substring(0, 16)
                        .convertDateToLong()
                if (date > DI.CURRENT_MILLISECOND) {
                    filterList.add(it)
                    Log.d("CashierFragment", "${date > DI.CURRENT_MILLISECOND}")
                    Log.d("OrderData", "-> $it")
                } else showSnackMessage("Buyurtma qabul qilinmagan")
            }
            historyAdapter.submitList(filterList)
            bn.historyLayout.listHistoryCashier.fadeIn()
            if (filterList.isEmpty()) {
                bn.historyLayout.tvEmptyHistory.visibility = View.VISIBLE
                bn.historyLayout.listHistoryCashier.visibility = View.GONE

            }
            filterList.clear()
            Log.d("CashierFragment", "${DI.CURRENT_MILLISECOND}")
        }


        bn.historyLayout.historySearch.addTextChangedListener(CustomWatcher(this))

        bn.historyLayout.calendar.setOnClickListener { openEndDateDialog() }
        bn.historyLayout.tvStartDay.setOnClickListener { openStartDateDialog() }


        bn.historyLayout.tvStartDay.text = "${convertLongToTime(firstMillisecond)}"
        bn.historyLayout.tvEndDay.text = convertLongToTime(Date().time)




        historyAdapter.setOnClickListener {
            Log.d("OrderDetail", "$it")
            val dialog = CheckDialog(requireContext(), it.cheque, textHtml, utf)
            dialog.setOnClickListener {
                dialog._bn = null
                dialog.dismiss()
            }
            dialog.show()
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------

    fun getFilterableOrder() {
        bn.historyLayout.listHistoryCashier.visibility = View.VISIBLE
        Log.d("CashierFragmentTime", "${historyList.size}")
        if (startDay > 0 && endDay > 0 && startDay < endDay) {
            historyList.forEach {
                val date =
                    it.createdAt.replace("T", ".").replace("-", ".").substring(0, 16)
                        .convertDateToLong()

                if ((date > startDay) && (date < endDay)) {
                    filterList.add(it)
                    Log.d("OrderData", "-> $it")
                    bn.historyLayout.listHistoryCashier.visibility = View.VISIBLE
                }
            }
        }
        historyAdapter.submitList(filterList)
        bn.historyLayout.listHistoryCashier.fadeIn()
        filterList.clear()
    }

    fun openStartDateDialog() {
        val dialog = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
            fragmentManager?.let {
                TimePickerDialog.newInstance({ view, hourOfDay, minute, second ->
                    val time = Times(
                        "$year",
                        "$monthOfYear",
                        "$dayOfMonth",
                        "$hourOfDay",
                        "$minute",
                        "$second"
                    )
                    bn.historyLayout.tvStartDay.text = time.toPattern().toString()
//                    calendar.set(time.year.toInt(), time.month.toInt(), time.day.toInt())
                    startDay =
                        bn.historyLayout.tvStartDay.text.toString().convertDateToLong()

                    getFilterableOrder()
                }, true)

                    .show(it, "tag")
            }

        }
        dialog.maxDate = Calendar.getInstance()
        fragmentManager?.let { dialog.show(it, "date") }

    }


    fun openEndDateDialog() {
        val dialog = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
            fragmentManager?.let {
                TimePickerDialog.newInstance({ view, hourOfDay, minute, second ->
                    val time = Times(
                        "$year",
                        "$monthOfYear",
                        "$dayOfMonth",
                        "$hourOfDay",
                        "$minute",
                        "$second"
                    )
                    bn.historyLayout.tvEndDay.text = time.toPattern().toString()

                    calendar.set(time.year.toInt(), time.month.toInt(), time.day.toInt())
                    endDay =
                        bn.historyLayout.tvEndDay.text.toString().convertDateToLong()
                    getFilterableOrder()
                }, true)

                    .show(it, "tag")
            }

        }
        dialog.maxDate = Calendar.getInstance()
        fragmentManager?.let { dialog.show(it, "date") }
    }

/*override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return CubeAnimation.create(CubeAnimation.RIGHT, enter, 1000)
    }*/


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

                val dialog = CheckDialog(requireContext(), createCheck(), textHtml, utf)
                Timber.d(check!!.html)
                dialog.setOnClickListener {
                    dialog._bn = null
                    dialog.dismiss()

                    bn.groupButtons.translationZ = 0f
                }
                dialog.show()


            } else showSnackMessage(getString(R.string.choose_table))

        }
    }

    private fun createCheck(): String {
        val data = Item(orderAdapter.getOrderList)
        val itemNameList = data.getItemNameList()
        val price = data.getPriceList()


        check = Check(
            "Abdurashid", itemNameList,
            price,
            totalPrice,
            bn.tablesLayout.priceCashBack.text.toString(),
            bn.tablesLayout.priceOnCash.text.toString(), "YAPLIZ MCHJ",
            "NAQD"
        )
        return check!!.html
    }

    private fun loadTables() {
        for (i in 1..20) {
            orderList.add(CashierOrderData(i, "Meal $i", i.toDouble(), "$i", "${i * i}"))
            orderList.add(CashierOrderData(i, "Food $i", i.toDouble(), "$i", "${i * i}"))
        }
        tableAdapter.setOnClickListener { tab ->
            if (!tab.active) {
                presenter.loadOrderByTableId(tab.id, 1)
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
    override fun addTableOrder(objectData: OrderGetData, type: Int) {
        bn.tablesLayout.cashierOrderList.isVisible = true
        bn.tablesLayout.tvEmpty.isVisible = false
        showMessage("id: ${objectData.id}")
        tableOrderId = objectData.id
        orderAdapter.clear()
        isFirst = false
        if (type == 1) {
            totalPrice = objectData.orderPrice.formatDouble()
//            Timber.d("${objectData.orderPrice.formatDouble()}")

            Log.d("OrderByTableId", "$objectData")
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
            orderList.clear()
        } else if (type == 2) {
//            tableOrderId = getData.id
//            isFirst = false
//            tableOrderSize = getData.menuSelection.size
            objectData.menuSelection.forEach {
                orderAdapter2.addProduct(
                    WaiterOrderData(
                        it.menu.id, it.menu.name, it.menu.price,
                        it.count, it.menu.price * it.count
                    )
                )
            }
            totalSum()
        }
    }

    override fun showProgress(isShow: Boolean, type: Int) {
        if (type == 1) {
            bn.tablesLayout.progressBarLoadOrder.visible(isShow)
        } else if (type == 2) {
            bn.togoLayout.togoOrderProgress.visible(isShow)
        } else if (type == 3) {
            bn.historyLayout.historyProgress.visible(isShow)
        }
    }

    override fun getItemsById(list: List<CategoryItemData>) {
        goodsCategoryAdapter.submitList(list)
    }

    override fun getMenu(list: ResData<List<CategoryData>>) {
//        menuList = list.objectData as ArrayList<CategoryData>
        categoryAdapter.submitList(list.objectData)
    }

    //
    override fun clearList(type: Boolean) {
        bn.togoLayout.totalSumTv.text = "0.0"
        bn.togoLayout.tableNumber.text = "0"
        orderAdapter2.clear()
        tableAdapter.notifyDataSetChanged()
        if (type) {
            showSnackMessage("Success")
        }
    }

    override fun totalSum() {
        Timber.d(orderAdapter.itemCount.toString())
        orderAdapter2.getAllOrder().forEach { totalSum += it.productTotalPrice }
        bn.togoLayout.totalSumTv.text = totalSum.formatDouble()
        totalSum = 0.0
    }

    override fun allHistory(orderGetData: List<OrderGetData>) {
        bn.historyLayout.hintViewGroup.visibility = View.VISIBLE
        bn.historyLayout.listHistoryCashier.visibility = View.VISIBLE
        bn.historyLayout.tvEmptyHistory.visibility = View.GONE



        historyList.addAll(orderGetData)
        historyAdapter.clear()
        historyList.sortHistoryByDate()
        historyAdapter.submitList(historyList)

        bn.historyLayout.listHistoryCashier.fadeIn()

    }

    override fun showTables() {

    }

    override fun clearAll() {
        bn.tablesLayout.totalPrice.text = "0"
        bn.tablesLayout.priceOnCash.setText("0")
        bn.tablesLayout.tableNumber.text = "0"
        orderAdapter.clear()

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun submitTables(list: List<TableData>) {
        tableList.clear()
        tableList.addAll(list)
        Timber.d(list.size.toString())
        bn.tableProgress.visibility = View.GONE
        bn.swiperefresh.isRefreshing = false
        if (list.isNotEmpty()) {
            tableAdapter.submitList(list as ArrayList<TableData>)
            bn.tableList.fadeIn()
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

    companion object {
        const val textHtml = "text/html"
        const val utf = "UTF-8"
        val firstMillisecond: Long = currentTimeToLong()
    }


    override fun onTextChanged(text: String) {
        historyAdapter.onSearch(text)
        Log.d("onTextChanged", text)
        bn.historyLayout.listHistoryCashier.fadeIn()
    }
}
