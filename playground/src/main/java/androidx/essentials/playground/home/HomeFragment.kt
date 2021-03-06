package androidx.essentials.playground.home

import android.os.Bundle
import android.view.View
import androidx.essentials.fragment.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels

class HomeFragment : Fragment() {

    override val layout = R.layout.fragment_home
    override val viewModel by viewModels<HomeViewModel>()
    override val binding by dataBinding<FragmentHomeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}