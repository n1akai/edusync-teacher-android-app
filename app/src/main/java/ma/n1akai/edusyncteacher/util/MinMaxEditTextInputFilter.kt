package ma.n1akai.edusyncteacher.util

import android.text.InputFilter
import android.text.Spanned

/**
 * Custom InputFilter that restricts the input to a range of minimum and maximum values, both
 * inclusive. The edit text must have android:inputType="number".
 */
class MinMaxEditTextInputFilter(private val mMin: Double, private val mMax: Double) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence {
        try {
            val newValueString: String = dest.subSequence(0, dstart).toString() +
                    source.subSequence(start, end).toString() +
                    dest.subSequence(dend, dest.length)

            if (newValueString.isEmpty() || newValueString == ".") return source

            val newValueDouble = newValueString.toDoubleOrNull()
            if (newValueDouble != null && isInRange(mMin, mMax, newValueDouble)) return source

        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isInRange(min: Double, max: Double, value: Double): Boolean {
        return if (max > min) {
            value in min..max
        } else {
            value in max..min
        }
    }
}
