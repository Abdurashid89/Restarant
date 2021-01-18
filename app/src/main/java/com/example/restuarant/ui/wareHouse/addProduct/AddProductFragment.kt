package com.example.restuarant.ui.wareHouse.addProduct

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.PermissionChecker
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentAddProductBinding
import com.example.restuarant.extentions.customDialog
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.BrandInData
import com.example.restuarant.model.entities.CategoryInData
import com.example.restuarant.presentation.were_house.add_product.AddProductPresenter
import com.example.restuarant.presentation.were_house.add_product.AddProductView
import com.example.restuarant.ui.category.CategoryAdapter
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class AddProductFragment : BaseFragment(), AddProductView {
    override val layoutRes: Int = R.layout.fragment_add_product
    private lateinit var binding: FragmentAddProductBinding

    private var image_uri: Uri? = null
    private val PERMISSION_CODE = 100
    private val IMAGE_CAPTURE_CODE = 101
    var categoryId = -1
    var brandId = -1
    var currentCategoryTxt = ""
    var currentBrandTxt = ""

    private val adapter = CategoryAdapter()
//    private val brandAdapter = BrandAdapter()

    @InjectPresenter
    lateinit var presenter: AddProductPresenter

    @ProvidePresenter
    fun providePresenter(): AddProductPresenter = scope.getInstance(AddProductPresenter::class.java)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)

        binding.toolbarProduct.setNavigationOnClickListener {
            presenter.onBackPressed()
        }

        binding.inputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val product = p0.toString()
                if (product.isNotEmpty() && product.length > 2) {
                    presenter.productExistOrNot(product)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.inputEditTextCategory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty() && p0.toString() != currentCategoryTxt)
                    presenter.getCategorySearch(p0.toString().toLowerCase())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

//        binding.inputBrandEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0.toString().isNotEmpty() && p0.toString() != currentBrandTxt) {
//                    presenter.getBrandSearch(p0.toString().toLowerCase())
//                }
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//
//        })

        binding.btnAddProduct.setOnClickListener {
            val category = binding.inputEditTextCategory.text.toString().trim()
//            val brand = binding.inputBrandEditText.text.toString().trim()
            val name = binding.inputName.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.inputName.startAnimation(
                        AnimationUtils.loadAnimation(
                            context,
                            R.anim.shake
                        )
                    )
                    vibrate(requireContext())
                    return@setOnClickListener
                }
                name.length < 2 -> {
                    binding.inputName.error = "name must be min 2 symbols"
                    return@setOnClickListener
                }

                category.isEmpty() || categoryId == -1 -> {
                    binding.inputEditTextCategory.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.shake
                        )
                    )
                    vibrate(requireContext())
                    return@setOnClickListener
                }
//                brand.isEmpty() || brandId == -1 -> {
//                    binding.inputBrandEditText.startAnimation(
//                        AnimationUtils.loadAnimation(
//                            requireContext(),
//                            R.anim.shake
//                        )
//                    )
//                    vibrate(requireContext())
//                    return@setOnClickListener
//                }
                else -> {
//presenter.addProduct(ProductData())
                }
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AddProductFragment.REQUEST_CODE)
    }

    @SuppressLint("WrongConstant")
    private fun openWithCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED || PermissionChecker.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    companion object {
        val REQUEST_CODE = 100
    }


    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        binding.loadingLayoutAddProduct.isClickable = !status
        binding.progressBarAddProduct.loading.visible(status)
    }

    override fun openDialog(message: String, status: Boolean) {
        if (status) {
            binding.inputName.setText("")
            binding.inputEditTextCategory.setText("")
//            binding.inputBrandEditText.setText("")
            categoryId = -1
            brandId = -1
        }
        customDialog(message, status)
    }

    override fun listCategory(list: List<CategoryInData>) {
        binding.listCategory.visibility = View.VISIBLE
        binding.listCategory.adapter = adapter

        adapter.submitList(null)
        adapter.submitList(list)
        adapter.setOnClickListener {
            currentCategoryTxt = it.name
            binding.inputEditTextCategory.setText(it.name)
            categoryId = it.id
            binding.listCategory.visibility = View.GONE
        }
    }

    override fun listBrand(list: List<BrandInData>) {
//        binding.listBrand.visibility = View.VISIBLE
//        binding.listBrand.adapter = brandAdapter
//
//        brandAdapter.submitList(null)
//        brandAdapter.submitList(list)
//        brandAdapter.setOnClickListener {
//            currentCategoryTxt = it.name
//            binding.inputBrandEditText.setText(it.name)
//            brandId = it.id
//            binding.listBrand.visibility = View.GONE
//        }
    }

    override fun errorOrNull(str: String) {

    }

    override fun productYON(status: Boolean, message: String) {
        if (status) {
            binding.inputName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_check_24, 0
            )
        } else {
            binding.inputName.error = message
        }
    }
}