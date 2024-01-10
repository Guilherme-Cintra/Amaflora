package ca.qc.castroguilherme.amaflorafb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.qc.castroguilherme.amaflorafb.models.Data
import com.bumptech.glide.Glide

class DiscoverAdapter() : RecyclerView.Adapter<DiscoverAdapter.PlantViewHolder>() {

    private var discoverPlants: List<Data> = emptyList()

    private lateinit var onItemClickListener: ((Data) -> Unit)

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: ImageView = itemView.findViewById(R.id.image_plant)
        val plantName: TextView = itemView.findViewById(R.id.plant_common_name)
//        var scientificName: TextView = itemView.findViewById(R.id.scientific_lbl)
        var sunReq: TextView = itemView.findViewById(R.id.sun_req)
        var waterReq: TextView = itemView.findViewById(R.id.water_req)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.discover_plants_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int = discoverPlants.size

    fun setDiscoverPlants(discoverPlants: List<Data>) {
        this.discoverPlants = discoverPlants
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Data) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = discoverPlants[position]
        holder.itemView.apply {

            Glide.with(this)
                .load(plant.defaultImage?.thumbnail)
                .placeholder(R.drawable.baseline_forest_24)
                .error(R.drawable.baseline_forest_24)
                .into(holder.plantImage)

            holder.plantName.text = plant.commonName
            holder.sunReq.text = sun(plant.sunlight)
//            holder.scientificName.text = plant.scientificName.first()
            holder.waterReq.text = watering(plant.watering)
        }



        holder.itemView.setOnClickListener {
            onItemClickListener(plant)
        }
    }

    private fun watering(water:String) :String{
        when(water) {
            "Average" -> {
                return "Average"
            }

            "Frequent" -> {
                return "Frequent"
            }

        }

        return "Rarely"
    }

    private fun sun(plants: List<String>) :String {
        when (plants.size) {
            1 -> {
                if (plants.contains("full sun")) {
                    return "4 to 6 hours "
                } else  if (plants.contains("part shade") || plants.contains("part sun/part shade")) {
                    return "2 hours"
                } else {
                    return "careful"
                }
            }

            2 -> {
                if (plants.contains("full sun") && plants.contains("part shade") || plants.contains("full sun") && plants.contains("part sun/part shade")) {
                    return  "2 to 6 hours"

                } else if (plants.contains("part shade") && plants.contains("filtered shade")) {
                    return  "0 to 2 hours"
                }
            }
            3 -> {
                return  "no worries"
            }
        }
        return  "no worries"
    }


}