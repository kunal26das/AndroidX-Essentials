package androidx.essentials.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.essentials.network.connectivity.ConnectivityManager

@Suppress("DEPRECATION")
@Deprecated("DEPRECATION")
class NetworkCallbackReceiver : BroadcastReceiver() {

    private var connectivityManager: android.net.ConnectivityManager? = null
    var onNetworkStateChangeListener: NetworkCallback.OnNetworkStateChangeListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        connectivityManager = ConnectivityManager.getInstance(context!!)
        onNetworkStateChangeListener?.onNetworkStateChange(
            connectivityManager?.activeNetworkInfo?.isConnected ?: false
        )
    }

    companion object {

        val INTENT_FILTER = IntentFilter().apply {
            addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
        }
    }
}