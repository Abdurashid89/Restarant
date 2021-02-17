package com.example.restuarant.ui.wareHouse.story

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentHistoryBinding
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.presentation.were_house.StoryView
import com.example.restuarant.ui.global.BaseFragment
import java.lang.NullPointerException

class HistoryFragment : BaseFragment(), StoryView {
    override val layoutRes: Int = R.layout.fragment_history
    private var _bn: FragmentHistoryBinding? = null
    private val binding get() = _bn ?: throw NullPointerException("error")
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemList: ArrayList<ProductInData>


    override fun showMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun makeLoadingVisible(status: Boolean) {
        TODO("Not yet implemented")
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

}