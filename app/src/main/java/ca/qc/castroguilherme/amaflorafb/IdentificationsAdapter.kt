package ca.qc.castroguilherme.amaflorafb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Identification
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.TimeZone


class IdentificationsAdapter() : RecyclerView.Adapter<IdentificationsAdapter.PlantViewHolder>() {

    private var identifications: List<Identification> = emptyList()

    lateinit var onDeleteClickListener: ((Identification) -> Unit)

//    private lateinit var onItemClickListener: ((Data) -> Unit)

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: ImageView = itemView.findViewById(R.id.identification_image)
        val plantName: TextView = itemView.findViewById(R.id.identification_name)

        var date: TextView = itemView.findViewById(R.id.date_txt)


        val dltBtn : FloatingActionButton = itemView.findViewById(R.id.floatingActionButton3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.identification_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int = identifications.size

    fun setIdentifications(identifications: List<Identification>) {
        this.identifications = identifications
        notifyDataSetChanged()
    }

//    fun setOnItemClickListener(listener: (Data) -> Unit){
//        onItemClickListener = listener
//    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val identification = identifications[position]
        holder.itemView.apply {

            Glide.with(this)
                .load(identification.imageDidentification)
                .placeholder(R.drawable.baseline_forest_24)
                .error(R.drawable.baseline_forest_24)
                .into(holder.plantImage)

            holder.plantName.text = identification.nomScientifique

            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
            val date = isoFormatter.parse(identification.dateDidentification)
            val displayFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
            holder.date.text = displayFormatter.format(date)

        }



        holder.dltBtn.setOnClickListener {
            onDeleteClickListener.invoke(identification)
        }
        }


    }
