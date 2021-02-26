package com.example.facescanner.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.facescanner.MainActivity
import com.example.facescanner.R
import kotlinx.android.synthetic.main.fragment_croped_preview.*
import kotlinx.android.synthetic.main.fragment_preview.*

/**
 * A simple [Fragment] subclass.
 */
class CropedPreviewFragment : Fragment(R.layout.fragment_croped_preview) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val filePath = bundle.getString("FILE_PATH")
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, Uri.parse(filePath))
            img_preview.setImageBitmap(bitmap)
        }
    }
}