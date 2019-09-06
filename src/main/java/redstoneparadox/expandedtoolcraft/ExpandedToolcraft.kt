package redstoneparadox.expandedtoolcraft

import net.fabricmc.api.ModInitializer
import redstoneparadox.expandedtoolcraft.item.Items
import redstoneparadox.expandedtoolcraft.parts.PartMaterials

object ExpandedToolcraft : ModInitializer {

    val ID = "expandedtoolcraft"

    override fun onInitialize() {
        println("Hello, world!")
        PartMaterials.initMaterials()
        Items.initItems()
    }
}