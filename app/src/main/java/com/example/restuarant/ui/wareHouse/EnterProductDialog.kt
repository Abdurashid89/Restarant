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
import com.example.restuarant.presentation.were_house.add_product.AddNewProductPresenter
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


//        if (bn.radio2.isChecked) {
//           binding.sellPrice.visibility = View.VISIBLE
//        } else {
//           binding.sellPrice.visibility = View.GONE
//        }

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



        binding.inputProductName.addTextChangedListener(object : BaseWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.btnDismiss.setOnClickListener {
            dialog?.dismiss()
        }
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
        TODO("Not yet implemented")
    }

    override fun productYON(status: Boolean, message: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}