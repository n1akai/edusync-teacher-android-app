package ma.n1akai.edusyncteacher.ui.home.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.model.Module


class SpinnerAdapter(val context: Context) : BaseAdapter() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getCount() = items.size

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = items[position]
        var viewHolder: ViewHolders? = null
        var view = convertView
        if (view == null) {
            viewHolder = when (item) {
                is Homework -> ViewHolders.HomeworkViewHolder()
                is Class -> ViewHolders.ClassViewHolder()
                is Module -> ViewHolders.ModuleViewHolder()
                else -> throw IllegalArgumentException("Invalid Item Type")
            }
            view = LayoutInflater.from(context).inflate(R.layout.spinner_layout, parent, false)
            viewHolder.initView(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolders
        }
        when (viewHolder) {
            is ViewHolders.HomeworkViewHolder -> viewHolder.bind(item as Homework)
            is ViewHolders.ClassViewHolder -> viewHolder.bind(item as Class)
            is ViewHolders.ModuleViewHolder -> viewHolder.bind(item as Module)
        }
        return view!!
    }

    sealed class ViewHolders {
        abstract fun initView(view: View?)

        class HomeworkViewHolder : ViewHolders() {
            private lateinit var text: TextView

            override fun initView(view: View?) {
                text = view!!.findViewById(R.id.sp_text)
            }

            fun bind(item: Homework) {
                text.text = "${item.class_name} - ${item.homework}"
            }
        }
        class ClassViewHolder : ViewHolders() {
            private lateinit var text: TextView

            override fun initView(view: View?) {
                text = view!!.findViewById(R.id.sp_text)
            }

            fun bind(item: Class) {
                text.text = item.class_name
            }
        }
        class ModuleViewHolder : ViewHolders() {
            private lateinit var text: TextView

            override fun initView(view: View?) {
                text = view!!.findViewById(R.id.sp_text)
            }

            fun bind(item: Module) {
                text.text = item.course_name
            }
        }
    }
}