package androidx.essentials.io

import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager

object InputMethodManager {

    private var connectivityManager: InputMethodManager? = null

    fun getInstance(context: Context): InputMethodManager {
        if (connectivityManager != null) {
            return connectivityManager!!
        }
        synchronized(this) {
            connectivityManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getSystemService(InputMethodManager::class.java)
            } else {
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            return connectivityManager!!
        }
    }
}