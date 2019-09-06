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
import redstoneparadox.expandedtoolcraft.parts.PartMaterials

// Not finished
class ModularToolItem(private val parts : HashMap<String, PartItem>) : ToolItem(DelegatedToolMaterial(), Settings().group(ItemGroup.TOOLS)) {

    init {
        (material as DelegatedToolMaterial).addDelegate(this)
    }

    @Environment(EnvType.CLIENT)
    fun resolvePartModels(stack: ItemStack, manager: BakedModelManager): ArrayList<BakedModel> {
        val models = arrayListOf<BakedModel>()

        val materialsData = stack.getSubTag("materials")
                ?: // Warn about this
                return models

        if (materialsData.isEmpty) {
            // Warn about this
            return models
        }

        for (key in parts.keys) {
            val part = parts[key]
            val materialID: String? = materialsData.getString(key)
            val material = if (materialID != null) PartMaterials.get(materialID) else null

            if (part != null && material != null) {
                val namespace = material.getNamespace()
                val partID = "${material.getMaterialPrefix()}_${part.getPartName()}"
                val modelID = ModelIdentifier(Identifier(namespace, partID), "inventory")
                models.add(manager.getModel(modelID))
            }
            else return models
        }

        return models
    }

    // Code here from getting the repair ingredient from the head material.
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

        override fun getEnchantability(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

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