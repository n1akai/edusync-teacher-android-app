package ma.n1akai.edusyncteacher.component.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ma.n1akai.edusyncteacher.R

class LoadingDialog(
    private val context: Context
) {

    private lateinit var dialog: AlertDialog

    fun show() {
        dialog = AlertDialog.Builder(context)
            .setView(R.layout.progress_layout)
            .create()
        dialog.show()
    }

    fun hide() {
        dialog.hide()
    }

}