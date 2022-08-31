package com.e.setostory2.add

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.e.setostory2.databinding.ActivityAddStoryBinding
import com.e.setostory2.main.MainActivity
import com.e.setostory2.utils.compress
import com.e.setostory2.utils.createTempFile
import com.e.setostory2.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddStoryActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var currentPhotoPath: String
    private lateinit var viewModel: AddStoryViewModel
    private var getFile : File? = null
    private lateinit var sharedPreferences: SharedPreferences

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result->
        if(result.resultCode == RESULT_OK){
            val image : Uri = result.data?.data as Uri
            val file = uriToFile(image,this@AddStoryActivity)
            getFile = file
            binding.addPrevImageview.setImageURI(image)
        }
    }
    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.addPrevImageview.setImageBitmap(result)
            getFile = myFile
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(AddStoryViewModel::class.java)
        if(askingAllPermission()){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, REQUEST_CODE_PERMISSION)
        }

        viewModInit()
        binding.addCamButton.setOnClickListener{
            takePicture()
        }
        binding.addFileButton.setOnClickListener{
            openGallery()
        }
        binding.addUploadButton.setOnClickListener{

            if(binding.addDescEdttxt.text.isEmpty()){
                Toast.makeText(applicationContext,"Isi Description terlebih dahulu", Toast.LENGTH_SHORT).show()

            }else {
                upload()
            }
        }

    }

    private fun viewModInit(){
        viewModel.addStoryObserver().observe(this) {
            if (it == null) {
                Toast.makeText(applicationContext, "Failed Upload", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
            if (!it.error) {
                val backIntent = Intent(this, MainActivity::class.java)
                this.startActivity(backIntent)
                finish()
            }


        }
    }

    private fun takePicture(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createTempFile(application).also {
            val uri : Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,"com.e.setostory",it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
            launcherIntentCamera.launch(intent)
        }


    }

    private fun upload(){
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if(getFile != null){
            val file = compress(getFile as File)
            val text = binding.addDescEdttxt.text.toString()
            val description = text.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultiPart : MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            viewModel.postStory("Bearer $token",imageMultiPart,description)
        }else {
            Toast.makeText(this@AddStoryActivity, "Silakan masukkan berkas gambar terlebih dahulu.", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSION){
            if(!askingAllPermission()){
                finish()
            }
        }
    }




    private fun openGallery(){
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        val chooser = Intent.createChooser(intent, "Please Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun askingAllPermission() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }


}