package ca.qc.castroguilherme.amaflorafb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ca.qc.castroguilherme.amaflorafb.databinding.FragmentUserInfoBinding
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
 * Use the [UserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserInfoFragment : Fragment() {
    private lateinit var binding: FragmentUserInfoBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateUser()
       showInfoUser()
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoFragment_to_profileFragment)
        }
    }

    private fun showInfoUser() {
        binding.imgProfilePic.apply {
            Glide.with(this).load(firebaseUser.photoUrl).into(binding.imgProfilePic)

        }

        binding.textName.text = firebaseUser.displayName
        binding.emailText.text = firebaseUser.email
    }

    private fun initiateUser() {
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!
        Log.i("FBTEST", "${firebaseUser.uid}")

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}