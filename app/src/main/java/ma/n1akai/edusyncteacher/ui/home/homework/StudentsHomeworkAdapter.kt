package ma.n1akai.edusyncteacher.ui.home.homework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.databinding.ListClassesBinding
import ma.n1akai.edusyncteacher.databinding.ListHomeWorkBinding

class StudentsHomeworkAdapter :
    RecyclerView.Adapter<StudentsHomeworkAdapter.StudentsHomeworkViewHolder>() {

    var items = listOf<Student>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class StudentsHomeworkViewHolder(
        private val binding: ListHomeWorkBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Student) {
            binding.apply {
                numberHomeWork.text = item.getPosition()
                nameHomeWork.text = item.getFullName()
                etatHomeWork.isVisible = item.isHomeworkDone()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StudentsHomeworkViewHolder(
            ListHomeWorkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StudentsHomeworkViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}