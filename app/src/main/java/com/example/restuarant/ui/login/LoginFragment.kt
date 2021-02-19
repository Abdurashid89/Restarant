package com.example.restuarant.ui.login

import android.os.Bundle
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.PinLockViewBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.presentation.login.LoginPresenter
import com.example.restuarant.presentation.login.LoginView
import com.example.restuarant.ui.cashier.check.CookerCheckData
import com.example.restuarant.ui.cashier.check.CookerCheckDialog
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import kotlin.collections.ArrayList

class LoginFragment : BaseFragment(), LoginView {
    override val layoutRes: Int = R.layout.pin_lock_view

    private var _bn: PinLockViewBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")


    private val unPaidList = ArrayList<OrderGetData>()
    private val unPaidListOld = ArrayList<OrderGetData>()

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter =
        scope.getInstance(LoginPresenter::class.java)


    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = PinLockViewBinding.bind(view)


        val arrayDot = ArrayList<Boolean>()
        for (i in 0..3) {
            arrayDot.add(false)
        }

        val numbers = ArrayList<String>()
        for (i in 1..9) {
            numbers.add("$i")
        }
        numbers.add("0")


        val empty = R.drawable.ic_baseline_fiber
        val fill = R.drawable.ic_baseline_fiber_manual_black

        var text_pin = ""
        for (i in 0 until bn.keypatGroup.childCount - 1) {
            bn.keypatGroup.getChildAt(i).setOnClickListener {
                bn.btnDelete.animate().setDuration(150).alpha(1f)
                for (k in 0..3) {
                    if (!arrayDot[k]) {
                        text_pin += numbers[i]
                        bn.dotGroup.getChildAt(k).setBackgroundResource(fill)
                        arrayDot[k] = true
                        break

                    }
                }
                if (text_pin.length == 4) {
                    presenter.openScreen(text_pin)
                }
            }
        }
        bn.btnDelete.setOnClickListener {

            when {
                arrayDot[3] -> {
                    bn.dotGroup.getChildAt(3).setBackgroundResource(empty)
                    arrayDot[3] = false
                    text_pin = text_pin.substring(0, 3)
                    return@setOnClickListener
                }
                arrayDot[2] -> {
                    bn.dotGroup.getChildAt(2).setBackgroundResource(empty)
                    arrayDot[2] = false
                    text_pin = text_pin.substring(0, 2)
                    return@setOnClickListener
                }
                arrayDot[1] -> {
                    bn.dotGroup.getChildAt(1).setBackgroundResource(empty)
                    arrayDot[1] = false
                    text_pin = text_pin.substring(0, 1)
                    return@setOnClickListener
                }
                arrayDot[0] -> {
                    bn.dotGroup.getChildAt(0).setBackgroundResource(empty)
                    arrayDot[0] = false
                    text_pin = ""
                    bn.btnDelete.animate().setDuration(100).alpha(0f)
                    return@setOnClickListener
                }
            }


        }
        val ls = ArrayList<CookerCheckData>()
        ls.add(CookerCheckData("palov", "+3"))
        ls.add(CookerCheckData("somsa", "-3"))
        ls.add(CookerCheckData("manti", "7"))
        ls.add(CookerCheckData("cola", "1"))
        CookerCheckDialog(requireContext(),5,ls).show()
//        createPdf(ls)
    }

    override fun ordersFromServer(list: List<OrderGetData>) {
        if (unPaidListOld.isEmpty()) {
            unPaidListOld.addAll(list)
//            createPdf(list)
        } else {

        }
        unPaidList.addAll(list)
    }

    //    private fun createPdf(list: List<OrderGetData>) {
//        list.forEach {
//            val mDoc = Document()
//            val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
//            val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
//            try {
//                PdfWriter.getInstance(mDoc,FileOutputStream(mFilePath))
//                mDoc.open()
//                mDoc.addAuthor("Table No ${it.id}")
//                it.menuSelection.forEach {menu->
//                    mDoc.add(Paragraph("${menu.menu.name}    count  " ))
//                }
//                mDoc.close()
//
//            }catch (t:Throwable){
//                t.message?.let { it1 -> showSnackMessage(it1) }
//            }
//            val uri = File(mFilePath).toUri()
//            CookerCheckDialog(requireContext(),uri).show()
//        }
//    }
//    private fun createPdf(list: List<OrderGetData>) {
////        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
////            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
//                list.forEach {
//                    val mDoc = Document()
//                    val mFileName = SimpleDateFormat(
//                        "yyyyMMdd_HHmmss",
//                        Locale.getDefault()
//                    ).format(System.currentTimeMillis())
//                    val mFilePath = Environment.getExternalStorageDirectory()
//                        .toString() + "/" + mFileName + ".pdf"
//                    try {
//                        PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
//                        mDoc.open()
//                        mDoc.addAuthor("Table No {it.id}")
//                        mDoc.add(Paragraph("{menu.menu.name}    count  "))
//                        for (i in 0 until 10) {
//                            mDoc.add(Paragraph("{menu.menu.name}    count  "))
//
//                        }
//                        mDoc.close()
//
//                    } catch (t: Throwable) {
//                        t.message?.let { it1 -> showSnackMessage(it1) }
//                        t.message?.let { it1 -> customLog(it1) }
//                    }
//                    val uri = File(mFilePath).toUri()
//                    showSnackMessage(uri.toString())
//                    customLog("Success")
////                    CookerCheckDialog(requireContext(), uri).show()
//                }
////            }
////        }
//
//    }


    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        bn.loadingLayoutPinLock.isClickable = !status
        bn.progresss.loading.visible(status)

    }

    override fun openErrorDialog(message: String, status: Boolean) {
//        customDialog(message, status)
        showSnackMessage(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}