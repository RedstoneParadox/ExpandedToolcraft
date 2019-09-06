package redstoneparadox.expandedtoolcraft.item

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.BakedModelManager
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolItem
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import redstoneparadox.expandedtoolcraft.materials.PartMaterials

// Not finished
class ModularToolItem(private val parts : HashMap<String, PartItem>) : ToolItem(DelegatedToolMaterial(), Settings().group(ItemGroup.TOOLS)) {

    init {
        (material as DelegatedToolMaterial).addDelegate(this)
    }

    fun resolvePartModelIDs(stack: ItemStack): List<Identifier> {
        val ids = arrayListOf<Identifier>()

        //TODO: Warn about this
        val materialsData = stack.getSubTag("materials") ?: return ids
        if (materialsData.isEmpty) return ids

        for (key in parts.keys) {
            val part = parts[key]
            val materialID: String? = materialsData.getString(key)
            val material = if (materialID != null) PartMaterials.get(materialID) else null

            if (part != null && material != null) {
                val namespace = material.getNamespace()
                val partID = "${material.getMaterialPrefix()}_${part.getPartName()}"
                ids.add(Identifier(namespace, partID))
            }
        }

        return ids
    }

    override fun canRepair(itemStack_1: ItemStack?, itemStack_2: ItemStack?): Boolean {
        return false
    }

    fun getDurability(): Int = 1

    /**
     * Tool material for delegating to instances of ModularToolItem
     */
    private class DelegatedToolMaterial : ToolMaterial {
        private var delegate : ModularToolItem? = null

        override fun getRepairIngredient(): Ingredient = Ingredient.EMPTY

        override fun getDurability(): Int = delegate?.getDurability() ?: 1

        override fun getAttackDamage(): Float {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getEnchantability(): Int = 0

        override fun getMiningSpeed(): Float {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getMiningLevel(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun addDelegate(delegate : ModularToolItem) {
            if (this.delegate != null) {
                this.delegate = delegate
            }
        }
    }
}