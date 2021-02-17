package app.cekongkir.ui.city

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.cekongkir.R
import app.cekongkir.databinding.ActivityHomeBinding
import app.cekongkir.databinding.FragmentCityBinding
import app.cekongkir.databinding.FragmentCostBinding

class CityFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(CityViewModel::class.java) }
    private lateinit var binding: FragmentCityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.titleBar.postValue("Pilih Kota")

        binding.container.setOnClickListener {
            findNavController().navigate(R.id.action_cityFragment_to_subdistrictFragment)
        }
    }
}