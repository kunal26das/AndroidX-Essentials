package androidx.essentials.playground.ui.time

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData

class TimeViewModel : ViewModel() {

    val endTime by mutableLiveData<Long>(Preference.END_TIME)
    val startTime by mutableLiveData<Long>(Preference.START_TIME)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}