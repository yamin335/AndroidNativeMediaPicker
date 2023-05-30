package mollah.yamin.androidnativemediapicker.binding

import androidx.databinding.DataBindingComponent
import mollah.yamin.androidnativemediapicker.binding.BindingAdapters

/**
 * A default binding component implementation for data binding adapters.
 */
class AppDataBindingComponent : DataBindingComponent {
    private val adapter = BindingAdapters()
    override fun getBindingAdapters() = adapter
}
