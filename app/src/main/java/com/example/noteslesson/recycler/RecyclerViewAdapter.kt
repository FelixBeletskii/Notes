package com.example.noteslesson.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.noteslesson.R
import com.example.noteslesson.database.MyDBManager
import com.example.noteslesson.databinding.ItemBinding

class RecyclerViewAdapter(val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    var itemList = ArrayList<Item>()


    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        private var isClickable = true

        val binding = ItemBinding.bind(item)



        fun bind(item: Item, listener: Listener) {
            with(binding) {
                tvTitle.text = item.title
                item.image?.toInt()?.let { ivImage.setImageResource(it) }
                itemView.setOnClickListener {
                    if (isClickable) {
                        isClickable = false
                        val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.recycler_anim)
                        itemView.startAnimation(animation)

                        itemView.postDelayed({
                            listener.onClick(item)
                            isClickable = true
                        }, 200)
                    }
                }
            }
            }

        }




      override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return Holder(view)
        }

       override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(itemList[position], listener)

        }

     override   fun getItemCount(): Int {
            return itemList.size
        }


       fun addItem(item: Item) {
            itemList.add(item)
            notifyDataSetChanged()
        }
        fun deleteItem(pos: Int, dbManager: MyDBManager){ // здесь добавляем ДБ менеджер чтобы удалить объект так же из базы данных

                dbManager.removeItemFromDB(itemList[pos].id.toString())
                itemList.removeAt(pos)
                notifyItemRangeChanged(pos,itemList.size)
                notifyItemRemoved(pos)



        }




    interface Listener {
            fun onClick(item: Item)


        }
    }
