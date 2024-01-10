package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentDiscoverDetailBinding
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantsViewModel
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val args: DiscoverDetailFragmentArgs by navArgs()
    val plantsRepo = PlantRepository()
    val plantsViewModel : PlantsViewModel by lazy {
        ViewModelProvider(this, PlantViewModelProviderFactory(plantsRepo)).get(
            PlantsViewModel::class.java
        )
    }


    private lateinit var binding: FragmentDiscoverDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val plant = args.plant
        plantsViewModel.getDetail(plant.id)
        plantsViewModel.detailPlant.observe(viewLifecycleOwner, Observer {
                response ->
            binding.imageUrl.apply {
                Glide.with(this)
                    .load(response.defaultImage?.mediumUrl)
                    .placeholder(R.drawable.baseline_forest_24)
                    .error(R.drawable.baseline_forest_24)
                    .into(binding.imageUrl)
            }

            binding.speciesLbl.text = response.scientificName.first().toString()

            binding.nameLbl.text = response.commonName
            binding.textDescription.text = response.description
            binding.careTxt.text = care(response.careLevel.toString())
        })


        back()

    }

    private fun back() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun detail() {
        val plant = args.plant
        plantsViewModel.getDetail(plant.id)
        plantsViewModel.detailPlant.observe(viewLifecycleOwner, Observer {
            response ->
            binding.imageUrl.apply {
                Glide.with(this)
                    .load(response.defaultImage?.mediumUrl)
                    .placeholder(R.drawable.baseline_forest_24)
                    .error(R.drawable.baseline_forest_24)
                    .into(binding.imageUrl)
            }

            binding.speciesLbl.text = response.scientificName.first().toString()

            binding.nameLbl.text = response.commonName
            binding.textDescription.text = response.description
            binding.careTxt.text = care(response.careLevel.toString())
        })
    }

    private fun care(lvl:String):String{
        when (lvl) {
            "Medium" -> {
                return "Medium"
            }

        }
        return "Unknown"
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}