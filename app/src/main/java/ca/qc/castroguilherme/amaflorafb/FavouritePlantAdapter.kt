package ca.qc.castroguilherme.amaflorafb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.ListPlantsItem
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import ca.qc.castroguilherme.amaflorafb.models.plantdbmodel.Data
import com.bumptech.glide.Glide


class FavouritePlantAdapter(): RecyclerView.Adapter<FavouritePlantAdapter.PlantViewHolder>() {

    private var plants: List<Plant> = emptyList()

    private lateinit var onItemClickListener: ((Plant) -> Unit)

     lateinit var onDeleteClickListener: ((Plant) -> Unit)
    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: ImageView = itemView.findViewById(R.id.imgFav)
        val plantName: TextView = itemView.findViewById(R.id.textViewName)

        var deleteBtn : Button = itemView.findViewById(R.id.button3)

    }
//
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_item, parent, false)
        return PlantViewHolder(view)
    }
    fun setPlants(plants: List<Plant>) {
        this.plants = plants
        notifyDataSetChanged()
    }
//
    override fun getItemCount() = plants.size


    fun setOnItemClickListener(listener: (Plant) -> Unit){
        onItemClickListener = listener
    }
//
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {

        val plant = plants[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(plant.imageUrl)
                .placeholder(R.drawable.baseline_forest_24)
                .error(R.drawable.baseline_forest_24)
                .into(holder.plantImage)

        }
        holder.plantName.text = plant.nomCommun

    holder.itemView.setOnClickListener {
        onItemClickListener(plant)
    }

    holder.deleteBtn.setOnClickListener {
        onDeleteClickListener.invoke(plant)
    }
    }
}