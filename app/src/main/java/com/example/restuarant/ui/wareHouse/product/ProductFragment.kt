package com.example.restuarant.ui.wareHouse.product

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentProductBinding
import com.example.restuarant.extentions.customDialog
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.product.ProductPresenter
import com.example.restuarant.presentation.product.ProductView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ProductFragment() : BaseFragment(), ProductView, SwipeRefreshLayout.OnRefreshListener {
    override val layoutRes: Int = R.layout.fragment_product
    private lateinit var adapter: ProductAdapter

    private lateinit var binding: FragmentProductBinding
    private lateinit var layoutManager: LinearLayoutManager

    @InjectPresenter
    lateinit var presenter: ProductPresenter

    @ProvidePresenter
    fun providePresenter(): ProductPresenter =
        scope.getInstance(ProductPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)
        adapter = ProductAdapter()
        binding.toolbarProduct.setNavigationOnClickListener {
            presenter.onBackPressed()
        }

        setUpRecylerView()
        scrollListener()
        binding.swipeRefreshProduct.setOnRefreshListener(this)

        binding.btnAddProduct.setOnClickListener {
            presenter.openAddProduct()
        }
    }

    private fun scrollListener() {
        binding.listProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    presenter.addPage(false)
                }
                super.onScrolled(recyclerView, dx, dy)
            }

        })
    }

    private fun setUpRecylerView() {
        layoutManager = LinearLayoutManager(requireContext())
        binding.listProduct.layoutManager = layoutManager
        binding.listProduct.adapter = adapter
    }


    override fun onRefresh() {
        adapter.submitList(null)
        presenter.addPage(true)
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        binding.loadingLayoutProduct.isClickable = !status
        binding.progressBarProduct.loading.visible(status)
    }

    override fun visibleProgressBar(status: Boolean) {
        binding.progressBar.visible(status)
    }

    override fun openErrorDialog(message: String, status: Boolean) {
        customDialog(message, status)
    }

    override fun openProductItemDialog(item: ProductInData) {
        ProductItemDialog(requireContext(), item)
    }

    override fun listProduct(list: List<ProductInData>) {
        if (binding.swipeRefreshProduct.isRefreshing) {
            binding.swipeRefreshProduct.isRefreshing = false
        }
        val ls = adapter.currentList.toMutableList()
        val data = ls
        val status = data.removeAll(list)
        if (!status) {
            ls.addAll(list)
            adapter.submitList(ls)
        }
    }

    override fun productItem(item: ProductInData) {
        presenter.openItemDialog(item)
    }

    override fun responseError() {
        if (binding.swipeRefreshProduct.isRefreshing) {
            binding.swipeRefreshProduct.isRefreshing = false
        }
    }
}