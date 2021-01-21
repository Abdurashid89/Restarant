package com.example.restuarant.ui.waiter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWaiterBinding
import com.example.restuarant.model.entities.CategoryData
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
    private var goodsCategoryAdapter: CategoryItemAdapter? = null
    private var deskAdapter: DeskAdapter? = null
    private var categoryAdapter:CategoryAdapter? = null
    private var tableadapter:TableAdapter? = null



    @InjectPresenter
    lateinit var presenter: WaiterPresenter

    @ProvidePresenter
    fun providePresenter(): WaiterPresenter = scope.getInstance(WaiterPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWaiterBinding.bind(view)

        val items = loadMenuItems()
        val tables = loadTables()



        binding.exitBtn.setOnClickListener {
            presenter.onBackPressed()
        }

        binding.tablesBtn.setOnClickListener {
            presenter.showTables()
        }

        binding.orderBtn.setOnClickListener {
            presenter.showMenu()
        }

        goodsCategoryAdapter = CategoryItemAdapter()
        goodsCategoryAdapter!!.submitList(items)
        categoryAdapter = CategoryAdapter()
        categoryAdapter!!.submitList(itemList)
        tableadapter = TableAdapter()
        tableadapter!!.submitList(tables)
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


}