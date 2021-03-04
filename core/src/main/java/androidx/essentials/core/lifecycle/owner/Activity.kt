package androidx.essentials.core.lifecycle.owner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.essentials.core.lifecycle.callback.FragmentLifecycleCallbacks
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Events
import androidx.essentials.extensions.Context.getActivity
import androidx.essentials.extensions.TryCatch.Try
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.Serializable
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

abstract class Activity : AppCompatActivity() {

    abstract val layout: Int
    val compositeDisposable = CompositeDisposable()
    protected open val binding: ViewDataBinding? = null
    protected open val viewModel by viewModels<ViewModel>()
    private val toast by lazy { Toast.makeText(this, "", Toast.LENGTH_SHORT) }
    inline fun <reified T : ViewModel> Activity.viewModel() = koinViewModel<T>()
    inline fun <reified T : ViewDataBinding> Activity.dataBinding() = lazy {
        DataBindingUtil.setContentView(this, layout) as T
    }

    inline fun <reified T : Any> Activity.subscribe(crossinline action: (T) -> Unit) =
        compositeDisposable.add(Events.subscribe<T> { action.invoke(it) })

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks, true)
        onViewCreated(binding?.root!!, savedInstanceState)
        binding?.lifecycleOwner = this
        initObservers()
    }

    @CallSuper
    protected open fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit

    @CallSuper
    protected open fun initObservers() = Unit

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Activity, { action.invoke(it) })
    }

    fun DialogFragment.show() = Try { if (!isAdded) show(supportFragmentManager, null) }

    protected fun resumeApplication() = if (
        !isTaskRoot and
        (intent.action != null) and
        (intent.action.equals(Intent.ACTION_MAIN)) and
        (intent.hasCategory(Intent.CATEGORY_LAUNCHER))
    ) {
        finish()
        true
    } else false

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks)
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        toast.apply {
            setDuration(duration)
            setText(resId)
        }.show()
    }

    fun toast(s: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        toast.apply {
            setDuration(duration)
            setText(s)
        }.show()
    }

    companion object {
        inline fun <reified T : android.app.Activity> Context.start(
            vararg extras: Pair<Enum<*>, Any>,
            flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
        ): android.app.Activity? {
            startActivity(Intent(this, T::class.java).apply {
                extras.forEach {
                    when (it.second) {
                        is Bundle -> putExtra(it.first.name, it.second as Bundle)
                        is Parcelable -> putExtra(it.first.name, it.second as Parcelable)
                        is Serializable -> putExtra(it.first.name, it.second as Serializable)
                        is Array<*> -> putExtra(it.first.name, it.second as Array<*>)
                        is Boolean -> putExtra(it.first.name, it.second as Boolean)
                        is BooleanArray -> putExtra(it.first.name, it.second as BooleanArray)
                        is Byte -> putExtra(it.first.name, it.second as Byte)
                        is ByteArray -> putExtra(it.first.name, it.second as ByteArray)
                        is Char -> putExtra(it.first.name, it.second as Char)
                        is CharArray -> putExtra(it.first.name, it.second as CharArray)
                        is CharSequence -> putExtra(it.first.name, it.second as CharSequence)
                        is Double -> putExtra(it.first.name, it.second as Double)
                        is DoubleArray -> putExtra(it.first.name, it.second as DoubleArray)
                        is Float -> putExtra(it.first.name, it.second as Float)
                        is FloatArray -> putExtra(it.first.name, it.second as FloatArray)
                        is Int -> putExtra(it.first.name, it.second as Int)
                        is IntArray -> putExtra(it.first.name, it.second as IntArray)
                        is Long -> putExtra(it.first.name, it.second as Long)
                        is LongArray -> putExtra(it.first.name, it.second as LongArray)
                        is Short -> putExtra(it.first.name, it.second as Short)
                        is ShortArray -> putExtra(it.first.name, it.second as ShortArray)
                        is String -> putExtra(it.first.name, it.second as String)
                    }
                }
                this.flags = flags
            })
            return getActivity()
        }
    }

}