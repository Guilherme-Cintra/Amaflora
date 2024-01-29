package ca.qc.castroguilherme.amaflorafb

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentAddPlantBinding
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.OwnedPlantBody
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel.PlantsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import java.time.LocalDate
import java.util.Calendar
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPlantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAddPlantBinding
    private val args: AddPlantFragmentArgs by navArgs()

    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel: AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
        )
    }

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
        binding = FragmentAddPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateUser()
        val id = args.id

        binding.pickTimeCard.setOnClickListener {
            showTimePickerDialog()
        }


        plantsViewModel.getDetail(id)
        plantsViewModel.detailPlant.observe(viewLifecycleOwner, Observer {
            Log.i("plantUrl", "${it.defaultImage?.mediumUrl}")
            binding.plantImage.apply {

                Glide.with(this)
                    .load(it.defaultImage?.mediumUrl)
                    .placeholder(R.drawable.baseline_forest_24)
                    .error(R.drawable.baseline_forest_24)
                    .into(binding.plantImage)

            }
        })



        binding.backBtn.setOnClickListener { findNavController().navigateUp() }

        addingPlant()

        amaFloraViewModel.ownedPlantResponse.observe(viewLifecycleOwner, Observer {
            if (it.id != null){
                Log.i("owned", "")
                Snackbar.make(view, "${it.surnom}  added to your list", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addPlantFragment_to_profileFragment)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addingPlant() {
        plantsViewModel.getDetail(id)
        plantsViewModel.detailPlant.observe(viewLifecycleOwner, Observer { plant ->
            if (!binding.editTextText2.text.isEmpty() || !binding.editTextText3.text.isEmpty() || !binding.editFrequency.text.isEmpty())
                binding.button.setOnClickListener {
                    val frequency = binding.editFrequency.text.toString().toInt()
                    val currentDate = LocalDate.now().toString()
                    val ownedPlantBody = OwnedPlantBody(currentDate, binding.editTextText3.text.toString(), binding.editFrequency.text.toString().toInt(), binding.time8?.text.toString(), plant.defaultImage?.originalUrl.toString(),plant.commonName.toString(), plant.id, binding.editTextText2.text.toString(), firebaseUser.uid )
                    amaFloraViewModel.addOwnedPlant(ownedPlantBody)


                }
        })

    }

    private fun showTimePickerDialog() {

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->

                val selectedTime = "$selectedHour:$selectedMinute"
                binding.time8?.setText(selectedTime)

                Log.i("TIME", "$selectedTime")
            }, hour, minute, true
        )

        timePickerDialog.show()
    }

    private fun initiateUser() {
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}