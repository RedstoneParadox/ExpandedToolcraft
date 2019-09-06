package redstoneparadox.expandedtoolcraft.materials

import redstoneparadox.expandedtoolcraft.ExpandedToolcraft
import redstoneparadox.expandedtoolcraft.materials.PartMaterial

object PartMaterials {

    private val materialsMap: HashMap<String, PartMaterial> = hashMapOf()

    val WOOD_MATERIAL = PartMaterial(ExpandedToolcraft.ID, "wood")

    fun initMaterials() {
        registerMaterial(WOOD_MATERIAL)
    }

    fun registerMaterial(material: PartMaterial) {
        materialsMap["${material.getNamespace()}:${material.getMaterialPrefix()}"] = material
    }

    fun get(id: String): PartMaterial? {
        return materialsMap[id]
    }

    fun getMaterials(): List<PartMaterial> {
        return ArrayList(materialsMap.values)
    }
}