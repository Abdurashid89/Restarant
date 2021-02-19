package com.example.restuarant.ui.wareHouse.history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentProductHistoryBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.product_history.HistoryView
import com.example.restuarant.presentation.were_house.product_history.HistoryPresenter
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.lang.NullPointerException

class ProductHistoryFragment : BaseFragment(), HistoryView {
    override val layoutRes: Int = R.layout.fragment_product_history
    private var _bn: FragmentProductHistoryBinding? = null
    private val binding get() = _bn ?: throw NullPointerException("error")
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemList: ArrayList<ProductInData>
    private var adapter = HistoryAdapter()

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter = scope.getInstance(HistoryPresenter::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentProductHistoryBinding.bind(view)
        layoutManager = LinearLayoutManager(requireContext())

        loadAdapter()
        adapter.submitList(itemList)
        binding.productHistoryRv.adapter = adapter

        binding.btnBack.setOnClickListener {
            presenter.onBackPressed()
        }

    }

    private fun loadAdapter() {
        itemList = ArrayList()
//        itemList.add(ProductInData(0, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(1, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(2, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(3, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(4, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(5, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(6, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(7, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(8, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(9, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(10, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(11, "aaaaa", 100.0, 100.0, 100, 10))
//        itemList.add(ProductInData(12, "aaaaa", 100.0, 100.0, 100, 10))

    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun openDialog(message: String, status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun errorOrNull(str: String) {
        TODO("Not yet implemented")
    }

    override fun productYON(status: Boolean, message: String) {
        TODO("Not yet implemented")
    }

}