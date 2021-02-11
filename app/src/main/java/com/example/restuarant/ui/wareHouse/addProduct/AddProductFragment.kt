package com.example.restuarant.ui.wareHouse.addProduct

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.PermissionChecker
import com.example.restuarant.R
//import com.example.restuarant.databinding.FragmentAddProductBinding
import com.example.restuarant.extentions.*
import com.example.restuarant.presentation.were_house.add_product.AddProductPresenter
import com.example.restuarant.presentation.were_house.add_product.AddProductView
//import com.example.restuarant.ui.category.CategoryAdapter
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
//
//class AddProductFragment : BaseFragment(), AddProductView {
//    override val layoutRes: Int = R.layout.fragment_add_product
//    private lateinit var binding: FragmentAddProductBinding
//
//    private var image_uri: Uri? = null
//    private val PERMISSION_CODE = 100
//    private val IMAGE_CAPTURE_CODE = 101
//    var categoryId = -1
//    var brandId = -1
//    var currentCategoryTxt = ""
//    var currentBrandTxt = ""
//
////    private val adapter = CategoryAdapter()
////    private val brandAdapter = BrandAdapter()
//
//    @InjectPresenter
//    lateinit var presenter: AddProductPresenter
//
//    @ProvidePresenter
//    fun providePresenter(): AddProductPresenter = scope.getInstance(AddProductPresenter::class.java)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentAddProductBinding.bind(view)
//
//        binding.toolbarProduct.setNavigationOnClickListener {
//            presenter.onBackPressed()
//        }
//
//        binding.inputName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                val product = p0.toString()
//                if (product.isNotEmpty() && product.length > 2) {
//                    presenter.productExistOrNot(product)
//                }
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//        })
//
//
////        binding.inputBrandEditText.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
////            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                if (p0.toString().isNotEmpty() && p0.toString() != currentBrandTxt) {
////                    presenter.getBrandSearch(p0.toString().toLowerCase())
////                }
////            }
////
////            override fun afterTextChanged(p0: Editable?) {}
////
////        })
//
//        binding.selectImage.setOnClickListener {
//            val selectText = resources.getStringArray(R.array.entry_values_choose_image)
//            val alertDialogBuilder = AlertDialog.Builder(requireContext())
//                .setTitle(resources.getString(R.string.message_select_or_take))
//                .setSingleChoiceItems(
//                    selectText, -1
//                ) { p0, p1 ->
//                    when (p1) {
//                        0 -> {
//                            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
//                                openGalleryForImage()
//                                p0.dismiss()
//                            }
//                        }
//                        1 -> {
//                            openWithCamera()
//                            p0.dismiss()
//                        }
//
//                    }
//                }
//            val alertDialog = alertDialogBuilder.create()
//            alertDialog.show()
//
//        }
//
//        binding.btnAddProduct.setOnClickListener {
//            val category = binding.inputEditTextCategory.text.toString().trim()
////            val brand = binding.inputBrandEditText.text.toString().trim()
//            val name = binding.inputName.text.toString().trim()
//
//            when {
//                name.isEmpty() -> {
//                    binding.inputName.startAnimation(
//                        AnimationUtils.loadAnimation(
//                            context,
//                            R.anim.shake
//                        )
//                    )
//                    vibrate(requireContext())
//                    return@setOnClickListener
//                }
//                name.length < 2 -> {
//                    binding.inputName.error = "name must be min 2 symbols"
//                    return@setOnClickListener
//                }
//
//                category.isEmpty() || categoryId == -1 -> {
//                    binding.inputEditTextCategory.startAnimation(
//                        AnimationUtils.loadAnimation(
//                            requireContext(),
//                            R.anim.shake
//                        )
//                    )
//                    vibrate(requireContext())
//                    return@setOnClickListener
//                }
//
////                brand.isEmpty() || brandId == -1 -> {
////                    binding.inputBrandEditText.startAnimation(
////                        AnimationUtils.loadAnimation(
////                            requireContext(),
////                            R.anim.shake
////                        )
////                    )
////                    vibrate(requireContext())
////                    return@setOnClickListener
////                }
//                else -> {
////presenter.addProduct(ProductData())
//                }
//            }
//        }
//    }
//
//    private fun openGalleryForImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, AddProductFragment.REQUEST_CODE)
//    }
//
//    @SuppressLint("WrongConstant")
//    private fun openWithCamera() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (PermissionChecker.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.CAMERA
//                ) == PackageManager.PERMISSION_GRANTED || PermissionChecker.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_DENIED
//            ) {
//                val permission =
//                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                requestPermissions(permission, PERMISSION_CODE)
//            } else {
//                openCamera()
//            }
//        } else {
//            openCamera()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            PERMISSION_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openCamera()
//                } else {
//                    showMessage("Permission denied")
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
//            val uri = data?.data ?: return
//            val path = PathUtil.getPath(context, uri)
//            if (getImageSizeFromUriInMegaByte(requireContext(), uri) > 2.0) {
//                customDialog(resources.getString(R.string.errorImageMessage), false)
//            } else {
//                Log.d("TTT", "image uri:$uri\n ")
//            }
//        }
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
//            val path = PathUtil.getPath(context, image_uri)
//            if (getImageSizeFromUriInMegaByte(requireContext(), image_uri!!) > 10.0) {
//                customDialog(resources.getString(R.string.errorImageMessageCamera), false)
//            } else {
//
//            }
//        }
//    }
//
//    private fun openCamera() {
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "New Picture")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//        image_uri = requireContext().contentResolver.insert(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            values
//        )
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
//        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
//    }
//
//    companion object {
//        val REQUEST_CODE = 100
//    }
//
//
//    override fun showMessage(message: String) {
//        showSnackMessage(message)
//    }
//
//    override fun makeLoadingVisible(status: Boolean) {
//        binding.loadingLayoutAddProduct.isClickable = !status
//        binding.progressBarAddProduct.loading.visible(status)
//    }
//
//    override fun openDialog(message: String, status: Boolean) {
//        if (status) {
//            binding.inputName.setText("")
//            binding.inputEditTextCategory.setText("")
////            binding.inputBrandEditText.setText("")
//            categoryId = -1
//            brandId = -1
//        }
//        customDialog(message, status)
//    }
//
//    override fun errorOrNull(str: String) {
//
//    }
//
//    override fun productYON(status: Boolean, message: String) {
//        if (status) {
//            binding.inputName.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                0,
//                0,
//                R.drawable.ic_baseline_check_24, 0
//            )
//        } else {
//            binding.inputName.error = message
//        }
//    }
//}