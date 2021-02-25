package com.example.restuarant.ui.wareHouse.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentProductHistoryBinding
import com.example.restuarant.extentions.CustomWatcher
import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.ProductHistoryData
import com.example.restuarant.presentation.were_house.product_history.HistoryPresenter
import com.example.restuarant.presentation.were_house.product_history.HistoryView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ProductHistoryFragment : BaseFragment(), HistoryView, ITextWatcher {
    override val layoutRes: Int = R.layout.fragment_product_history
    private var _bn: FragmentProductHistoryBinding? = null
    private val binding get() = _bn ?: throw NullPointerException("error")
    private lateinit var layoutManager: LinearLayoutManager
    private var isInput = true

    //    private var data = ProductData()
    private lateinit var itemList: ArrayList<ProductHistoryData>
    lateinit var adapter: HistoryAdapter

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter = scope.getInstance(HistoryPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentProductHistoryBinding.bind(view)
        layoutManager = LinearLayoutManager(requireContext())
        binding.productHistoryRv.layoutManager = layoutManager
        itemList = ArrayList()
        loadAdapter()


        binding.btnBack.setOnClickListener {
            presenter.onBackPressed()
        }

        binding.btnIncome.setOnClickListener {
            isInput = true
            inputOrOutput(isInput)
        }
        binding.btnSell.setOnClickListener {
            isInput = false
            inputOrOutput(isInput)
        }

        binding.productSearch.addTextChangedListener(CustomWatcher(this))
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

    @SuppressLint("SetTextI18n")
    private fun inputOrOutput(type: Boolean) {
        adapter = HistoryAdapter(type)
//        adapter.submitList(itemList)

        adapter.setOnFoundListener {
            binding.tvNotFound.visible(!it)
            showMessage(it.toString())
        }

        binding.productHistoryRv.adapter = adapter
        if (type) {
            binding.btnIncome.alpha = 0.5f
            binding.btnSell.alpha = 1f
//            binding.btnIncome.setBackgroundResource(R.color.green)
//            binding.btnSell.setBackgroundResource(R.color.green)
            binding.tvCount.text = "Input count"
            binding.tvProductPrice.text = "Price"
            binding.tvAllProductPrice.text = "All price"
            presenter.inputProductHistory()
        } else {
            binding.tvCount.text = "Output count"
            binding.tvProductPrice.text = "Price"
            binding.tvAllProductPrice.text = "All price"
            binding.btnIncome.alpha = 1f
            binding.btnSell.alpha = 0.5f
            presenter.outputProductHistory()
//            binding.btnIncome.setBackgroundResource(R.color.white)
//            binding.btnSell.setBackgroundResource(R.color.green)
        }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
//        TODO("Not yet implemented")
    }

    override fun openDialog(message: String, status: Boolean) {
//        TODO("Not yet implemented")
    }

    override fun errorOrNull(str: String) {
//        TODO("Not yet implemented")
    }

    override fun productYON(status: Boolean, message: String) {
//        TODO("Not yet implemented")
    }

    override fun listProducts(list: List<ProductHistoryData>) {
        if (list.isNotEmpty()) {
            if (isInput) {
                adapter = HistoryAdapter((isInput))
                binding.productHistoryRv.adapter = adapter
            }
            itemList.clear()
            itemList.addAll(list)
            adapter.clear()
            adapter.submitList(itemList)
            binding.tvNotFound.visibility = View.GONE
        } else {
            binding.tvNotFound.visibility = View.VISIBLE
        }
    }

    override fun onTextChanged(text: String) {
        val list = ArrayList<ProductHistoryData>()

        itemList.forEach {
            if (it.name.toLowerCase().contains(text.toLowerCase())){
                list.add(it)
            }
        }

        if (list.isEmpty()){
            adapter.clear()
            binding.tvNotFound.visibility = View.VISIBLE
        } else {
            adapter.clear()
            adapter.submitList(list)
            binding.tvNotFound.visibility = View.GONE
        }

      /*  itemList.forEach {
            if (!it.name.contains(text)) {
                binding.tvNotFound.visibility = View.VISIBLE
            //    binding.productHistoryRv.visibility = View.GONE
            } else {
                adapter.submitList(itemList)
                adapter.onSearch(text)
                binding.tvNotFound.visibility = View.GONE
                binding.productHistoryRv.visibility = View.VISIBLE
            }
        }*/
    }

}