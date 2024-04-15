package ma.n1akai.edusyncteacher.ui.home.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.databinding.ListClassesBinding

class MarkAttendanceAdapter : RecyclerView.Adapter<MarkAttendanceAdapter.MarkAttendanceViewHolder>() {

    var items = listOf<Class>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MarkAttendanceViewHolder(
        private val binding: ListClassesBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: Class) {
            binding.apply {
                tvClassName.text = item.class_name
                tvBranchName.text = item.branch_name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkAttendanceViewHolder {
        return MarkAttendanceViewHolder(ListClassesBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MarkAttendanceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}