package com.example.noteslesson

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteslesson.database.DataModel
import com.example.noteslesson.database.MyDBManager
import com.example.noteslesson.databinding.FragmentMainBinding
import com.example.noteslesson.recycler.Item
import com.example.noteslesson.recycler.RecyclerViewAdapter


class MainFragment : Fragment(), RecyclerViewAdapter.Listener {
    lateinit var dbManager: MyDBManager
    val model : DataModel by activityViewModels()
    val adapter = RecyclerViewAdapter(this)

    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        val controller = findNavController()
        with(binding) {
            if (adapter.itemCount == 0) {
                recyclerView.visibility = View.GONE
                tvNoItems.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                tvNoItems.visibility = View.GONE
            }
            btnAddItem.setOnClickListener {
                controller.navigate(R.id.editFragment)
            }
        }
        model.createdItem.observe(activity as LifecycleOwner,{
            adapter.addItem(it)
        })



    }


    fun init() {
        with(binding) {
            dbManager = MyDBManager(requireContext())
            dbManager.openDB()
            recyclerView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
            val swapHelper = getSwapMG()
            swapHelper.attachToRecyclerView(recyclerView)
            recyclerView.adapter = adapter
            val itemList: ArrayList<Item> = dbManager.readDB()
            for (i in itemList) {
                val item = Item(i.title, i.desc, i.image, i.id)
                adapter.addItem(item)
            }


        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainFragment()
    }

    override fun onClick(item: Item) {
        Log.v("MyLog","Click picked")
    }

    fun getSwapMG(): ItemTouchHelper{
        return ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition, dbManager)
  // добавляем сюда дб менеджер, потому что функция просит его
                        }

        })

    }
}



