package redstoneparadox.expandedtoolcraft.client.render

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.BakedQuad
import net.minecraft.client.render.model.json.JsonUnbakedModel
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.Sprite
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.ExtendedBlockView
import redstoneparadox.expandedtoolcraft.item.ModularToolItem
import redstoneparadox.expandedtoolcraft.item.PartItem
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.util.*
import java.util.function.Supplier

class DynamicToolPartBakedModel : BakedModel, FabricBakedModel {

    val DEFAULT_ITEM_TRANSFORMS = loadTransformFromJson(Identifier("minecraft:models/item/generated"))

    override fun isVanillaAdapter(): Boolean = false

    override fun isBuiltin(): Boolean = false

    override fun getTransformation(): ModelTransformation = DEFAULT_ITEM_TRANSFORMS!!;

    override fun hasDepthInGui(): Boolean = false

    override fun useAmbientOcclusion(): Boolean = true

    override fun getSprite(): Sprite = MinecraftClient.getInstance().spriteAtlas.getSprite(Identifier("modulargear:item/blank"))

    override fun getQuads(var1: BlockState?, var2: Direction?, var3: Random?): MutableList<BakedQuad> = mutableListOf()

    override fun getItemPropertyOverrides(): ModelItemPropertyOverrideList = ModelItemPropertyOverrideList.EMPTY

    override fun emitItemQuads(stack: ItemStack, randSupplier: Supplier<Random>, context: RenderContext) {
        val item = stack.item
        if (item is PartItem) {
            val id = item.resolveModelID(stack)
            context.fallbackConsumer().accept(MinecraftClient.getInstance().bakedModelManager.getModel(ModelIdentifier(id, "inventory")))
        }
    }

    override fun emitBlockQuads(p0: ExtendedBlockView?, p1: BlockState?, p2: BlockPos?, p3: Supplier<Random>?, p4: RenderContext?) {}

    fun loadTransformFromJson(location: Identifier): ModelTransformation? {
        return try {
            JsonUnbakedModel.deserialize(getReaderForResource(location)).transformations
        } catch (exception: IOException) {
            exception.printStackTrace()
            null
        }

    }

    @Throws(IOException::class)
    fun getReaderForResource(location: Identifier): Reader {
        val file = Identifier(location.namespace, location.path + ".json")
        val resource = MinecraftClient.getInstance().resourceManager.getResource(file)
        return BufferedReader(InputStreamReader(resource.inputStream, Charsets.UTF_8))
    }

}