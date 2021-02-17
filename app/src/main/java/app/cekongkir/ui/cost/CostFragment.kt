package app.cekongkir.ui.cost

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.cekongkir.R
import app.cekongkir.databinding.FragmentCostBinding
import app.cekongkir.ui.city.CityActivity

class CostFragment : Fragment(){

    private lateinit var binding: FragmentCostBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editOrigin.setOnClickListener {
            startActivity(Intent(context, CityActivity::class.java)
            )
        }
    }
}