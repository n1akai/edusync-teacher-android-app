package ma.n1akai.edusyncteacher.ui.home.modules.marks

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.model.Mark
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.data.network.request.TestMarkRequest
import ma.n1akai.edusyncteacher.databinding.ListModulesBinding
import ma.n1akai.edusyncteacher.databinding.ListStudentMarkBinding
import ma.n1akai.edusyncteacher.util.MinMaxEditTextInputFilter

class MarkAdapter :
    RecyclerView.Adapter<MarkAdapter.MarkViewHolder>() {

    private lateinit var context: Context
    var items = listOf<Student>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var numberOfTests = 1
    var map = mutableMapOf<String, Mark>()

    inner class MarkViewHolder(
        private val binding: ListStudentMarkBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Student) {
            binding.apply {
                studentMarkTvPosition.text = item.getPosition()
                studentMarkTvName.text = item.getFullName()
                for (i in 0..numberOfTests - 1) {
                    val cc = "CC${i + 1}"

                    // LinearLayout
                    val linearLayout = LinearLayout(context)
                    val linearLayoutLp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
                    linearLayoutLp.setMargins(0, 6, 0, 0)
                    linearLayout.apply {
                        orientation = LinearLayout.HORIZONTAL
                        layoutParams = linearLayoutLp

                    }

                    // TextView
                    val textView = TextView(context)
                    val textViewLp = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                    )
                    textView.textSize = 18f
                    textView.setTextColor(Color.parseColor("#FFFFFF"))
                    textView.text = cc

                    linearLayout.addView(textView)

                    // EditText
                    val editText = EditText(context)
                    editText.inputType = EditorInfo.TYPE_CLASS_NUMBER
                    val editTextLp = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                    )
                    editTextLp.setMargins(24, 0, 24, 0) // Adjust margins as needed
                    editText.background = context.getDrawable(R.drawable.edit_text_bg)
                    editText.layoutParams = editTextLp
                    editText.setPadding(16, 0, 16, 0)
                    editText.setText("0.0")

                    if (item.test_marks != null  && item.test_marks?.contains(cc) == true) {
                        editText.setText(item.test_marks?.get(cc).toString())
                    }

                    // EditText TextChanged Listener
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            if (item.test_marks == null) {
                                item.test_marks = mutableMapOf()
                            }
                            if (!s.isNullOrEmpty()) {
                                item.test_marks!![cc] = s.toString().toDouble()
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {

                        }

                    })

                    linearLayout.addView(editText)

                    studentMarkTvAllCc.addView(linearLayout)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkViewHolder {
        context = parent.context
        return MarkViewHolder(
            ListStudentMarkBinding.inflate(
                LayoutInflater.from(context),
                parent, false
            )
        )
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MarkViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}