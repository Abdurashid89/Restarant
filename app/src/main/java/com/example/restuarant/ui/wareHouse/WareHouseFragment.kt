package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWareHouseBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.visible
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
        binding.productRv.layoutManager = layoutManager
        binding.productRv.adapter = adapter

        binding.swiperefresh.setOnRefreshListener(this)
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
        val dialog = EnterProductDialog(type)
        dialog.setOnCLickListener {
            presenter.getAllProduct()
            dialog.dismiss()
        }
            dialog.show(childFragmentManager, "tag")
    }

    private fun addProduct() {
        val dialog = CreateProductDialogFragment()
        dialog.setOnCLickListener {
            presenter.getAllProduct()
            dialog.dismiss()
        }
            dialog.show(childFragmentManager, "tag")
    }


    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        binding.progress.visible(status)
    }

    override fun openErrorDialog(message: String, status: Boolean) {
        binding.swiperefresh.isRefreshing = false
    }

    override fun listProducts(list: List<ProductInData>) {
        binding.swiperefresh.isRefreshing = false
        if(list.isNotEmpty()){
            adapter.submitList(null)
            adapter.submitList(list)
            binding.notProduct.visible(false)
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onRefresh() {
        presenter.getAllProduct()
    }

}