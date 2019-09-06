package redstoneparadox.expandedtoolcraft.parts

class PartMaterial(private val namespace: String, private val prefix: String) {

    fun getNamespace(): String {
        return namespace
    }

    fun getMaterialPrefix(): String {
        return prefix
    }
}