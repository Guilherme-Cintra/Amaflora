package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentDiscoverBinding
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentDiscoverBinding
    val discoverAdapter = DiscoverAdapter()
    val plantsRepo = PlantRepository()
    val plantsViewModel : PlantsViewModel by lazy {
        ViewModelProvider(this, PlantViewModelProviderFactory(plantsRepo)).get(
            PlantsViewModel::class.java
        )
    }

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
        binding = FragmentDiscoverBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchPlant()

        clickPlant()

    }




    fun clickPlant(){
        discoverAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("plant", it)
            }
            findNavController().navigate( R.id.action_discoverFragment_to_discoverDetailFragment,
                bundle
            )
        }
    }

    fun searchPlant(){
        binding.editTextText.doAfterTextChanged {
            plantsViewModel.searchPlant(it.toString().trim())
        }
        binding.discoverPlantsRv.adapter = discoverAdapter
        plantsViewModel.allPlants.observe( viewLifecycleOwner, Observer { response ->
            discoverAdapter.setDiscoverPlants(response.data)
            Log.i("testing", "${response.data.size}")
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}