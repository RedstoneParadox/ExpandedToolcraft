package redstoneparadox.expandedtoolcraft.materials

import net.minecraft.item.ItemStack

class PartMaterial(private val namespace: String, private val prefix: String) {

    private val statsMap: HashMap<String, MaterialStats> = hashMapOf()

    fun getNamespace(): String {
        return namespace
    }

    fun getMaterialPrefix(): String {
        return prefix
    }

    fun canRepair(ingredient: ItemStack): Boolean = statsMap["head"]?.repairItem == ingredient.item ?: false
}