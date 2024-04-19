package ma.n1akai.edusyncteacher.ui.home.modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.databinding.ListClassesBinding
import ma.n1akai.edusyncteacher.databinding.ListHomeWorkBinding
import ma.n1akai.edusyncteacher.databinding.ListModulesBinding

class ModuleAdapter :
    RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>() {

    var items = listOf<Module>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var listener: OnModuleClickListener

    inner class ModuleViewHolder(
        private val binding: ListModulesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Module) {
            binding.apply {
                tvClass.text = item.class_name
                tvModuleName.text = "${item.course_code} - ${item.course_name}"
                tvBranchName.text = item.branch_name
                tvMht.text = "C:${item.Coef} - ${item.MHT}H"
            }
            binding.root.setOnClickListener {
                listener.onModuleClick(item, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ModuleViewHolder(
            ListModulesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    interface OnModuleClickListener {
        fun onModuleClick(module: Module, view: View)
    }

}