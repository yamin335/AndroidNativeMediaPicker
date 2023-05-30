package mollah.yamin.androidnativemediapicker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mollah.yamin.androidnativemediapicker.R
import mollah.yamin.androidnativemediapicker.databinding.MainActivityBinding
import mollah.yamin.androidnativemediapicker.utils.MEDIA_SELECTION_TYPE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSingleImage.setOnClickListener {
            val nIntent = Intent(this@MainActivity, MediaViewActivity::class.java)
            nIntent.putExtra(MEDIA_SELECTION_TYPE, 1)
            startActivity(nIntent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        binding.btnMultipleImage.setOnClickListener {
            val nIntent = Intent(this@MainActivity, MediaViewActivity::class.java)
            nIntent.putExtra(MEDIA_SELECTION_TYPE, 2)
            startActivity(nIntent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        binding.btnSingleVideo.setOnClickListener {
            val nIntent = Intent(this@MainActivity, MediaViewActivity::class.java)
            nIntent.putExtra(MEDIA_SELECTION_TYPE, 3)
            startActivity(nIntent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        binding.btnMultipleVideo.setOnClickListener {
            val nIntent = Intent(this@MainActivity, MediaViewActivity::class.java)
            nIntent.putExtra(MEDIA_SELECTION_TYPE, 4)
            startActivity(nIntent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        binding.btnSingleMedia.setOnClickListener {
            val nIntent = Intent(this@MainActivity, MediaViewActivity::class.java)
            nIntent.putExtra(MEDIA_SELECTION_TYPE, 5)
            startActivity(nIntent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        binding.btnMultipleMedia.setOnClickListener {
            val nIntent = Intent(this@MainActivity, MediaViewActivity::class.java)
            nIntent.putExtra(MEDIA_SELECTION_TYPE, 6)
            startActivity(nIntent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }
}