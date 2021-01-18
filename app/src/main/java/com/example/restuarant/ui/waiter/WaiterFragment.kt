package com.example.restuarant.ui.waiter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentWaiterBinding
import com.example.restuarant.presentation.waiter.WaiterPresenter
import com.example.restuarant.presentation.waiter.WaiterView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class WaiterFragment : BaseFragment(),WaiterView {
    override val layoutRes: Int = R.layout.fragment_waiter

    private lateinit var binding : FragmentWaiterBinding
    private lateinit var deskAdapter: DeskAdapter
    private lateinit var deskList: ArrayList<Int>

    @InjectPresenter
    lateinit var presenter:WaiterPresenter

    @ProvidePresenter
    fun providePresenter():WaiterPresenter = scope.getInstance(WaiterPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWaiterBinding.bind(view)

        deskList.add(11)
        deskList.add(12)
        deskList.add(13)
        deskList.add(14)
        deskList.add(15)
        deskList.add(16)
        deskList.add(17)
        deskList.add(18)
        deskList.add(19)
        deskList.add(20)

        deskAdapter = DeskAdapter(deskList,object :DeskAdapter.OnDeskItemClickListener{
            override fun onClick() {
               presenter.openClientDialog()

            }

            override fun onLongClick() {

            }

        })
        binding.rv.adapter = deskAdapter

    }

    override fun showMessage(message: String) {

    }

    override fun makeLoadingVisible(status: Boolean) {

    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun openClientCountDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.guest_count, null, false)
        dialog.setView(view)
        dialog.show()
    }


}