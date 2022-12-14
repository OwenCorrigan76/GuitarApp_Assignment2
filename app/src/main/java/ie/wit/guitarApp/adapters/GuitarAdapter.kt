package ie.wit.guitarApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.CardGuitarBinding
import ie.wit.guitarApp.models.GuitarAppModel
interface GuitarClickListener {
    fun onGuitarClick(guitar: GuitarAppModel)
}
class GuitarAdapter constructor(
    private var guitars: ArrayList<GuitarAppModel>,
    private val listener: GuitarClickListener
) :
    RecyclerView.Adapter<GuitarAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGuitarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
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

    inner class MainHolder(val binding: CardGuitarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(guitar: GuitarAppModel, listener: GuitarClickListener) {
           // binding.valuation.text = ("Valuation: €" + guitar.valuation.toDouble().toString())
           // binding.guitarMake.text = ("Make: " + guitar.guitarMake)
           // binding.guitarModel.text = ("Model: " + guitar.guitarModel)
           // binding.dateView.text = ("Manufactured: " + guitar.manufactureDate)
            // Picasso.get().load(guitar.image).resize(200, 200).into(binding.imageIcon)
            //   binding.root.setOnClickListener() }
            binding.root.tag = guitar
            binding.guitar = guitar // update with individual guitar info
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onGuitarClick(guitar) }
            binding.executePendingBindings()

        }
    }
}


