package redstoneparadox.expandedtoolcraft.item

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

class PartItem(private val name : String) : Item(Item.Settings().group(ItemGroup.TOOLS)) {

    fun getPartName(): String {
        return name
    }
}