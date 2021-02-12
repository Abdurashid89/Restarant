package com.example.restuarant.ui.waiter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.*
import com.example.restuarant.R
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
import com.romainpiel.shimmer.ShimmerTextView
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
    private var tableId = -1
    private var totalSum = 0.0
    private var btnId: Int = 1

    @InjectPresenter
    lateinit var presenter: WaiterPresenter

    @ProvidePresenter
    fun providePresenter(): WaiterPresenter = scope.getInstance(WaiterPresenter::class.java)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentWaiterBinding.bind(view)
//        shimmer = Shimmer()
//        shimmer.start(bn.waiterName)

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

        bn.tablesBtn.setBackgroundResource(R.color.red)

        bn.tablesBtn.setOnClickListener {
            clearList(false)
            presenter.getTableList()
            presenter.changeColor()
            btnId = 1
            presenter.showTables()
            bn.tablesBtn.setBackgroundResource(R.color.red)
        }

        bn.orderBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 2
            categoryAdapter.submitList(menuList)
            presenter.showMenu()
            categoryAdapter.notifyDataSetChanged()
            bn.orderBtn.setBackgroundResource(R.color.red)
        }

        bn.dashboardBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 3
            bn.dashboardBtn.setBackgroundResource(R.color.red)
        }

        bn.exitBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 4
            presenter.onBackPressed()
            bn.exitBtn.setBackgroundResource(R.color.red)
        }

//        val snapHelper1 = LinearSnapHelper()
//        snapHelper1.attachToRecyclerView(bn.categoryRv)

        tableAdapter.setOnClickListener {
            if (it.active) {
                presenter.showMenu()
                presenter.changeColor()
                btnId = 2
                tableId = it.id
                bn.orderBtn.setBackgroundResource(R.color.red)
                bn.tableNumber.text = it.id.toString()
            } else {
                presenter.orderGetData(it.id)
                presenter.showMenu()
                presenter.changeColor()
                btnId = 2
                tableId = it.id
                bn.orderBtn.setBackgroundResource(R.color.red)
                bn.tableNumber.text = it.id.toString()

            }
        }

        bn.btnPrint.setOnClickListener {
            if (orderAdapter.list.size > 0) {
                orderList = ArrayList()
                orderAdapter.list.toMutableList().forEach {
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
                presenter.showProgress()
            } else {
                showSnackMessage("Avval mahsulot tanlang!")
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
            val waiterOrderData = WaiterOrderData(it.id, it.name, it.price, 1, it.price)
            if (orderAdapter.list.isEmpty()) {
                orderAdapter.addProduct(waiterOrderData)
            } else {
                var isHave = false
                for (i in orderAdapter.list.indices) {
                    if (waiterOrderData.id == orderAdapter.list[i].id) {
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
        }
    }


    override fun showMessage(message: String) {
        showSnackMessage(message)
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
        menuList = ArrayList()
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
        orderAdapter.list.forEach {
            totalSum += it.productTotalPrice
        }

//        val split = totalSum.toString().split(".")
//        val s = split[0].toLong().stringFormat() + ".${split[1]}"
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
        orderAdapter.list.clear()
        tableAdapter.notifyDataSetChanged()
        if (type) {
            showSnackMessage("Success")
        }

    }

    override fun getOrderInfo(getData: OrderGetData) {
        getData.menuSelection.forEach {
           orderAdapter.addProduct(WaiterOrderData(it.menu.id,it.menu.name,it.menu.price,
           it.count,it.menu.price*it.count))
        }
        totalSum()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    override fun onPause() {
        super.onPause()
//        shimmer.cancel()
    }
}