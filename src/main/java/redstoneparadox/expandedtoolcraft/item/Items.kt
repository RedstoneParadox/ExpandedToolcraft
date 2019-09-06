package redstoneparadox.expandedtoolcraft.item

import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import redstoneparadox.expandedtoolcraft.ExpandedToolcraft

object Items {

    val HANDLE_ITEM = PartItem("handle")
    val PICKAXE_HEAD_ITEM = PartItem("pickaxe_head")

    val PICKAXE_ITEM = ModularToolItem(hashMapOf("head" to PICKAXE_HEAD_ITEM, "handle" to HANDLE_ITEM))

    fun initItems() {
        register("handle", HANDLE_ITEM)
        register("pickaxe_head", PICKAXE_HEAD_ITEM)

        register("pickaxe", PICKAXE_ITEM)
    }

    private fun register(id: String, item: Item) {
        Registry.register(Registry.ITEM, "${ExpandedToolcraft.ID}:${id}", item)
    }
}