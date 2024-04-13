package ma.n1akai.edusyncteacher.util

import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show();
}

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}