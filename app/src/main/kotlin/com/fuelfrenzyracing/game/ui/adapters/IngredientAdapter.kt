package com.fuelfrenzyracing.game.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fuelfrenzyracing.game.data.models.FuelIngredient
import com.fuelfrenzyracing.game.databinding.ItemIngredientBinding

class IngredientAdapter(
    private val onSelectionChanged: (FuelIngredient, Boolean) -> Unit
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var ingredients = listOf<FuelIngredient>()
    private val selectedIds = mutableSetOf<String>()

    fun updateData(newIngredients: List<FuelIngredient>) {
        ingredients = newIngredients
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedIds.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount() = ingredients.size

    inner class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: FuelIngredient) {
            binding.tvIngredientName.text = ingredient.name
            binding.tvCategory.text = ingredient.category.name
            binding.tvDescription.text = ingredient.description
            binding.cbSelect.isChecked = ingredient.id in selectedIds
            binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedIds.add(ingredient.id)
                } else {
                    selectedIds.remove(ingredient.id)
                }
                onSelectionChanged(ingredient, isChecked)
            }
        }
    }
}
