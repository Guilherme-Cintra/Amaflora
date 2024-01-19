package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentDiscoverBinding
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetHistoriqueBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

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
    private lateinit var binding: FragmentDiscoverBinding

    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser


    val discoverAdapter = DiscoverAdapter()
    val historiqueAdapter = HistoriqueAdapter()

    val plantsRepo = PlantRepository()
    val plantsViewModel: PlantsViewModel by lazy {
        ViewModelProvider(this, PlantViewModelProviderFactory(plantsRepo)).get(
            PlantsViewModel::class.java
        )
    }

    val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel: AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
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
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val top_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation)
        binding.cardImage.startAnimation(top_anim)


        historique()
        searchPlant()

        clickPlant()




    }

    private fun historique() {
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!
        binding.recentPlantsRv.visibility  = View.VISIBLE
//        var plants = mutableListOf<Plant>()

        amaFloraViewModel.getHistorique(firebaseUser.uid)
        amaFloraViewModel.plantsLiveData.observe(viewLifecycleOwner, Observer {
                plants ->

            plants?.forEach {
                Log.i("plantIn", "fragment plants ${it.id}")
            }

            if (plants != null) {
                binding.recentPlantsRv.adapter = historiqueAdapter
                binding.discoverPlantsRv.visibility = View.GONE

                historiqueAdapter.setPlants(plants)

                binding.cardImage.visibility = View.GONE
            }
        })
    }


    fun clickPlant() {
        discoverAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("plant", it)
            }
            findNavController().navigate(
                R.id.action_discoverFragment_to_discoverDetailFragment,
                bundle
            )
        }
    }

    fun searchPlant() {
        binding.editTextText.doAfterTextChanged {
            binding.cardImage.visibility = View.GONE
            plantsViewModel.searchPlant(it.toString().trim())
            binding.recentPlantsRv.visibility = View.GONE

        }
        binding.discoverPlantsRv.adapter = discoverAdapter
        plantsViewModel.allPlants.observe(viewLifecycleOwner, Observer { response ->
            discoverAdapter.setDiscoverPlants(response.data)
            Log.i("testing", "${response.data.size}")
            binding.discoverPlantsRv.visibility = View.VISIBLE
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