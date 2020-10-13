package app.cekongkir.kotlin.ui.cost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.cekongkir.R
import app.cekongkir.kotlin.ui.city.CityFragment
import app.cekongkir.kotlin.ui.home.HomeActivity
import app.cekongkir.kotlin.ui.waybill.WaybillFragment
import kotlinx.android.synthetic.main.fragment_cost.view.*

class CostFragment : Fragment() {

    lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_cost, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener(){
        fragmentView.button_cost.setOnClickListener {
            val cityFragment = CityFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.containerHome, cityFragment, "cityFragment")
                    .commit()
        }
    }
}