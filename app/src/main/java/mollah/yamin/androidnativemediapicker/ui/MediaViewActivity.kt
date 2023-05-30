package mollah.yamin.androidnativemediapicker.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mollah.yamin.androidnativemediapicker.databinding.MediaViewActivityBinding
import mollah.yamin.androidnativemediapicker.ui.adapters.MediaRecyclerAdapter
import mollah.yamin.androidnativemediapicker.utils.BitmapUtils
import mollah.yamin.androidnativemediapicker.utils.MEDIA_SELECTION_TYPE

class MediaViewActivity : AppCompatActivity() {

    private lateinit var binding: MediaViewActivityBinding
    private lateinit var singleImagePickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var multipleImagePickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var singleVideoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var multipleVideoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var singleMediaPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var multipleMediaPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var adapter: MediaRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MediaViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MediaRecyclerAdapter()
        binding.recyclerView.adapter = adapter

        singleImagePickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            /*
             * Callback is invoked after the user selects an image file or
             * closes the photo picker */
            if (uri == null) return@registerForActivityResult

            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = BitmapUtils.getBitmapFromUri(uri, this@MediaViewActivity)
                runOnUiThread {
                    binding.materialCardView.visibility = View.VISIBLE
                    binding.preview.setImageBitmap(bitmap)
                }
            }
        }

        multipleImagePickerLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uriList ->
            /*
             * Callback is invoked after the user selects single or multiple image files or
             * closes the photo picker */
            if (uriList == null) return@registerForActivityResult
            binding.recyclerView.visibility = View.VISIBLE
            setData(uriList)
        }

        singleVideoPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            /*
             * Callback is invoked after the user selects a video file or
             * closes the video picker */
            if (uri == null) return@registerForActivityResult

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                CoroutineScope(Dispatchers.IO).launch {
                    // Loading thumbnail of that video
                    val bitmap = applicationContext.contentResolver.loadThumbnail(uri, Size(640, 480), null)
                    runOnUiThread {
                        binding.icPlay.visibility = View.VISIBLE
                        binding.materialCardView.visibility = View.VISIBLE
                        binding.preview.setImageBitmap(bitmap)
                    }
                }
            }
        }

        multipleVideoPickerLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3 /* Max Limit */ )) { uriList ->
            /*
             * Callback is invoked after the user selects single or multiple video files or
             * closes the video picker */
            if (uriList == null) return@registerForActivityResult
            binding.recyclerView.visibility = View.VISIBLE
            setData(uriList)
        }

        singleMediaPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            /*
             * Callback is invoked after the user selects a media file or
             * closes the media picker */
            if (uri == null) return@registerForActivityResult
            val type = applicationContext.contentResolver.getType(uri) ?: return@registerForActivityResult
            val typeArr = type.split("/")

            CoroutineScope(Dispatchers.IO).launch {
                if (typeArr.isNotEmpty() && typeArr.first() == "image") {
                    val bitmap = BitmapUtils.getBitmapFromUri(uri, this@MediaViewActivity)
                    runOnUiThread {
                        binding.materialCardView.visibility = View.VISIBLE
                        binding.preview.setImageBitmap(bitmap)
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Loading thumbnail of that video
                    val bitmap = applicationContext.contentResolver.loadThumbnail(uri, Size(640, 480), null)
                    runOnUiThread {
                        binding.icPlay.visibility = View.VISIBLE
                        binding.materialCardView.visibility = View.VISIBLE
                        binding.preview.setImageBitmap(bitmap)
                    }
                }
            }

        }

        multipleMediaPickerLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(6 /* Max Limit */ )) { uriList ->
            /*
             * Callback is invoked after the user selects single or multiple media files or
             * closes the photo picker */
            if (uriList == null) return@registerForActivityResult
            binding.recyclerView.visibility = View.VISIBLE
            setData(uriList)
        }

        when(intent.getIntExtra(MEDIA_SELECTION_TYPE, 0)) {
            1 -> singleImagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            2 -> multipleImagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            3 -> singleVideoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
            4 -> multipleVideoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
            5 -> singleMediaPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            6 -> multipleMediaPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }

    private fun setData(uriList: List<Uri>) {
        binding.loader.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val bitmaps: ArrayList<Pair<Bitmap, String>> = ArrayList()
            for (uri in uriList) {
                val type = binding.root.context.contentResolver.getType(uri) ?: return@launch
                val typeArr = type.split("/")
                if (typeArr.isNotEmpty() && typeArr.first() == "image") {
                    bitmaps.add(Pair(BitmapUtils.getBitmapFromUri(uri, binding.root.context), "image"))
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Loading thumbnail of that video
                    val bitmap = binding.root.context.contentResolver.loadThumbnail(uri, Size(640, 480), null)
                    bitmaps.add(Pair(bitmap, "video"))
                }
            }
            runOnUiThread {
                adapter.setData(bitmaps)
                binding.loader.visibility = View.INVISIBLE
            }
        }
    }
}