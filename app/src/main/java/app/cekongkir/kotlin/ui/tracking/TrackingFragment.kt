package app.cekongkir.kotlin.ui.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import app.cekongkir.R
import app.cekongkir.kotlin.utils.showToast
import kotlinx.android.synthetic.main.fragment_tracking.view.*

private const val SICEPAT = "000421030654"

class TrackingFragment : Fragment() {

    private lateinit var fragmentView: View
    private var courier: String = ""
    private var waybill: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_tracking, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTest()
        setupSpinner()
        setupListener()
    }

    private fun setupTest(){
        fragmentView.edit_waybill.setText( SICEPAT )
    }

    private fun setupSpinner(){
        val courierAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.courier, android.R.layout.simple_spinner_item
        )
        courierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentView.list_courier.adapter = courierAdapter
    }

    private fun setupListener(){
        fragmentView.button_track.setOnClickListener {

            courier = fragmentView.list_courier.selectedItem.toString()
            waybill = fragmentView.edit_waybill.text.toString()

            if (waybill.isNotEmpty() && courier.isNotEmpty() ) {
                findNavController().navigate(
                        R.id.action_waybillFragment_to_waybillDetailFragment,
                        bundleOf( "waybill" to waybill, "courier" to courier )
                )
            } else {
                showToast("Lengkapi no. resi dan kurir")
            }
        }
    }


}