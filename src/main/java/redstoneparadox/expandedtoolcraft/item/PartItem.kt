package redstoneparadox.expandedtoolcraft.item

import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.BakedModelManager
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import redstoneparadox.expandedtoolcraft.parts.PartMaterials

class PartItem(private val name : String) : Item(Item.Settings().group(ItemGroup.TOOLS)) {

    fun getPartName(): String {
        return name
    }

    fun resolveModel(stack: ItemStack, manager: BakedModelManager): BakedModel? {
        val materialID = stack.tag?.getString("material")
                ?: // TODO: Print a warning
                return manager.missingModel

        val material = PartMaterials.get(materialID)

        if (material != null) {
            val partID = "${material.getMaterialPrefix()}_$name"
            val partNamespace = material.getNamespace()
            val modelID = ModelIdentifier(Identifier(partNamespace, partID), "inventory")
            return manager.getModel(modelID)
        }
        //TODO: Print a warning
        return manager.missingModel
    }
}