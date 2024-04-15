package ma.n1akai.edusyncteacher.ui.home.homework

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.databinding.FragmentHomeworkBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment

@AndroidEntryPoint
class HomeworkFragment : BaseFragment<FragmentHomeworkBinding>() {

    private val viewModel: HomeworkViewModel by viewModels()
    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeworkBinding.inflate(inflater, container, false)


}