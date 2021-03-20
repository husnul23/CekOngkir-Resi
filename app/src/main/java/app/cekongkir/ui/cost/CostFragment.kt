package app.cekongkir.ui.cost

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.databinding.FragmentCostBinding
import app.cekongkir.ui.city.CityActivity
import app.cekongkir.ui.city.CityViewModel

class CostFragment : Fragment(){

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CostViewModel::class.java) }
    private lateinit var binding: FragmentCostBinding
    private var originId: String? = ""
    private var destinationId: String? = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.preference.observe(viewLifecycleOwner, Observer { preferenceList ->
            preferenceList.forEach {
                when (it.type) {
                    "origin" -> {
                        originId = it.id
                        binding.editOrigin.setText(it.name)
                    }
                    "destination" -> {
                        destinationId = it.id
                        binding.editDestination.setText(it.name)
                    }
                }
            }
        })
    }

    private fun setupListener() {
        binding.editOrigin.setOnClickListener {
            startActivity(
                    Intent(context, CityActivity::class.java)
                            .putExtra("intent_type", "origin")
            )
        }

        binding.editDestination.setOnClickListener {
            startActivity(
                    Intent(context, CityActivity::class.java)
                            .putExtra("intent_type", "destination")
            )
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPreferences()
    }
}