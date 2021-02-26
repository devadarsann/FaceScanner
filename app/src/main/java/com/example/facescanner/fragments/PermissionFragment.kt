package com.example.facescanner.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.facescanner.R

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

/**
 * A simple [Fragment] subclass.
 */
class PermissionFragment : Fragment(R.layout.fragment_permission) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Navigate()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Navigate()
            } else {
                val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
                dialog.setTitle("Camera permission is required")
                dialog.setMessage("Please grant permission for Camera in order to use this application")
            }
        }

    private fun Navigate() {
        Navigation.findNavController(requireActivity(), R.id.rootView)
            .navigate(R.id.permission_to_camera)
    }
}