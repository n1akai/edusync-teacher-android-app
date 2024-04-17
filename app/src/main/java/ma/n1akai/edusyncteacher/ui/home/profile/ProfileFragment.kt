package ma.n1akai.edusyncteacher.ui.home.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.databinding.FragmentProfileBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTeacher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.teacher.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            binding.apply {
                profileTvFullName.text = it.getFullName()
                profileTvCne.text = it.cne
                profileTvFirstName.text = it.first_name
                profileTvLastName.text = it.last_name
                profileTvDateOfBirth.text = it.date_of_birth
                profileTvAdresse.text = it.adresse
                profileTvExperience.text = it.experience
                profileTvQualification.text = it.qualification
                profileTvPhoneNumber.text = it.phone_number
                profileTvEmail.text = it.email
            }
        }
    }

}