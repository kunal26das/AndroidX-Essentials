package androidx.essentials.playground.utils

import androidx.essentials.firebase.Firebase
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback

interface Listeners : Firebase.OnTokenChangeListener,
    NetworkCallback.OnNetworkStateChangeListener,
    LocationProvider.OnLocationChangeListener