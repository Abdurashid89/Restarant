package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.example.restuarant.R
import com.example.restuarant.databinding.ItemProductBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.*
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.add_product.EnterProductPresenter
import com.example.restuarant.presentation.were_house.add_product.EnterProductView
import com.example.restuarant.ui.global.BaseWatcher
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

/**
 * Created by Davronbek on 09,Февраль,2021
 */
class EnterProductDialog(var inputOrOutput: Boolean) : MvpAppCompatDialogFragment(),
    EnterProductView {
    private var _bn: ItemProductBinding? = null
    private val binding get() = _bn ?: throw NullPointerException("error")
    private var listener: SingleBlock<Boolean>? = null
    private var itemList = ArrayList<ProductInData>()
    private var adapter = EnterProductAdapter()
    private var nullOrErrorListener: ((String, Boolean) -> Unit)? = null
    private var productId = -1
    private var isSold = false

    @InjectPresenter
    lateinit var presenterNew: EnterProductPresenter

    @ProvidePresenter
    fun providePresenter(): EnterProductPresenter =
        Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
            .getInstance(EnterProductPresenter::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = ItemProductBinding.bind(view)
//        presenterNew.getProduct()
//        presenterNew.inputProduct(data)
//        loadAdapter()
//        adapter.submitList(itemList)
        //
        binding.tv.text = if (inputOrOutput) "Приход" else "Расход"
        binding.productRv.adapter = adapter
        binding.productRv.visibility = View.GONE
        if (!inputOrOutput) {
            binding.inputProductInComePrice.visibility = View.GONE
            binding.inputProductSellPrice.isVisible = false
        }
        adapter.setOnClickListener {
            isSold = it.sold
            binding.inputProductName.setText(it.name)
            adapter.clear()
            binding.productRv.visibility = View.GONE
            productId = it.id

            when (it.type) {
                Constants.kg -> {
                    binding.productType.text = Constants.kg
                }
                Constants.piece -> {
                    binding.productType.text = Constants.piece
                }
                else -> {
                    binding.productType.text = Constants.liter
                }
            }
            binding.inputProductSellPrice.visible(false)
        }


//        if (bn.radio2.isChecked) {
//           binding.sellPrice.visibility = View.VISIBLE
//        } else {
//           binding.sellPrice.visibility = View.GONE
//        }

        binding.inputProductName.addTextChangedListener(object : BaseWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    searchProduct(s.toString().toLowerCase())
                } else {
                    binding.productRv.visibility = View.GONE
                    adapter.clear()
                }

            }
        })

        binding.btnAdd.setOnClickListener {
            val name = binding.inputProductName.text.toString().trim()
            val weight = binding.inputProductWeight.text.toString().trim()
            val inComePrice = binding.inputProductInComePrice.text.toString()
            val sellPrice = binding.inputProductSellPrice.text.toString()
            customLog("income->$inComePrice sell -> $sellPrice")

            when {
                name.isEmpty() -> {
                    binding.inputProductName.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.shake
                        )
                    )
                    vibrate(requireContext())
                    return@setOnClickListener
                }
                weight.isEmpty() -> {
                    binding.inputProductWeight.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.shake
                        )
                    )
                    vibrate(requireContext())
                    return@setOnClickListener
                }

//                inputOrOutput -> {
//                    if (inComePrice.isEmpty()) {
//                    binding.inputProductInComePrice.startAnimation(
//                        AnimationUtils.loadAnimation(
//                            requireContext(),
//                            R.anim.shake
//                        )
//                    )
//                    vibrate(requireContext())
//                    return@setOnClickListener
//                }
//
//                }
//
//                inputOrOutput -> {
//                    if (sellPrice.isEmpty()) {
//                        binding.inputProductSellPrice.startAnimation(
//                            AnimationUtils.loadAnimation(
//                                requireContext(),
//                                R.anim.shake
//                            )
//                        )
//                        vibrate(requireContext())
//                        return@setOnClickListener
//                    }
//                }
                else -> {
                    val income_price = if (inComePrice.isNotEmpty()) inComePrice else "50"
                    val sell_price = if (sellPrice.isNotEmpty()) sellPrice else "50"
                    presenterNew.inputOrOutput(
                        inputOrOutput,
                        ProductInData(
                            productId,
                            binding.productType.text.toString(),
                            isSold,
                            name,
                            income_price.toInt().toDouble(),
                            sell_price.toInt().toDouble(),
                            weight.toInt().toDouble(),
                            10.0
                        )
                    )
                    showSnackMessage("send")
                }

            }
        }

        binding.btnDismiss.setOnClickListener {
            listener?.invoke(true)
        }
    }

    private fun loadAdapter() {
//        itemList.add(ProductInData(0, "", "a", "a", "aa", "go'sht"))
//        itemList.add(ProductInData(1, "", "a", "a", "aa", "saryog'"))
//        itemList.add(ProductInData(2, "", "a", "a", "aa", "non"))
//        itemList.add(ProductInData(3, "", "a", "a", "aa", "suv"))
//        itemList.add(ProductInData(4, "", "a", "a", "aa", "kartoshka"))
//        itemList.add(ProductInData(5, "", "a", "a", "aa", "sabzi"))
//        itemList.add(ProductInData(6, "", "a", "a", "aa", "piyoz"))
//        itemList.add(ProductInData(7, "", "a", "a", "aa", "sholg'om"))
//        itemList.add(ProductInData(8, "", "a", "a", "aa", "gorox"))
//        itemList.add(ProductInData(9, "", "a", "a", "aa", "mosh"))
//        itemList.add(ProductInData(10, "", "a", "a", "aa", "guruch"))
//        itemList.add(ProductInData(11, "", "a", "a", "aa", "loviya"))
//        itemList.add(ProductInData(12, "", "a", "a", "aa", "tovuq go'shti"))
    }

    private fun searchProduct(newText: String) {
        val ls = ArrayList<ProductInData>()
        binding.productRv.visibility = View.VISIBLE
        itemList.forEach {
            if (it.name.toLowerCase().contains(newText)) {
                ls.add(it)
            }
        }
        adapter.submitList(ls)
    }

    fun setOnCLickListener(block: SingleBlock<Boolean>) {
        listener = block
    }

    override fun showMessage(message: String) {
        customLog(message)
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        binding.loadingLayout.isClickable = !status
        binding.progressBar.loading.visible(status)
//     binding.progressBar
    }

    override fun openDialog(message: String, status: Boolean) {
        binding.inputProductName.setText("")
        binding.inputProductWeight.setText("")
        binding.inputProductInComePrice.setText("")
        binding.inputProductSellPrice.setText("")
        binding.tvTotalPrice.text = ""
    }

    override fun errorOrNull(str: String) {

    }

    override fun productYON(status: Boolean, message: String) {
        TODO("Not yet implemented")
    }

    override fun listProducts(list: List<ProductInData>) {
        if (list.isNotEmpty()) {
            itemList.clear()
            itemList.addAll(list)
            adapter.setOnClickListener {
                binding.inputProductName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_baseline_check_24,
                    0
                )
                binding.inputProductName.setText(it.name)
                binding.productType.text = it.type
                productId = it.id
                binding.productRv.visibility = View.GONE
            }

        } else {
            binding.inputProductName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_error_outline_24,
                0
            )
            binding.productRv.visibility = View.GONE
        }
    }

    override fun clearAllOldData() {
        adapter.clear()
        binding.inputProductName.setText("")
        binding.inputProductWeight.setText("")
        binding.inputProductInComePrice.setText("")
        binding.inputProductSellPrice.setText("")
        productId = -1
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}