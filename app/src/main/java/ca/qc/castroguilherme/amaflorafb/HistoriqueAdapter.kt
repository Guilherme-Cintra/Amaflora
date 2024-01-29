package ca.qc.castroguilherme.amaflorafb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import ca.qc.castroguilherme.amaflorafb.models.plantdbmodel.Data
import com.bumptech.glide.Glide


class HistoriqueAdapter(): RecyclerView.Adapter<HistoriqueAdapter.PlantViewHolder>() {

    private var plants: List<Plant> = emptyList()
    private lateinit var onItemClickListener: ((Plant) -> Unit)
    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: ImageView = itemView.findViewById(R.id.plant_img)
        val plantName: TextView = itemView.findViewById(R.id.plantName)
//        //        var scientificName: TextView = itemView.findViewById(R.id.scientific_lbl)
        var sunReq: TextView = itemView.findViewById(R.id.water_requirement)
        var waterReq: TextView = itemView.findViewById(R.id.textView12)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.historique_item, parent, false)
        return PlantViewHolder(view)
    }
    fun setPlants(plants: List<Plant>) {
        this.plants = plants
        notifyDataSetChanged()
    }

    override fun getItemCount() = plants.size

    fun setOnItemClickListener(listener: (Plant) -> Unit){
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
        holder.plantName.text = plant.nomCommun
        holder.itemView.setOnClickListener {
            onItemClickListener(plant)
        }

        holder.sunReq.text = plant.soleil
        holder.waterReq.text = plant.freqEau + " " + plant.uniteFreqEau
    }
}