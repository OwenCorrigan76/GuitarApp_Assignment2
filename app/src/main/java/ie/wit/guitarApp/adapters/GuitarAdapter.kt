package ie.wit.guitarApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.CardGuitarBinding
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.utils.customTransformation

interface GuitarClickListener {
    fun onGuitarClick(guitar: GuitarAppModel)
}

class GuitarAdapter constructor(
    private var guitars: ArrayList<GuitarAppModel>,
    private val listener: GuitarClickListener, private val readOnly: Boolean
) :
    RecyclerView.Adapter<GuitarAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGuitarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding, readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val guitar = guitars[holder.adapterPosition]
        holder.bind(guitar, listener)
    }

    fun removeAt(position: Int) {
        guitars.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = guitars.size

    inner class MainHolder(val binding: CardGuitarBinding, private val readOnly: Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(guitar: GuitarAppModel, listener: GuitarClickListener) {
            // binding.valuation.text = ("Valuation: €" + guitar.valuation.toDouble().toString())
            // binding.guitarMake.text = ("Make: " + guitar.guitarMake)
            // binding.guitarModel.text = ("Model: " + guitar.guitarModel)
            // binding.dateView.text = ("Manufactured: " + guitar.manufactureDate)
            // Picasso.get().load(guitar.image).resize(200, 200).into(binding.imageIcon)
            //   binding.root.setOnClickListener() }

            // update guitar (in layout) object info with dataBinding
            binding.guitar = guitar
            binding.root.tag = guitar

            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            Picasso.get().load(guitar.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
           /* Picasso.get().load(guitar.image.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon2)*/
            binding.root.setOnClickListener { listener.onGuitarClick(guitar) }
            binding.executePendingBindings()

        }
    }
}


