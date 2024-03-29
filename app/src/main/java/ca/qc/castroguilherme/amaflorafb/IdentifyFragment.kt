package ca.qc.castroguilherme.amaflorafb

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog


import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView

import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import ca.qc.castroguilherme.amaflorafb.databinding.FragmentIdentifyBinding
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreateIdentification
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel.AmaFloraViewModelProviderFactory
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.identifyPlantViewModel.PlantIdentificationRepository
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.identifyPlantViewModel.PlantIdentificationViewModel
import ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.identifyPlantViewModel.PlantIdentificationViewModelProviderFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IdentifyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IdentifyFragment : Fragment() {
    private lateinit var binding: FragmentIdentifyBinding
    private var imageCapture: ImageCapture? = null

    //Will be used to send to Retrofit
    //Will be used to send to Retrofit
    val parts: MutableList<MultipartBody.Part> = mutableListOf()


    var image = "nothing yet"
    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    val amaFloraRepository = AmaFloraRepository()
    val amaFloraViewModel: AmaFloraViewModel by lazy {
        ViewModelProvider(this, AmaFloraViewModelProviderFactory(amaFloraRepository)).get(
            AmaFloraViewModel::class.java
        )
    }

    private val plantIdRepo = PlantIdentificationRepository()
    private val plantIdentificationViewModel: PlantIdentificationViewModel by lazy {
        ViewModelProvider(this, PlantIdentificationViewModelProviderFactory(plantIdRepo))
            .get(PlantIdentificationViewModel::class.java)
    }


    private lateinit var cameraContrller: LifecycleCameraController

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentIdentifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateUser()

        if (!hasPermissions(requireActivity().baseContext)) {
            activityResultLauncher.launch(REQUIRED_PERMISSIONS)
        } else {
            startCamera()
        }

        binding.buttonPic.setOnClickListener {
            takePhoto()
//            plantIdentificationViewModel.identificationResponse.observe(viewLifecycleOwner, Observer { response ->
//
//                response.bestMatch?.let { bestMatch ->
//                    // Create the AlertDialog Builder
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setTitle("Identification Result")
//                    builder.setMessage("We believe that your plant belongs to this species : ${response.bestMatch}\nAlthough our accuracy is beyond 97% we still recomend you to be cautious with the results")
//
//                    builder.setPositiveButton("OK") { dialog, which ->
//                    }
//                    val dialog: AlertDialog = builder.create()
//                    dialog.show()
//                    val createIdentification = CreateIdentification(image, response.bestMatch.toString(), firebaseUser.uid)
//                    amaFloraViewModel.postIdentification(createIdentification)
//                    response
//                }
//
//            })
        }



    }
    private fun initiateUser() {
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!


    }


    private fun takePhoto() {
        Log.i("idenSuccess", "picture taken")
//        val name = "images"
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }

        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        cameraContrller.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.i(TAG, "Error taking picture ${exception.message}")
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "Succes taking picture ${outputFileResults.savedUri}"
//                    Toast.makeText(requireActivity().baseContext, "Success taking picture", Toast.LENGTH_LONG)
//                        .show()
                    binding.squaree?.visibility = View.GONE
                    binding.progressBar?.visibility = View.VISIBLE
                    binding.buttonPic.isClickable = false

                    Log.i(TAG, msg)
                    displaySucces(outputFileResults.savedUri)
                    val images = outputFileResults.savedUri?.let {it ->
                        prepareFilePart(requireActivity().baseContext,
                            it
                        )
                    }
                    images?.let { it ->
                        parts.clear()
                        Log.i("idenSuccess", parts.size.toString())
                        parts.add(it) }
                    plantIdentificationViewModel.identify("all", parts)


                    plantIdentificationViewModel.identificationResponse.observe(viewLifecycleOwner, Observer {
                        response ->
                        if(response?.bestMatch != null){

                            binding.squaree?.visibility = View.VISIBLE
                            binding.progressBar?.visibility = View.GONE
                            binding.buttonPic.isClickable = true
                            val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Identification Result")
                    builder.setMessage("We believe that your plant belongs to this species : ${response.bestMatch}\nAlthough our accuracy is beyond 97% we still recomend you to be cautious with the results")

                    builder.setPositiveButton("OK") { dialog, which ->

                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                            image = response.results.first().images.first().url.m
                            Log.i("iden", image)
                    val createIdentification = CreateIdentification(image, response.bestMatch.toString(), firebaseUser.uid)
                    amaFloraViewModel.postIdentification(createIdentification)
                            amaFloraViewModel.identificationResponse.value = null
                        }
                    })

                    plantIdentificationViewModel.identificationError.observe(viewLifecycleOwner, Observer {
                        if (it != null){
                            binding.squaree?.visibility = View.VISIBLE
                            binding.progressBar?.visibility = View.GONE
                            binding.buttonPic.isClickable = true
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setTitle("Identification error")
                            builder.setMessage("${it.toString()}")

                            builder.setPositiveButton("OK") { dialog, which ->

                            }
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    })

                }
            }
        )


    }

    private fun displaySucces(uri: Uri?) {


    }

    fun prepareFilePart(context: Context, fileUri: Uri): MultipartBody.Part {
        // Create a temporary file in your app's cache directory
        val file = File(context.cacheDir, "temp_image")
        val inputStream = context.contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        // Create RequestBody instance from file
        val requestFile = RequestBody.create(
            "image/jpeg".toMediaTypeOrNull(),
            file
        )

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            //Handle permission granted rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false) {
                    permissionGranted = false
                }
                if (!permissionGranted) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Permission")
                    builder.setMessage("We need permission to access your camera to identify the plants.\nIf you don't grant access you won't be able to identify plants.")

                    builder.setPositiveButton("OK") { dialog, which ->

                    }
                    val dialog: AlertDialog = builder.create()
                    hasPermissions(requireContext())
//                    findNavController().navigate(R.id.action_identifyFragment2_to_profileFragment)
                } else {
                    startCamera()
                }
            }
        }
    private fun startCamera() {

        val previewView: PreviewView = binding.viewFinder
        cameraContrller = LifecycleCameraController(requireActivity().baseContext)
        cameraContrller.bindToLifecycle(this)

        previewView.controller = cameraContrller
    }

    companion object {
        private const val TAG = "FotoTag"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE

            )
//                .apply {
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                }
//            }
            .toTypedArray()

        fun hasPermissions(context: Context) = Companion.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IdentifyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IdentifyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}