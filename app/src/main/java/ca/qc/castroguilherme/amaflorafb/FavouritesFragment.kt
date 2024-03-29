package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentDiscoverBinding
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentFavouritesBinding
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
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentFavouritesBinding

    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel: AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
        )
    }

    val favouritePlantAdapter = FavouritePlantAdapter()
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
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initiateUser()

        displayFavourites()

        val top_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation )


        binding.cardTitle.startAnimation(top_anim)

        deleteFv()


        clickPlant()

    }

    private fun clickPlant() {
        favouritePlantAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("id", it.id)
            }
            findNavController().navigate(R.id.action_favouritesFragment2_to_discoverDetailFragment, bundle)
        }
    }

    private fun updateList() {
        amaFloraViewModel.getWishList(firebaseUser.uid)
        amaFloraViewModel.wishListLiveData.observe(viewLifecycleOwner, Observer {
                plants ->
            if (!(plants?.isEmpty() == true)) {

                binding.recyclerView.adapter = favouritePlantAdapter
                plants?.let { favouritePlantAdapter.setPlants(it) }
            }
        })
    }

    private fun deleteFv() {
        favouritePlantAdapter.onDeleteClickListener = {
            Log.i("delete", "${it.id}")
            amaFloraViewModel.deleteFavourite(it.id)



            amaFloraViewModel.deleteFavResponse.observe(viewLifecycleOwner, Observer {
                    resp ->
                if (resp.messgae == "Favorit supprimée"){
                    view?.let { Snackbar.make(it, "Favorit supprimée", Snackbar.LENGTH_LONG).show() }
                    updateList()
                }
            })
        }

    }

    private fun displayFavourites() {

        amaFloraViewModel.getWishList(firebaseUser.uid)
        amaFloraViewModel.wishListLiveData.observe(viewLifecycleOwner, Observer {
            plants ->
            if (!(plants?.isEmpty() == true)) {

                binding.recyclerView.adapter = favouritePlantAdapter
                plants?.let { favouritePlantAdapter.setPlants(it) }
            }

            else {
                binding.cardNothing.visibility = View.VISIBLE
                val bottom_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation )
                binding.cardNothing.startAnimation(bottom_anim)
                binding.recyclerView.adapter = favouritePlantAdapter
                plants?.let { favouritePlantAdapter.setPlants(it) }
            }
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
         * @return A new instance of fragment FavouritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}