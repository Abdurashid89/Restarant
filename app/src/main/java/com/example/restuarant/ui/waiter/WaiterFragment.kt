package com.example.restuarant.ui.waiter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWaiterBinding
import com.example.restuarant.model.entities.CategoryData
import com.example.restuarant.model.entities.TableData
import com.example.restuarant.presentation.waiter.WaiterPresenter
import com.example.restuarant.presentation.waiter.WaiterView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class WaiterFragment : BaseFragment(), WaiterView {
    override val layoutRes: Int = R.layout.fragment_waiter

    private lateinit var binding: FragmentWaiterBinding
    private lateinit var menuList: ArrayList<CategoryData>
    private lateinit var itemList: ArrayList<CategoryData>
    private lateinit var tableList: ArrayList<Int>
    private lateinit var tablePageList: ArrayList<TableData>
    private var goodsCategoryAdapter: CategoryItemAdapter? = null
    private var deskAdapter: DeskAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var tableadapter: TableAdapter? = null
    private var changeColor: Int = 0


    @InjectPresenter
    lateinit var presenter: WaiterPresenter

    @ProvidePresenter
    fun providePresenter(): WaiterPresenter = scope.getInstance(WaiterPresenter::class.java)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWaiterBinding.bind(view)

        val items = loadMenuItems()
        val tables = loadTables()
        val tableList = loadTableList()


        binding.exitBtn.setOnClickListener {
            changeColor = 1
            presenter.onBackPressed()
        }

        binding.tablesBtn.setOnClickListener {
            binding.tablesBtn.setBackgroundColor(R.color.green)
            presenter.showTables()
        }

        binding.orderBtn.setOnClickListener {
            presenter.showMenu()
        }

        binding.dashboardBtn.setOnClickListener {

        }

        goodsCategoryAdapter = CategoryItemAdapter()
        goodsCategoryAdapter!!.submitList(items)
        categoryAdapter = CategoryAdapter()
        categoryAdapter!!.submitList(itemList)
        tableadapter = TableAdapter()
        tableadapter!!.submitList(tableList)
        deskAdapter = DeskAdapter(tables, object : DeskAdapter.OnDeskItemClickListener {
            override fun onClick() {
                Toast.makeText(requireContext(), "${tables.size}", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick() {

            }

        })

        categoryAdapter!!.setOnClickListener {

        }


        val snapHelper1 = LinearSnapHelper()
        snapHelper1.attachToRecyclerView(binding.categoryRv)

        binding.menuRv.adapter = goodsCategoryAdapter

        binding.tablesRv.adapter = deskAdapter

        binding.categoryRv.adapter = categoryAdapter

        binding.tablePageRv.adapter = tableadapter

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
        binding.tablesConstraint.visibility = View.GONE
    }

    override fun showMenu() {
        binding.categoryConstraint.visibility = View.VISIBLE
        binding.menuRv.visibility = View.VISIBLE
        binding.tablesConstraint.visibility = View.VISIBLE
        binding.tablePageRv.visibility = View.GONE
    }

    private fun loadTableList(): ArrayList<TableData> {
        tablePageList = ArrayList()
        tablePageList.add(TableData(1, true, 4))
        tablePageList.add(TableData(2, true, 4))
        tablePageList.add(TableData(3, true, 4))
        tablePageList.add(TableData(4, true, 4))
        tablePageList.add(TableData(5, true, 4))
        tablePageList.add(TableData(6, true, 4))
        tablePageList.add(TableData(7, true, 4))
        tablePageList.add(TableData(8, true, 4))
        tablePageList.add(TableData(9, false, 4))
        tablePageList.add(TableData(10, false, 4))
        tablePageList.add(TableData(11, true, 4))
        tablePageList.add(TableData(12, true, 4))
        tablePageList.add(TableData(13, true, 4))
        tablePageList.add(TableData(14, true, 4))
        tablePageList.add(TableData(15, false, 4))
        tablePageList.add(TableData(16, true, 4))
        tablePageList.add(TableData(17, true, 4))
        tablePageList.add(TableData(18, true, 4))
        tablePageList.add(TableData(19, true, 4))
        tablePageList.add(TableData(20, true, 4))
        tablePageList.add(TableData(21, true, 4))
        tablePageList.add(TableData(22, false, 4))
        tablePageList.add(TableData(23, true, 4))
        tablePageList.add(TableData(24, true, 4))
        tablePageList.add(TableData(25, true, 4))
        tablePageList.add(TableData(26, true, 4))
        tablePageList.add(TableData(27, true, 4))
        tablePageList.add(TableData(28, true, 4))
        tablePageList.add(TableData(29, true, 4))
        tablePageList.add(TableData(30, true, 4))
        return tablePageList
    }

    private fun loadMenuItems(): ArrayList<CategoryData> {
        itemList = ArrayList()
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 15000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 35000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 45000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 55000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 65000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 75000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 85000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 95000, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))
        itemList.add(CategoryData("Palov", R.drawable.ic_launcher_background, 25500, "Milliy"))

        return itemList
    }

    private fun loadTables(): ArrayList<Int> {
        tableList = ArrayList()
        tableList.add(1)
        tableList.add(2)
        tableList.add(3)
        tableList.add(4)
        tableList.add(5)
        tableList.add(6)
        tableList.add(7)
        tableList.add(8)
        tableList.add(9)
        tableList.add(10)
        tableList.add(11)
        tableList.add(12)
        tableList.add(13)
        tableList.add(14)
        tableList.add(15)
        tableList.add(16)
        tableList.add(17)
        tableList.add(18)
        tableList.add(19)
        tableList.add(20)
        tableList.add(21)
        tableList.add(22)
        tableList.add(23)
        tableList.add(24)
        tableList.add(25)
        tableList.add(26)
        tableList.add(27)
        tableList.add(28)
        tableList.add(29)
        tableList.add(30)
        return tableList

    }

    private fun changeBtnColor() {

    }
}