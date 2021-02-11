package com.example.restuarant.extentions

import android.accounts.AuthenticatorException
import android.accounts.NetworkErrorException
import android.content.ContentResolver
import android.content.Context
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Vibrator
import android.provider.MediaStore
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.presentation.responseDialog.ResponseStatusDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonSyntaxException
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

fun Double.formatDouble():String{
    val split = this.toString().split(".")
    return split[0].toLong().stringFormat() + ".${split[1]}"
}

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun Navigator.setLunchScreen(screen: SupportAppScreen) {
    applyCommands(arrayOf(BackTo(null), Replace(screen)))
}

typealias SingleBlock <T> = (T) -> Unit

fun Double.formatDouble():String{
    val split = this.toString().split(".")
    return split[0].toLong().stringFormat() + ".${split[1]}"
}

fun String.isNotDouble(): Boolean {
//     if double return false       else return true
    val size = this.length
    val nnn = this.replace(".", "")
    return nnn.length == size
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Long.stringFormat(): String {
    return String.format("%,d", this).replace(',', ' ')
}

fun Fragment.showSnackMessage(message: String) {
    view?.showSnackMessage(message)
}

fun Fragment.showToastMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackMessage(message: String) {
    val ssb = SpannableStringBuilder().apply {
        append(message)
        setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            message.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    Snackbar.make(this, ssb, Snackbar.LENGTH_LONG).show()
}

fun vibrate(context: Context) {
    val vibrate = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrate.vibrate(100)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this, ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                colorRes
            )
        )
    )
}

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd")
    return format.format(date)
}

fun currentTimeToLong(): Long {
    return System.currentTimeMillis()
}

fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat("yyyy.MM.dd")
    return df.parse(date).time
}

fun PopupMenu.setForceShowIcon() {
    try {
        val fields: Array<Field> = this.javaClass.declaredFields
        for (field in fields) {
            if ("mPopup" == field.name) {
                field.isAccessible = true
                val menuPopupHelper: Any = field.get(this)!!
                val classPopupHelper = Class.forName(
                    menuPopupHelper
                        .javaClass.name
                )
                val setForceIcons: Method = classPopupHelper.getMethod(
                    "setForceShowIcon", Boolean::class.javaPrimitiveType
                )
                setForceIcons.invoke(menuPopupHelper, true)
                break
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun Fragment.getDate(): String {
    return "${Calendar.getInstance().get(Calendar.MONTH)}/${
    Calendar.getInstance().get(Calendar.YEAR)
    }"
}

fun customLog(message: String) {
    Log.d("AAA", message)
}

fun putAnimate(context: Context) {

}

fun convertTimeWithTimeZome(time: Long): String? {
    val cal = Calendar.getInstance()
    cal.timeZone = TimeZone.getTimeZone("UTC")
    cal.timeInMillis = time
    return String.format(
        "%02d.%02d.%04d",
        cal[Calendar.DAY_OF_MONTH],
        cal[Calendar.MONTH],
        cal[Calendar.YEAR]
    )
}

fun ViewGroup.inflate(@LayoutRes resId: Int) =
    LayoutInflater.from(context).inflate(resId, this, false)

fun RecyclerView.ViewHolder.bindItem(block: View.() -> Unit) = block(itemView)

fun Fragment.checkPermission(permission: String, granted: () -> Unit) {
    val mContext = context ?: return
    val options = Permissions.Options()
    options.setCreateNewTask(true)
    Permissions.check(
        mContext,
        arrayOf(permission),
        null,
        options,
        object : PermissionHandler() {
            override fun onGranted() {
                granted()
            }
        })
}

fun FragmentActivity.checkPermission(permission: String, granted: () -> Unit) {
    val mContext = this
    val options = Permissions.Options()
    options.setCreateNewTask(true)
    Permissions.check(
        mContext,
        arrayOf(permission),
        null,
        options,
        object : PermissionHandler() {
            override fun onGranted() {
                granted()
            }
        })
}

fun getPath(context: Context, uri: Uri): String {
    var cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
    cursor.moveToFirst()
    var document_id = cursor.getString(0)
    document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
    cursor.close()
    cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
    )!!
    cursor.moveToFirst()
    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
    cursor.close()
    return path
}

fun getImageSizeFromUriInMegaByte(context: Context, uri: Uri): Double {
    val scheme = uri.scheme
    var dataSize = 0.0
    if (scheme == ContentResolver.SCHEME_CONTENT) {
        try {
            val fileInputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (fileInputStream != null) {
                dataSize = fileInputStream.available().toDouble()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else if (scheme == ContentResolver.SCHEME_FILE) {
        val path = uri.path
        var file: File? = null
        try {
            file = File(path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (file != null) {
            dataSize = file.length().toDouble()
        }
    }
    return dataSize / (1024 * 1024)
}

fun encodeToBase64(image: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun decodeBase64(input: String?): Bitmap? {
    val decodedByte = Base64.decode(input, 0)
    return BitmapFactory
        .decodeByteArray(decodedByte, 0, decodedByte.size)
}

fun runOnWorkerThread(f: () -> Unit) {
    Executors.newSingleThreadExecutor().execute(f)
}

fun Throwable.errorResponse(): String {
    return when (this) {
        is IllegalStateException -> "Notog'ri ma'lumot qaytdi"
        is JsonSyntaxException -> "Notog'ri ma'lumot qaytdi"
        is SocketTimeoutException -> "Serverga ulanib bolmadi"
        is NoRouteToHostException -> "Serverga ulanib bolmadi"
        is retrofit2.adapter.rxjava2.HttpException -> {
            when (this.code()) {
                400 -> {
                    "So'rov no'tog'ri yuborildi"
                }
                401 -> {
                    "Foydalanuvchi ro'yhatdan o'tmagan"
                }
                in 500..600 -> {
                    "Server error"
                }
                else -> {
                    "Aniqlanmagan xatolik"
                }
//                71 90 91 94 93 95 33 88 97 98 99
            }
        }
        is ConnectException -> "Internetga ulanib bolmadi"
        is SocketException -> "Internetga ulanib bolmadi"
        is NetworkErrorException -> "Internetga ulanib bolmadi"
        else -> "Aniqlanmagan xatolik. Iltimos qayta urinib ko'ring"

    }
}

fun customSubString(string: String): String {
    val substring = string.substring(6, (string.length - 2))
    return if (substring == "null") {
        "0"
    } else {
        substring
    }
}

fun Fragment.customDialog(message: String, status: Boolean) {
    val dialog = ResponseStatusDialog(requireContext(), message, status)
    dialog.show()
}
