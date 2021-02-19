package com.example.restuarant.ui.wareHouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import com.example.restuarant.R
import com.example.restuarant.databinding.AddNewProductBinding
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.presentation.were_house.add_product.AddNewProductPresenter
import com.example.restuarant.presentation.were_house.add_product.AddProductView
import com.example.restuarant.ui.global.BaseWatcher
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import java.lang.NullPointerException

class CreateProductDialogFragment : MvpAppCompatDialogFragment(), AddProductView {
    private var _bn: AddNewProductBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    private var listener: SingleBlock<ProductData>? = null
    private var nullOrErrorListener: ((String, Boolean) -> Unit)? = null

    @InjectPresenter
    lateinit var presenterNew: AddNewProductPresenter

    @ProvidePresenter
    fun providePresenter(): AddNewProductPresenter =
        Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
            .getInstance(AddNewProductPresenter::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_new_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = AddNewProductBinding.bind(view)

        bn.radio1.isChecked = true

        bn.radio2.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            bn.radio2.setOnClickListener {

            }
            bn.radio1.setOnClickListener {

            }
        }

//        if (bn.radio2.isChecked) {
//            bn.sellPrice.visibility = View.VISIBLE
//        } else {
//            bn.sellPrice.visibility = View.GONE
//        }

        bn.btnAdd.setOnClickListener {
            val name = bn.inputProductName.text.toString().trim()
            val sell = if (bn.radio1.isChecked) false else bn.radio2.isChecked
            val type =
                if (bn.radioDona.isChecked) "PIECE" else if (bn.radioKg.isChecked) "KG" else if (bn.radioLitr.isChecked) "LITER" else ""
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

            presenterNew.addNewProduct(
                ProductData(
                    name, type, sell, 0.0, 0.0, 0, 0
                )
            )

        }



        bn.inputProductName.addTextChangedListener(object : BaseWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        bn.btnDismiss.setOnClickListener {
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
        bn.loadingLayout.isClickable = !status
        bn.progressBar.loading.visible(status)
//      bn.progressBar
    }

    override fun openDialog(message: String, status: Boolean) {
        bn.inputProductName.setText("")
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