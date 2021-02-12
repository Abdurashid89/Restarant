package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.restuarant.R
import com.example.restuarant.databinding.ItemProductBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.add_product.EnterProductPresenter
import com.example.restuarant.presentation.were_house.add_product.EnterProductView
import com.example.restuarant.ui.global.BaseWatcher
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import java.lang.NullPointerException

/**
 * Created by Davronbek on 09,Февраль,2021
 */
class EnterProductDialog : MvpAppCompatDialogFragment(), EnterProductView {
    private var _bn: ItemProductBinding? = null
    private val binding get() = _bn ?: throw NullPointerException("error")
    private var listener: SingleBlock<ProductData>? = null
    private lateinit var itemList: ArrayList<ProductInData>
    private var adapter = EnterProductAdapter()
    private var nullOrErrorListener: ((String, Boolean) -> Unit)? = null

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
        loadAdapter()
        adapter.submitList(itemList)
        binding.productRv.adapter = adapter

        adapter.setOnClickListener {
            binding.inputProductName.setText(it.name)
            binding.productRv.visibility = View.GONE
            adapter.submitList(null)
        }


//        if (bn.radio2.isChecked) {
//           binding.sellPrice.visibility = View.VISIBLE
//        } else {
//           binding.sellPrice.visibility = View.GONE
//        }

        binding.inputProductName.addTextChangedListener(object : BaseWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                if (s.toString().isNotEmpty()) {
                    searchProduct(s.toString().toLowerCase())

                } else {
                    binding.productRv.visibility = View.GONE
                }

            }
        })

        binding.btnAdd.setOnClickListener {
            val name = binding.inputProductName.text.toString().trim()
            val weight = binding.inputProductWeight.text.toString()
            val inComePrice = binding.inputProductInComePrice.text.toString()
            val sellPrice = binding.inputProductSellPrice.text.toString()

            if (name.isEmpty()) {
                binding.inputProductName.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }
            if (weight == "") {
                binding.inputProductWeight.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }
            if (inComePrice == "") {
                binding.inputProductInComePrice.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }

            if (sellPrice == "") {
                binding.inputProductSellPrice.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }


        }

        binding.btnDismiss.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun loadAdapter() {
        itemList = ArrayList()
        itemList.add(ProductInData(0, "", "a", "a", "aa", "go'sht"))
        itemList.add(ProductInData(1, "", "a", "a", "aa", "saryog'"))
        itemList.add(ProductInData(2, "", "a", "a", "aa", "non"))
        itemList.add(ProductInData(3, "", "a", "a", "aa", "suv"))
        itemList.add(ProductInData(4, "", "a", "a", "aa", "kartoshka"))
        itemList.add(ProductInData(5, "", "a", "a", "aa", "sabzi"))
        itemList.add(ProductInData(6, "", "a", "a", "aa", "piyoz"))
        itemList.add(ProductInData(7, "", "a", "a", "aa", "sholg'om"))
        itemList.add(ProductInData(8, "", "a", "a", "aa", "gorox"))
        itemList.add(ProductInData(9, "", "a", "a", "aa", "mosh"))
        itemList.add(ProductInData(10, "", "a", "a", "aa", "guruch"))
        itemList.add(ProductInData(11, "", "a", "a", "aa", "loviya"))
        itemList.add(ProductInData(12, "", "a", "a", "aa", "tovuq go'shti"))
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

    fun setOnCLickListener(block: SingleBlock<ProductData>) {
        listener = block
    }

    override fun showMessage(message: String) {
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

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}