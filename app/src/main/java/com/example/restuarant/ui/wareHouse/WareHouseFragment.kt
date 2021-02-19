package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.view.View
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
        binding.productRv.layoutManager = layoutManager
        binding.productRv.adapter = adapter

        binding.btnLogOut.setOnClickListener {
            presenter.onBackPressed()
        }

        binding.btnEnter.setOnClickListener {
            inputOrOutputProduct(true)
        }

        binding.btnRasxod.setOnClickListener {
            inputOrOutputProduct(false)
        }


        binding.btnAdd.setOnClickListener {
            addProduct()
        }
        binding.btnHistory.setOnClickListener {
            presenter.openHistoryScreen()
        }


    }

    private fun inputOrOutputProduct(type: Boolean) {
        EnterProductDialog(type).show(childFragmentManager, "tag")
    }

    private fun addProduct() {
        CreateProductDialogFragment().show(childFragmentManager, "tag")
    }


    private fun loadAdapter() {

        itemList = ArrayList()
//        itemList.add(ProductInData(0, "aaaaa", 100.0, 100.0, 100, "go'sht"))
//        itemList.add(ProductInData(1, "aaaaa", 100.0, 100.0, 100, "saryog'"))
//        itemList.add(ProductInData(2, "aaaaa", 100.0, 100.0, 100, "non"))
//        itemList.add(ProductInData(3, "aaaaa", 100.0, 100.0, 100, "suv"))
//        itemList.add(ProductInData(4, "aaaaa", 100.0, 100.0, 100, "kartoshka"))
//        itemList.add(ProductInData(5, "aaaaa", 100.0, 100.0, 100, "sabzi"))
//        itemList.add(ProductInData(6, "aaaaa", 100.0, 100.0, 100, "piyoz"))
//        itemList.add(ProductInData(7, "aaaaa", 100.0, 100.0, 100, "sholg'om"))
//        itemList.add(ProductInData(8, "aaaaa", 100.0, 100.0, 100, "gorox"))
//        itemList.add(ProductInData(9, "aaaaa", 100.0, 100.0, 100, "mosh"))
//        itemList.add(ProductInData(10, "aaaaa", 100.0, 100.0, 100, "guruch"))
//        itemList.add(ProductInData(11, "aaaaa", 100.0, 100.0, 100, "loviya"))
//        itemList.add(ProductInData(12, "aaaaa", 100.0, 100.0, 100, "tovuq go'shti"))
//
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {

    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun listProducts(list: List<ProductInData>) {
        adapter.submitList(list)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onRefresh() {

    }

}