package redstoneparadox.expandedtoolcraft.item

import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import redstoneparadox.expandedtoolcraft.ExpandedToolcraft

object Items {

    private val partItems: ArrayList<PartItem> = arrayListOf()
    private val toolNames: ArrayList<Identifier> = arrayListOf()

    val HANDLE_ITEM = PartItem("handle")
    val PICKAXE_HEAD_ITEM = PartItem("pickaxe_head")

    val PICKAXE_ITEM = ModularToolItem(hashMapOf("head" to PICKAXE_HEAD_ITEM, "handle" to HANDLE_ITEM))

    fun initItems() {
        registerPart("handle", HANDLE_ITEM)
        registerPart("pickaxe_head", PICKAXE_HEAD_ITEM)

        registerTool("pickaxe", PICKAXE_ITEM)
    }

    private fun register(id: String, item: Item) {
        Registry.register(Registry.ITEM, "${ExpandedToolcraft.ID}:${id}", item)
    }

    private fun registerPart(id: String, partItem: PartItem) {
        Registry.register(Registry.ITEM, "${ExpandedToolcraft.ID}:${id}", partItem)
        partItems.add(partItem)
    }

    private fun registerTool(id: String, modularToolItem: ModularToolItem) {
        val fullID = Identifier("${ExpandedToolcraft.ID}:${id}")
        Registry.register(Registry.ITEM, fullID, modularToolItem)
        toolNames.add(fullID)
    }

    fun getParts(): List<PartItem> {
        return ArrayList(partItems)
    }

    fun getToolIDs(): List<Identifier> {
        return ArrayList(toolNames)
    }
}