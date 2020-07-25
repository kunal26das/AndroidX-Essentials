package androidx.essentials.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
@Deprecated("DEPRECATION")
class NetworkCallbackReceiver : BroadcastReceiver() {

    private var connectivityManager: ConnectivityManager? = null
    var onNetworkStateChangeListener: NetworkCallback.OnNetworkStateChangeListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        connectivityManager = ConnectivityManagerCompat.getInstance(context!!)
        when ((connectivityManager?.activeNetworkInfo?.isConnected)) {
            null -> onNetworkStateChangeListener?.onOffline()
            true -> onNetworkStateChangeListener?.onOnline()
            false -> onNetworkStateChangeListener?.onOffline()
        }
    }

    companion object {
        const val CONNECTIVITY_ACTION = ConnectivityManager.CONNECTIVITY_ACTION
    }
}
