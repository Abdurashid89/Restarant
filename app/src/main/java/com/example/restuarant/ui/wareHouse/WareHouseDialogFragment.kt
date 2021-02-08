package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.restuarant.R
import com.example.restuarant.databinding.ItemProductBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.add_product.AddProductPresenter
import com.example.restuarant.presentation.were_house.add_product.AddProductView
import com.example.restuarant.ui.global.BaseWatcher
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import java.lang.NullPointerException

class WareHouseDialogFragment : MvpAppCompatDialogFragment(), AddProductView {
    private var _bn: ItemProductBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    private var listener: SingleBlock<ProductData>? = null
    private var nullOrErrorListener: ((String, Boolean) -> Unit)? = null

    @InjectPresenter
    lateinit var presenter: AddProductPresenter

    @ProvidePresenter
    fun providePresenter(): AddProductPresenter =
        Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
            .getInstance(AddProductPresenter::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = ItemProductBinding.bind(view)

        bn.inputProductName.setOnClickListener {
            val name = bn.inputProductName.toString().trim()
            val weight = bn.inputProductWeight.toString().toDouble()
            val price = bn.inputProductPrice.toString().toDouble()

            if (name.isEmpty()) {
                bn.inputProductName.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }
            if (weight.equals(0)) {
                bn.inputProductName.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }
            if (price.equals(0)) {
                bn.inputProductName.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }

            listener?.invoke(ProductData(0, name, weight, price))
        }

        bn.inputProductName.addTextChangedListener(object : BaseWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    fun setOnCLickListener(block: SingleBlock<ProductData>) {
        listener = block
    }

    override fun showMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun makeLoadingVisible(status: Boolean) {
        bn.loadingLayout.isClickable = !status
        bn.progressBar.loading.visible(status)
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

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}