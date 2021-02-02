package com.example.restuarant.ui.waiter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWaiterBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.stringFormat
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.*
import com.example.restuarant.presentation.waiter.WaiterPresenter
import com.example.restuarant.presentation.waiter.WaiterView
import com.example.restuarant.ui.global.BaseFragment
import com.example.restuarant.ui.waiter.adapters.CategoryAdapter
import com.example.restuarant.ui.waiter.adapters.CategoryItemAdapter
import com.example.restuarant.ui.waiter.adapters.OrderAdapter
import com.example.restuarant.ui.waiter.adapters.TableAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class WaiterFragment : BaseFragment(), WaiterView {
    override val layoutRes: Int = R.layout.fragment_waiter

    private var _bn: FragmentWaiterBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")


    private lateinit var itemList:ArrayList<CategoryItemData>

    private var goodsCategoryAdapter = CategoryItemAdapter()
    private var categoryAdapter = CategoryAdapter()
    private var tableAdapter = TableAdapter()
    private var orderAdapter = OrderAdapter()

    private var btnId: Int = 1

    @InjectPresenter
    lateinit var presenter: WaiterPresenter

    @ProvidePresenter
    fun providePresenter(): WaiterPresenter = scope.getInstance(WaiterPresenter::class.java)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentWaiterBinding.bind(view)

        bn.tablesBtn.setBackgroundResource(R.color.red)

        bn.tablesBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 1
            presenter.showTables()
            bn.tablesBtn.setBackgroundResource(R.color.red)
        }

        bn.orderBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 2
            presenter.showMenu()
            bn.orderBtn.setBackgroundResource(R.color.red)
            if (bn.tableNumber.text != "0") {
                bn.tableNumber.text = "0"
            }
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

        val snapHelper1 = LinearSnapHelper()
        snapHelper1.attachToRecyclerView(bn.categoryRv)

        bn.menuRv.adapter = goodsCategoryAdapter
        bn.categoryRv.adapter = categoryAdapter
        bn.tablePageRv.adapter = tableAdapter
        bn.orderRv.adapter = orderAdapter

        tableAdapter.setOnClickListener {
            presenter.showMenu()
            presenter.changeColor()
            btnId = 2
            bn.orderBtn.setBackgroundResource(R.color.red)
            bn.tableNumber.text = it.id.toString()
        }

        bn.btnPrint.setOnClickListener {
            presenter.showProgress()
//            presenter.sendOrder()
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
            val waiterOrderData = WaiterOrderData(it.name, 15000, 1, 15000)
            orderAdapter.addProduct(waiterOrderData)
            presenter.totalSum()
            if (orderAdapter.itemCount!=0) {
                bn.orderRv.smoothScrollToPosition(orderAdapter.itemCount - 1)
            }
//            Timber.d(it.name)
//            val list = orderAdapter.currentList.toMutableList()
//            Timber.d(list.size.toString())
//            list.add(waiterOrderData)
//            Timber.d(list.size.toString())
//            orderAdapter.submitList(list)
        }


    }


    override fun showMessage(message: String) {

    }

    override fun makeLoadingVisible(status: Boolean) {

    }

    override fun openErrorDialog(message: String, status: Boolean) {

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
        categoryAdapter.submitList(list.objectData)
    }

    override fun getTables(list: ResData<List<TableData>>) {
        tableAdapter.submitList(list.objectData)
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
        var sum = 0
        orderAdapter.currentList.toMutableList().forEach {
            sum+=it.productTotalPrice
        }
        bn.totalSumTv.text = sum.toLong().stringFormat()
    }

    override fun showProgress(type: Int, status: Boolean) {
        when(type){
            DI.ORDER_PROGRESS-> bn.waiterOrderProgress.visible(status)
            DI.MENU_ITEMS_PROGRESS-> bn.waiterItemsProgress.visible(status)
            DI.TABLES_PROGRESS-> bn.waiterItemsProgress.visible(status)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

//    private fun loadTableList(): ArrayList<TableData> {
//        tablePageList = ArrayList()
//        for (i in 0..30) {
//            tablePageList.add(TableData(i + 1, "", "", false, i + 1))
//        }
//
//        return tablePageList
//    }

//    private fun loadMenuItems(): ArrayList<CategoryData> {
//        categoryList = ArrayList()
//        for (i in 0..20) {
//            categoryList.add(
//                CategoryData(1,"",
//                    "Palov","Milliy",
//                    loadItems()
//                )
//            )
//        }
//        return categoryList
//    }

//    private fun loadItems():ArrayList<CategoryItemData>{
//        itemList = ArrayList()
//        for (i in 0..30) {
//            itemList.add(CategoryItemData("palov",15000,"milliy",""))
//            itemList.add(CategoryItemData("manti",15000,"uyg'ur",""))
//        }
//        return itemList
//    }

}