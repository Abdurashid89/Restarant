package com.example.restuarant.presentation.product

import com.example.restuarant.Screens
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.GetResponseData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.model.interactor.ProductIntegrator
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProductPresenter @Inject constructor(
    private val router: FlowRouter,
    private val integrator: ProductIntegrator
) : BasePresenter<ProductView>() {
    private var page = 0
    private var size = 20
    private var totalElements = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addPage(true)
    }

    fun addPage(status: Boolean) {
        if (status) {
            page = 0
            getAllProduct(page, size)
        } else {
            val maxPage = if (totalElements % size == 0) {
                totalElements / size
            } else
                totalElements / size + 1
            if (page + 1 < maxPage) {
                page++
                getAllProduct(page, size)
            }
        }
    }

    private fun getAllProduct(page: Int, size: Int) {
        integrator.getAllProduct(page, size)
            .doOnSubscribe {
                viewState.visibleProgressBar(true)
            }.doAfterTerminate {
                viewState.visibleProgressBar(false)
            }
            .subscribe({
                onSuccessGetAllProduct(it)
            }, {
                viewState.openErrorDialog(errorResponse(it), false)
            }).connect()
    }

    private fun onSuccessGetAllProduct(data: GetResponseData<List<ProductInData>>) {
        if (data != null) {
            totalElements = data.totalElements
            val activeList = ArrayList<ProductInData>()
            data.objectData.forEach {
                if (it.active) activeList.add(it)
            }
            viewState.listProduct(activeList)
        }
    }

    fun onBackPressed() {
        router.exit()
    }

    fun openAddProduct() {
        router.newChain(Screens.AddProductPage)
    }

    fun openItemDialog(item: ProductInData) {
        viewState.openProductItemDialog(item)
    }

}