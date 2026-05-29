package com.fuelfrenzyracing.game.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fuelfrenzyracing.game.data.database.VehicleEntity
import com.fuelfrenzyracing.game.databinding.ItemVehicleBinding

class VehicleAdapter(
    private val onVehicleClick: (VehicleEntity) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private var vehicles = listOf<VehicleEntity>()

    fun updateData(newVehicles: List<VehicleEntity>) {
        vehicles = newVehicles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = ItemVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount() = vehicles.size

    inner class VehicleViewHolder(private val binding: ItemVehicleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: VehicleEntity) {
            binding.tvVehicleName.text = vehicle.name
            binding.tvVehicleType.text = vehicle.type
            binding.tvBaseSpeed.text = "Speed: ${vehicle.baseSpeed}"
            binding.root.setOnClickListener { onVehicleClick(vehicle) }
        }
    }
}
