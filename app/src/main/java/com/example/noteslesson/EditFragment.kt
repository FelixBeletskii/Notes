package com.example.noteslesson

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.Manifest
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.noteslesson.database.MyDBManager
import com.example.noteslesson.databinding.FragmentEditBinding
import com.example.noteslesson.databinding.FragmentMainBinding

class EditFragment : Fragment() {
    lateinit var dbManager: MyDBManager


    private lateinit var binding: FragmentEditBinding
    var index = 0
    private val imageList = listOf(
        R.drawable.ic_add, R.drawable.ic_android, R.drawable.ic_message
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbManager = MyDBManager(requireContext())
        dbManager.openDB()
        initListeners()


    }


    private fun initListeners(){
        val controller = findNavController()

        with(binding) {
            btnOpenImage.setOnClickListener {
                openImageEdit()
            }
            btnSelect.setOnClickListener {
                index++
                if (index<=imageList.size-1){
                    imPhoto.setImageResource(imageList[index])
                }
                else {
                     index = 0
                     imPhoto.setImageResource(imageList[index])
                }
            }
            btnDelete.setOnClickListener {
                closeImageEdit()

            }
            btnSave.setOnClickListener {
                var image: String? = null
                val title = edTitle.text.toString()
                val content = edDesc.text.toString()
                if(binding.imageSelectContainer.visibility == View.VISIBLE){ image = imageList[index].toString() }
                if(title!=""&&content!=""){
                    dbManager.insertDB(title,content,image)
                }

                controller.navigate(R.id.mainFragment)
            }

        }
    }

    private fun openImageEdit() {
        with(binding) {
            imageSelectContainer.visibility = View.VISIBLE
            btnOpenImage.visibility = View.GONE
        }
    }

    private fun closeImageEdit() {
        with(binding) {
            imageSelectContainer.visibility = View.GONE
            btnOpenImage.visibility = View.VISIBLE
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EditFragment()
    }
}
