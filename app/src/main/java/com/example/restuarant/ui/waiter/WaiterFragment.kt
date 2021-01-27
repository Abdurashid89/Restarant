package com.example.restuarant.ui.waiter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWaiterBinding
import com.example.restuarant.model.entities.CategoryData
import com.example.restuarant.model.entities.OrderSendData
import com.example.restuarant.model.entities.TableData
import com.example.restuarant.model.entities.WaiterOrderData
import com.example.restuarant.presentation.waiter.WaiterPresenter
import com.example.restuarant.presentation.waiter.WaiterView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class WaiterFragment : BaseFragment(), WaiterView {
    override val layoutRes: Int = R.layout.fragment_waiter

    private lateinit var binding: FragmentWaiterBinding
    private lateinit var itemList: ArrayList<CategoryData>
    private lateinit var tablePageList: ArrayList<TableData>
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
        binding = FragmentWaiterBinding.bind(view)

        binding.tablesBtn.setBackgroundResource(R.color.red)
        val items = loadMenuItems()
        val tableList = loadTableList()

        binding.tablesBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 1
            presenter.showTables()
            binding.tablesBtn.setBackgroundResource(R.color.red)
        }

          binding.orderBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 2
            presenter.showMenu()
            binding.orderBtn.setBackgroundResource(R.color.red)
              if (binding.tableNumber.text != "0") {
                  binding.tableNumber.text = "0"
              }
        }

        binding.dashboardBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 3
            binding.dashboardBtn.setBackgroundResource(R.color.red)
        }

        binding.exitBtn.setOnClickListener {
            presenter.changeColor()
            btnId = 4
            presenter.onBackPressed()
            binding.exitBtn.setBackgroundResource(R.color.red)
        }

        goodsCategoryAdapter = CategoryItemAdapter()
        goodsCategoryAdapter.submitList(items)
        categoryAdapter = CategoryAdapter()
        categoryAdapter.submitList(itemList)
        tableAdapter = TableAdapter()
        tableAdapter.submitList(tableList)

        tableAdapter.setOnClickListener {
            presenter.showMenu()
            presenter.changeColor()
            btnId = 2
            binding.orderBtn.setBackgroundResource(R.color.red)
            binding.tableNumber.text = it.id.toString()
        }
        orderAdapter = OrderAdapter()
//        orderAdapter.submitList()

        binding.btnPrint.setOnClickListener {
//            val waiterOrderData = WaiterOrderData()
//            val orderSendData = OrderSendData("","","",)
        }

        categoryAdapter.setOnClickListener {

        }

        goodsCategoryAdapter.setOnClickListener {

        }


        val snapHelper1 = LinearSnapHelper()
        snapHelper1.attachToRecyclerView(binding.categoryRv)

        binding.menuRv.adapter = goodsCategoryAdapter

        binding.categoryRv.adapter = categoryAdapter

        binding.tablePageRv.adapter = tableAdapter

        binding.orderRv.adapter = orderAdapter

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
        binding.categoryConstraint.visibility = View.GONE
        binding.menuRv.visibility = View.GONE
        binding.tablePageRv.visibility = View.VISIBLE
    }

    override fun showMenu() {
        binding.categoryConstraint.visibility = View.VISIBLE
        binding.menuRv.visibility = View.VISIBLE
        binding.tablePageRv.visibility = View.GONE
    }

    override fun changeColor() {
        when (btnId) {
            1 -> binding.tablesBtn.setBackgroundResource(R.color.green)
            2 -> binding.orderBtn.setBackgroundResource(R.color.green)
            3 -> binding.dashboardBtn.setBackgroundResource(R.color.green)
            4 -> binding.exitBtn.setBackgroundResource(R.color.green)
        }
    }

    private fun loadTableList(): ArrayList<TableData> {
        tablePageList = ArrayList()
        for (i in 0..30) {
            tablePageList.add(TableData(i + 1, "", "",false,i+1))
        }

        return tablePageList
    }

    private fun loadMenuItems(): ArrayList<CategoryData> {
        itemList = ArrayList()
        for (i in 0..20) {
//            itemList.add(
//                CategoryData(1,"",
//                    "Palov","Milliy",
//
//                )
//            )
        }
        return itemList
    }

//    private fun loadTables(): ArrayList<Int> {
//        tableList = ArrayList()
//        for (i in 0..30) {
//            tableList.add(i)
//        }
//        return tableList
//
//    }


}