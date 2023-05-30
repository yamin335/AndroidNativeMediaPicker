package mollah.yamin.androidnativemediapicker.app

import android.app.Application
import androidx.databinding.DataBindingUtil
import mollah.yamin.androidnativemediapicker.binding.AppDataBindingComponent

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(AppDataBindingComponent())
    }
}