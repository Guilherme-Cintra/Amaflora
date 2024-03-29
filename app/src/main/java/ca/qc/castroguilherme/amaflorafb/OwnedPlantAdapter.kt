package ca.qc.castroguilherme.amaflorafb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.ListPlantsItem
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import ca.qc.castroguilherme.amaflorafb.models.plantdbmodel.Data
import com.bumptech.glide.Glide


class OwnedPlantAdapter(): RecyclerView.Adapter<OwnedPlantAdapter.PlantViewHolder>() {

    private var plants: List<ListPlantsItem> = emptyList()
    private lateinit var onItemClickListener: ((ListPlantsItem) -> Unit)
    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: ImageView = itemView.findViewById(R.id.fotoPlanta)
        val plantName: TextView = itemView.findViewById(R.id.plantNome)
        var  plantDescription: TextView = itemView.findViewById(R.id.textView20Description)
        var  plantHeure: TextView = itemView.findViewById(R.id.heureTkt)
        var  plantFreq: TextView = itemView.findViewById(R.id.frequencyTkt)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.owned_plant_item, parent, false)
        return PlantViewHolder(view)
    }
    fun setPlants(plants: List<ListPlantsItem>) {
        this.plants = plants
        notifyDataSetChanged()
    }

    override fun getItemCount() = plants.size

    fun setOnItemClickListener(listener: (ListPlantsItem) -> Unit){
        onItemClickListener = listener
    }
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {

        val plant = plants[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(plant.imageUrl)
                .placeholder(R.drawable.baseline_forest_24)
                .error(R.drawable.baseline_forest_24)
                .into(holder.plantImage)
        }
        holder.plantName.text = plant.surnom
        holder.plantDescription.setText(plant.descriptionPerso)


        holder.plantFreq.text = "every " + plant.frequenceNotif.toString() + " days"
        holder.plantHeure.text = plant.heureNotification.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener(plant)
        }
    }
}