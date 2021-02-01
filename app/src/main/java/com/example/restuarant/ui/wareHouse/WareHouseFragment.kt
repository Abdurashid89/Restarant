package com.example.restuarant.ui.wareHouse

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWareHouseBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.model.entities.CategoryInProductData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.WareHousePresenter
import com.example.restuarant.presentation.were_house.WareHouseView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class WareHouseFragment() : BaseFragment(), WareHouseView {
    override val layoutRes: Int = R.layout.fragment_ware_house
    private lateinit var binding: FragmentWareHouseBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemList: ArrayList<ProductInData>
    private var adapter = WareHouseAdapter()

    @InjectPresenter
    lateinit var presenter: WareHousePresenter

    @ProvidePresenter
    fun providePresenter(): WareHousePresenter = scope.getInstance(WareHousePresenter::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWareHouseBinding.bind(view)
        layoutManager = LinearLayoutManager(requireContext())
        loadAdapter()
        adapter.submitList(itemList)
        binding.productRv.adapter = adapter

        binding.btnLogOut.setOnClickListener {
            presenter.onBackPressed()
        }

        binding.addNewProduct.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext()).create()
            val view = layoutInflater.inflate(R.layout.item_product, null)
            alertDialog.setView(view)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }
    }


    private fun loadAdapter() {
        itemList = ArrayList()
        var categoryInData =
            CategoryInProductData(0, "aa", "aa", "aa", "aa", "aa", "Aa", 0, false, 0)
        itemList.add(ProductInData(0, "aaaaa", "a", "a", "aa", "go'sht", categoryInData))
        itemList.add(ProductInData(1, "aaaaa", "a", "a", "aa", "saryog'", categoryInData))
        itemList.add(ProductInData(2, "aaaaa", "a", "a", "aa", "non", categoryInData))
        itemList.add(ProductInData(3, "aaaaa", "a", "a", "aa", "suv", categoryInData))
        itemList.add(ProductInData(4, "aaaaa", "a", "a", "aa", "kartoshka", categoryInData))
        itemList.add(ProductInData(5, "aaaaa", "a", "a", "aa", "sabzi", categoryInData))
        itemList.add(ProductInData(6, "aaaaa", "a", "a", "aa", "piyoz", categoryInData))
        itemList.add(ProductInData(7, "aaaaa", "a", "a", "aa", "sholg'om", categoryInData))
        itemList.add(ProductInData(8, "aaaaa", "a", "a", "aa", "gorox", categoryInData))
        itemList.add(ProductInData(9, "aaaaa", "a", "a", "aa", "mosh", categoryInData))
        itemList.add(ProductInData(10, "aaaaa", "a", "a", "aa", "guruch", categoryInData))
        itemList.add(ProductInData(11, "aaaaa", "a", "a", "aa", "loviya", categoryInData))
        itemList.add(ProductInData(12, "aaaaa", "a", "a", "aa", "tovuq go'shti", categoryInData))

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

}