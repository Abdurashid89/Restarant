package com.example.restuarant.ui.wareHouse

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWareHouseBinding
import com.example.restuarant.extentions.showSnackMessage
//import com.example.restuarant.model.entities.CategoryInProductData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.WareHousePresenter
import com.example.restuarant.presentation.were_house.WareHouseView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.lang.NullPointerException

class WareHouseFragment() : BaseFragment(), WareHouseView, SwipeRefreshLayout.OnRefreshListener {
    override val layoutRes: Int = R.layout.fragment_ware_house
    private var _bn: FragmentWareHouseBinding? = null
    private val binding get() = _bn ?: throw  NullPointerException("error")
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemList: ArrayList<ProductInData>
    private var adapter = WareHouseAdapter()

    @InjectPresenter
    lateinit var presenter: WareHousePresenter


    @ProvidePresenter
    fun providePresenter(): WareHousePresenter = scope.getInstance(WareHousePresenter::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentWareHouseBinding.bind(view)
        layoutManager = LinearLayoutManager(requireContext())
        loadAdapter()
        adapter.submitList(itemList)
        binding.productRv.adapter = adapter

        binding.btnLogOut.setOnClickListener {
            presenter.onBackPressed()
        }

        binding.btnEnter.setOnClickListener {
            enterProduct()
        }


        binding.btnAdd.setOnClickListener {
            addProduct()
        }
    }

    private fun enterProduct() {
        EnterProductDialog().show(childFragmentManager, "tag")
    }

    private fun addProduct() {
        WareHouseDialogFragment().show(childFragmentManager,"tag")
    }


    private fun loadAdapter() {
        itemList = ArrayList()
        itemList.add(ProductInData(0, "aaaaa", "a", "a", "aa", "go'sht"))
        itemList.add(ProductInData(1, "aaaaa", "a", "a", "aa", "saryog'"))
        itemList.add(ProductInData(2, "aaaaa", "a", "a", "aa", "non"))
        itemList.add(ProductInData(3, "aaaaa", "a", "a", "aa", "suv"))
        itemList.add(ProductInData(4, "aaaaa", "a", "a", "aa", "kartoshka"))
        itemList.add(ProductInData(5, "aaaaa", "a", "a", "aa", "sabzi"))
        itemList.add(ProductInData(6, "aaaaa", "a", "a", "aa", "piyoz"))
        itemList.add(ProductInData(7, "aaaaa", "a", "a", "aa", "sholg'om"))
        itemList.add(ProductInData(8, "aaaaa", "a", "a", "aa", "gorox"))
        itemList.add(ProductInData(9, "aaaaa", "a", "a", "aa", "mosh"))
        itemList.add(ProductInData(10, "aaaaa", "a", "a", "aa", "guruch"))
        itemList.add(ProductInData(11, "aaaaa", "a", "a", "aa", "loviya"))
        itemList.add(ProductInData(12, "aaaaa", "a", "a", "aa", "tovuq go'shti"))

    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {

    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onRefresh() {

    }

}