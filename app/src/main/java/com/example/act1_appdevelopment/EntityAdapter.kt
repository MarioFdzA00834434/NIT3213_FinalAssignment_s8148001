package com.example.act1_appdevelopment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class EntityAdapter(
    private var entities: List<Entity>,
    private val itemClickListener: (Entity) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val entity = entities[position]
        holder.bind(entity, itemClickListener)
    }

    override fun getItemCount(): Int = entities.size

    fun updateData(newEntities: List<Entity>) {
        entities = newEntities
        notifyDataSetChanged()
    }

    class EntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val destinationTextView: TextView = itemView.findViewById(R.id.destination)
        private val countryTextView: TextView = itemView.findViewById(R.id.country)
        private val seasonTextView: TextView = itemView.findViewById(R.id.season)
        private val attractionTextView: TextView = itemView.findViewById(R.id.attraction)

        fun bind(entity: Entity, clickListener: (Entity) -> Unit) {
            destinationTextView.text = entity.destination
            countryTextView.text = entity.country
            seasonTextView.text = entity.bestSeason
            attractionTextView.text = entity.popularAttraction

            itemView.setOnClickListener { clickListener(entity) }
        }
    }
}