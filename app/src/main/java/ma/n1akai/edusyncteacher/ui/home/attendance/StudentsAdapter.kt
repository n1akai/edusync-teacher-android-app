package ma.n1akai.edusyncteacher.ui.home.attendance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.databinding.ListAttendanceBinding
import ma.n1akai.edusyncteacher.databinding.ListClassesBinding
import ma.n1akai.edusyncteacher.databinding.ListHomeWorkBinding

class StudentsAdapter :
    RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder>() {

    var items = listOf<Student>()
        set(value) {
            field = value.sortedBy {
                it.position
            }
            notifyDataSetChanged()
        }
    var absentStudents = mutableListOf<Student>()

    inner class StudentsViewHolder(
        private val binding: ListAttendanceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Student) {
            binding.apply {
                numberHomeWork.text = item.getPosition()
                nameHomeWork.text = item.getFullName()
                isAbsent.isChecked = absentStudents.contains(item)
                isAbsent.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        absentStudents.add(item)
                    } else {
                        absentStudents.remove(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StudentsViewHolder(
            ListAttendanceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}