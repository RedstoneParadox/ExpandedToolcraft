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

    fun resolveModelID(stack: ItemStack): Identifier {
        val materialID = stack.tag?.getString("material") ?: return Identifier("minecraft:missingno")
        val material = PartMaterials.get(materialID)
        if (material != null) {
            val partID = "${material.getMaterialPrefix()}_$name"
            val partNamespace = material.getNamespace()
            return Identifier(partNamespace, partID)
        }
        return Identifier("minecraft:missingno")
    }
    
}