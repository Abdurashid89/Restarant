package com.example.restuarant.ui.waiter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWaiterBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.*
import com.example.restuarant.model.entities.*
import com.example.restuarant.presentation.waiter.WaiterPresenter
import com.example.restuarant.presentation.waiter.WaiterView
import com.example.restuarant.ui.global.BaseFragment
import com.example.restuarant.ui.waiter.adapters.CategoryAdapter
import com.example.restuarant.ui.waiter.adapters.CategoryItemAdapter
import com.example.restuarant.ui.waiter.adapters.OrderAdapter
import com.example.restuarant.ui.waiter.adapters.TableAdapter
import com.example.restuarant.ui.waiter.callback.SwipeToDeleteCallback
import com.romainpiel.shimmer.Shimmer
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber


class WaiterFragment : BaseFragment(), WaiterView {
    override val layoutRes: Int = R.layout.fragment_waiter

    private var _bn: FragmentWaiterBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")

    // ## list ##
    private var menuList = ArrayList<CategoryData>()
    private var orderList = ArrayList<MenuSelect>()

    // ## adapters ##
    private var goodsCategoryAdapter = CategoryItemAdapter()
    private var categoryAdapter = CategoryAdapter()
    private var tableAdapter = TableAdapter()
    private var orderAdapter = OrderAdapter()

    // ## variable ##
    private var shimmer = Shimmer()
    private var tableOrderId = -1L
    private var tableId = -1
    private var totalSum = 0.0
    private var btnId: Int = 1
    private var isFirst = true

    @InjectPresenter
    lateinit var presenter: WaiterPresenter

    @ProvidePresenter
    fun providePresenter(): WaiterPresenter = scope.getInstance(WaiterPresenter::class.java)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentWaiterBinding.bind(view)
        shimmer.setRepeatCount(4)
            .setDuration(1000)
            .setStartDelay(1000).direction = Shimmer.ANIMATION_DIRECTION_LTR
        shimmer.start(bn.waiterName)

        // ## adapters ##
        bn.menuRv.adapter = goodsCategoryAdapter
        bn.categoryRv.adapter = categoryAdapter
        bn.tablePageRv.adapter = tableAdapter
        bn.orderRv.adapter = orderAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(bn.tablePageRv)

        bn.orderRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        val swipeHelper = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = bn.orderRv.adapter as OrderAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                totalSum()
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(bn.orderRv)

        bn.tablesBtn.setBackgroundResource(R.color.teal_1000)

        bn.tablesBtn.setOnClickListener {
            clearList(false)
            presenter.getTableList()
            presenter.changeColor()
            btnId = 1
            presenter.showTables()
            bn.tablesBtn.setBackgroundResource(R.color.teal_1000)
        }

        bn.orderBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 2
            categoryAdapter.submitList(menuList)
            presenter.showMenu()
            categoryAdapter.notifyDataSetChanged()
            bn.orderBtn.setBackgroundResource(R.color.teal_1000)
        }

        bn.dashboardBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 3
            bn.dashboardBtn.setBackgroundResource(R.color.teal_1000)
        }

        bn.exitBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 4
            presenter.onBackPressed()
            bn.exitBtn.setBackgroundResource(R.color.teal_1000)
        }

//        val snapHelper1 = LinearSnapHelper()
//        snapHelper1.attachToRecyclerView(bn.categoryRv)

        tableAdapter.setOnClickListener {
            if (it.active) {
                isFirst = true
                presenter.showMenu()
                presenter.changeColor()
                btnId = 2
                tableId = it.id
                bn.orderBtn.setBackgroundResource(R.color.teal_1000)
                bn.tableNumber.text = it.name.toString()
            } else {
                orderAdapter.clear()
                presenter.orderGetData(it.id)
                presenter.showMenu()
                presenter.changeColor()
                btnId = 2
                tableId = it.id
                bn.orderBtn.setBackgroundResource(R.color.teal_1000)
                bn.tableNumber.text = it.name.toString()

            }
        }

        bn.btnPrint.setOnClickListener {
            if (isFirst) {
                orderList.clear()
                if (orderAdapter.getAllOrder().isNotEmpty()) {
                    orderAdapter.getAllOrder().forEach {
                        orderList.add(MenuSelect(it.productCount, it.id))
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
                            orderList,
                            1
                        )
                    )
                } else {
                    showSnackMessage("Avval mahsulot tanlang!")
                }
            } else {
                orderList.clear()
                if (orderAdapter.getAllOrder().isNotEmpty()) {
                    orderAdapter.getAllOrder().forEach {
                        orderList.add(MenuSelect(it.productCount, it.id))
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
                        orderList,
                        1
                    )
                )
            }
        }

        categoryAdapter.setOnClickListener {
            presenter.getMenuItems(it.id)
        }

        orderAdapter.setOnPlusClickListener {
            orderAdapter.plus(it)
            presenter.totalSum()
        }

        orderAdapter.setOnMinusClickListener {
            orderAdapter.minus(it)
            presenter.totalSum()
        }

        goodsCategoryAdapter.setOnClickListener {
            if (bn.tableNumber.text != "0") {
                val waiterOrderData = WaiterOrderData(it.id, it.name, it.price, 1, it.price)
                if (orderAdapter.getAllOrder().isEmpty()) {
                    orderAdapter.addProduct(waiterOrderData)
                } else {
                    var isHave = false
                    for (i in orderAdapter.getAllOrder().indices) {
                        if (waiterOrderData.id == orderAdapter.getAllOrder()[i].id) {
                            orderAdapter.plus(i)
                            isHave = true
                        }
                    }
                    if (!isHave) orderAdapter.addProduct(waiterOrderData)
                }
                presenter.totalSum()
                if (orderAdapter.itemCount != 0) {
                    bn.orderRv.smoothScrollToPosition(orderAdapter.itemCount - 1)
                }
            } else {
                showSnackMessage("Avval table tanlang!")
            }

        }
    }


    override fun showMessage(message: String) {
        showSnackMessage(message)
        customLog(message)
    }

    override fun makeLoadingVisible(status: Boolean) {

    }

    override fun openErrorDialog(message: String, status: Boolean) {
        customDialog(message, status)
    }

    override fun openClientCountDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.guest_count, null, false)
        dialog.setView(view)
        dialog.show()
    }

    override fun showTables() {
        bn.categoryConstraint.visibility = View.GONE
        bn.menuRv.visibility = View.GONE
        bn.tablePageRv.visibility = View.VISIBLE
    }

    override fun showMenu() {
        bn.categoryConstraint.visibility = View.VISIBLE
        bn.menuRv.visibility = View.VISIBLE
        bn.tablePageRv.visibility = View.GONE
    }

    override fun getMenu(list: ResData<List<CategoryData>>) {
        menuList = list.objectData as ArrayList<CategoryData>
        categoryAdapter.submitList(list.objectData)
    }

    override fun getTables(list: List<TableData>) {
        tableAdapter.submitList(list)
    }

    override fun getItemsById(list: List<CategoryItemData>) {
        goodsCategoryAdapter.submitList(list)
    }

    override fun changeColor() {
        when (btnId) {
            1 -> bn.tablesBtn.setBackgroundResource(R.color.green)
            2 -> bn.orderBtn.setBackgroundResource(R.color.green)
            3 -> bn.dashboardBtn.setBackgroundResource(R.color.green)
            4 -> bn.exitBtn.setBackgroundResource(R.color.green)
        }
    }

    override fun totalSum() {
        Timber.d(orderAdapter.itemCount.toString())
        orderAdapter.getAllOrder().forEach { totalSum += it.productTotalPrice }
        bn.totalSumTv.text = totalSum.formatDouble()
        totalSum = 0.0
    }

    override fun showProgress(type: Int, status: Boolean) {
        when (type) {
            DI.ORDER_PROGRESS -> bn.waiterOrderProgress.visible(status)
            DI.MENU_ITEMS_PROGRESS -> bn.waiterItemsProgress.visible(status)
            DI.TABLES_PROGRESS -> bn.waiterItemsProgress.visible(status)
        }
    }

    override fun clearList(type: Boolean) {
        bn.totalSumTv.text = "0.0"
        bn.tableNumber.text = "0"
        orderAdapter.clear()
        orderList.clear()
        tableAdapter.notifyDataSetChanged()
        if (type) {
            showSnackMessage("Success")
        }

    }

    override fun getOrderInfo(getData: OrderGetData) {
        Log.e("SSS","${getData.menuSelection}")
        tableOrderId = getData.id
        isFirst = false
        getData.menuSelection.forEach {
            orderAdapter.addProduct(
                WaiterOrderData(
                    it.menu.id, it.menu.name, it.menu.price, it.count, it.menu.price * it.count
                )
            )
        }
        totalSum()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}