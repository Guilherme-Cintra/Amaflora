package ca.qc.castroguilherme.amaflorafb

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentOwnedPlantsDetailBinding
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UpdatePlantBody
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OwnedPlantsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OwnedPlantsDetailFragment : Fragment() {
    private lateinit var binding: FragmentOwnedPlantsDetailBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val args: OwnedPlantsDetailFragmentArgs by navArgs()

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
        binding = FragmentOwnedPlantsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plant = args.plant


        updatePlant()


        binding.editFrequencyTxt.setText(plant.frequenceNotif.toString())
        binding.plantPicture.apply {
            Glide.with(this)
                .load(plant.imageUrl)
                .placeholder(R.drawable.baseline_forest_24)
                .error(R.drawable.baseline_forest_24)
                .into(binding.plantPicture)
        }
        binding.description.text = plant.descriptionPerso.toString()
        binding.back.setOnClickListener { findNavController().navigateUp() }

        binding.plantNom.setText(plant.surnom.toString())
        binding.timeTxt.setText(plant.heureNotification.toString())
        binding.editFrequencyTxt.setText(plant.frequenceNotif.toString())

        binding.deleteBtn.setOnClickListener {
            amaFloraViewModel.deleteOwned(plant.id)

        }

        binding.button2.setOnClickListener {
            showTimePickerDialog()
        }
        amaFloraViewModel.deleteResponse.observe(viewLifecycleOwner, Observer {
                response ->
            Log.i("delete", "line 88 ${response.message}")
            if (response.message == "Plante supprimée"){
                Snackbar.make(view, "Plante supprimée", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_ownedPlantsDetailFragment_to_profileFragment)
            }
        })

        binding.button4!!.setOnClickListener { updatePlant() }
        amaFloraViewModel.updateReponse.observe(viewLifecycleOwner, Observer { resp ->
            if (resp.id != null) {
                Snackbar.make(view, "${resp.surnom} updated", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_ownedPlantsDetailFragment_to_profileFragment)
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePlant() {
        val currentDate = LocalDate.now().toString()
        val updatePlantBody = UpdatePlantBody(currentDate, binding.description.text.toString(), binding.editFrequencyTxt.text.toString().toInt(), binding.timeTxt.text.toString(), binding.plantNom.text.toString())
        amaFloraViewModel.updatePlant(args.plant.id, updatePlantBody)
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
                binding.timeTxt?.setText(selectedTime)

                Log.i("TIME", "$selectedTime")
            }, hour, minute, true
        )

        timePickerDialog.show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OwnedPlantsDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OwnedPlantsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}