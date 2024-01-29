package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentHistoryBinding
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
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
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHistoryBinding


    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel: AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
        )
    }

    val identificationsAdapter = IdentificationsAdapter()
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
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val top_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation )


        binding.cardIdentifications.startAnimation(top_anim)
        binding.imageCamera.startAnimation(top_anim)
//        binding.cardNothing.startAnimation(bottom_anim)

        initiateUser()

        displayIdentifications()





        deleteIdentification()

    }

    private fun deleteIdentification() {
        identificationsAdapter.onDeleteClickListener = {
            amaFloraViewModel.deleteIdentification(it.id)
            view?.let { it1 -> Snackbar.make(it1, "Identification deleted", Snackbar.LENGTH_SHORT).show() }
            displayIdentifications()
        }
    }

    private fun displayIdentifications() {
        amaFloraViewModel.getIdentificationList(firebaseUser.uid)
        amaFloraViewModel.listIdentifications.observe(viewLifecycleOwner, Observer {
            response ->
//            if (!response.identifications.isEmpty() == true) {
                binding.recyclerViewIdentifications.adapter = identificationsAdapter
                identificationsAdapter.setIdentifications(response.identifications.reversed())
//            } else {
//                val bottom_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation )
//                binding.cardNothing.startAnimation(bottom_anim)
//            }
        })
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
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}