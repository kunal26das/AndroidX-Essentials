package androidx.essentials.playground.ui.preferences

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData

class SharedPreferencesViewModel : ViewModel() {

    val _int by mutableLiveData<Int>(Preference.INT)
    val _long by mutableLiveData<Long>(Preference.LONG)
    val _float by mutableLiveData<Float>(Preference.FLOAT)
    val _string by mutableLiveData<String>(Preference.STRING)
    val _boolean by mutableLiveData<Boolean>(Preference.BOOLEAN)

}