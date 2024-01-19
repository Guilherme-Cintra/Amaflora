package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentDiscoverDetailBinding
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Recherche
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantsViewModel
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

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

    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    val plantsRepo = PlantRepository()
    val plantsViewModel : PlantsViewModel by lazy {
        ViewModelProvider(this, PlantViewModelProviderFactory(plantsRepo)).get(
            PlantsViewModel::class.java
        )
    }

    private val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel : AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
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
        val id = plant.id
        Log.i("MyApi", "id : ${id}")

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
             if (care(response.careLevel) != null) {
                 binding.careTxt.text = care(response.careLevel)
             }

            val sun = sun(response.sunlight)

            response.description

            val createPlant = CreatePlant(response.description, response.wateringGeneralBenchmark.value, plant.id, response.defaultImage.thumbnail, response.careLevel, response.commonName, response.scientificName[0], sun, response.wateringGeneralBenchmark.unit )
            amaFloraViewModel.createPlant(createPlant)
            amaFloraViewModel.platCreationResponse.observe(viewLifecycleOwner, Observer {
                response ->
                Log.i("MyApi", "${response.newPlant?.description}")
            })

            auth = Firebase.auth
            firebaseUser = auth.currentUser!!
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val recherche = RechercheBody(currentDate.toString(), id, firebaseUser.uid.toString())

            Log.i("MyApi", "date : ${recherche.date}")
            Log.i("MyApi", "id : ${recherche.plantId}}")
            Log.i("MyApi", "uid : ${recherche.userId}")

            amaFloraViewModel.sauvegarderRecherche(recherche)

            amaFloraViewModel.rechercheSauvegardeResponse.observe(viewLifecycleOwner, Observer {
                response ->
                if (response.recherche != null) {
                    Log.i("MyApi", "recherche sauvegardée date : ${response.recherche.date}")
                }
            })

        })


        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_discoverDetailFragment_to_addPlantFragment)
        }
        back()

    }

    private fun back() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

//    private fun descriptionMax(desc: String) :String {
//        if (desc.length > 255){
//            desc.chars()
//            for (i in 0..<255){
//
//            }
//        }
//
//            return  desc
//    }

    private fun detail() {
        val plant = args.plant
        Log.i("DetailError", "plant id: ${plant.id}")
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

    private fun sun(plants: List<String>) :String {
        when (plants.size) {
            1 -> {
                if (plants.contains("full sun")) {
                    return "4 to 6 hours "
                } else  if (plants.contains("part shade") || plants.contains("part sun/part shade")) {
                    return "2 hours"
                } else {
                    return "careful"
                }
            }
            2 -> {
                if (plants.contains("full sun") && plants.contains("part shade") || plants.contains("full sun") && plants.contains("part sun/part shade")) {
                    return  "2 to 6 hours"

                } else if (plants.contains("part shade") && plants.contains("filtered shade")) {
                    return  "0 to 2 hours"
                }
            }
            3 -> {
                return  "no worries"
            }
        }
        return  "no worries"
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