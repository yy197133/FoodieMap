package com.yoy.foodiemap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.yoy.foodiemap.data.Place
import com.yoy.foodiemap.databinding.FragmentAddPlaceBinding
import com.yoy.foodiemap.utils.Injector
import com.yoy.foodiemap.viewmodels.PlacesViewModel

class AddPlaceFragment : Fragment() {

    private lateinit var viewModel: PlacesViewModel

    companion object {
        const val NAME = "name"
        const val ADDRESS = "address"
        const val LAT = "lat"
        const val LNG = "lng"


        fun newInstance(name: String, address: String, lat: Double, lng: Double): AddPlaceFragment{
            val bundle = bundleOf(NAME to name, ADDRESS to address, LAT to lat, LNG to lng)
            return AddPlaceFragment().also { it.arguments = bundle }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAddPlaceBinding.inflate(inflater,container,false)
        val context = context ?: return binding.root

        val lat = arguments?.getDouble(LAT, Double.NaN) ?: Double.NaN
        if (lat == Double.NaN) return binding.root
        val lng = arguments?.getDouble(LNG, Double.NaN) ?: Double.NaN
        if (lng == Double.NaN) return binding.root

        val toobar = binding.toolbar

        toobar.title = "收藏"
        toobar.setNavigationIcon(R.drawable.ic_back)
        (activity as? AppCompatActivity)?.setSupportActionBar(toobar)

        viewModel = ViewModelProviders.of(this,Injector.providePlaceViewModelFactory(context)).get(PlacesViewModel::class.java)

        binding.placeName.setText(arguments?.getString(NAME,"") ?: "")
        binding.placeAddress.text = arguments?.getString(ADDRESS,"") ?: ""

        toobar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnConfirm.setOnClickListener {
            val place = Place(binding.placeName.text.toString(),
                                binding.placeType.text.toString(),
                                binding.placeSpecialty.text.toString(),
                                binding.radioGroup.checkedRadioButtonId == R.id.rb_yes,
                                binding.placeEvaluation.text.toString(),
                                binding.placeAddress.text.toString(),
                                lat,lng)
            viewModel.addPlace(place)
            findNavController().popBackStack()
        }
        return binding.root
    }




}