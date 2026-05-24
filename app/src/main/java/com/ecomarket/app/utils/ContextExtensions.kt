package com.ecomarket.app.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity

// Obtiene la actividad real desde Compose
fun Context.getActivity(): FragmentActivity? {

    return when (this) {

        is FragmentActivity -> this

        is ContextWrapper -> baseContext.getActivity()

        else -> null
    }
}