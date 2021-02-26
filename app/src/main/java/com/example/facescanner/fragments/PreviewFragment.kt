package com.example.facescanner.fragments

import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.facescanner.MainActivity
import com.example.facescanner.R
import com.example.facescanner.views.ZoomableImageView
import kotlinx.android.synthetic.main.fragment_preview.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.Executor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class PreviewFragment : Fragment(R.layout.fragment_preview) {

    private lateinit var executor:Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {bundle: Bundle ->
            val filePath = bundle.getString("FILE_PATH")
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, Uri.parse(filePath))
            preview_image.setImageBitmap(bitmap)

            btn_cancel.setOnClickListener {
                findNavController().navigateUp()
            }

            btn_crop.setOnClickListener {
                val handlerThread = HandlerThread("FileSave")
                handlerThread.start()
                Handler(handlerThread.looper).post{
                    try {

                        var file = MainActivity.getOutputDirectory(requireContext())
                        file = File(file.absolutePath.plus("cropped_image.png"))


                        val fileOutputStream = FileOutputStream(file)
                        preview_image.croppedImage.compress(
                            Bitmap.CompressFormat.PNG,
                            80,
                            fileOutputStream
                        )

                        val mimeType = MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(file.extension)
                        MediaScannerConnection.scanFile(
                            context,
                            arrayOf(file.absolutePath),
                            arrayOf(mimeType)
                        ) { _, uri ->
                        }

                        activity?.runOnUiThread {
                            val bundle = bundleOf("FILE_PATH" to file.toUri().toString())
                            findNavController().navigate(R.id.preview_to_cropped,bundle)
                        }

                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                }
            }
        }





    }

}