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
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentProfileBinding
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import com.bumptech.glide.Glide
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
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding

    val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel: AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
        )
    }

    val ownedPlantAdapter = OwnedPlantAdapter()

    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateUser()

        binding.hello.setText("${binding.hello.text} ${firebaseUser.displayName}")

        binding.imageView3.apply {
            Glide.with(this).load(firebaseUser.photoUrl).into(binding.imageView3)

        }
        ownedPlantAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("plant", it) }
            findNavController().navigate(
                R.id.action_profileFragment_to_ownedPlantsDetailFragment,
                bundle
            )
        }

        seeUserInfo()
        displayOwnedPlants()

    }

    private fun displayOwnedPlants() {
        amaFloraViewModel.getUserPlants(firebaseUser.uid)
        amaFloraViewModel.userPlantsReponse.observe(viewLifecycleOwner, Observer { response ->
            if (!response.isEmpty()) {
                binding.ownedPlantsRv.adapter = ownedPlantAdapter
                ownedPlantAdapter.setPlants(response)
            }
        })
    }

    private fun seeUserInfo() {
        binding.imageView3.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userInfoFragment)
        }
    }

    private fun initiateUser() {
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!
        Log.i("FBTEST", "${firebaseUser.uid}")

    }


    override fun onStart() {
        super.onStart()


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}