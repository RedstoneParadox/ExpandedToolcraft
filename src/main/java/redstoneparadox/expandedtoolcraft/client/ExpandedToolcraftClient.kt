package redstoneparadox.expandedtoolcraft.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.model.ModelAppender
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelVariantProvider
import net.minecraft.resource.ResourceManager
import net.minecraft.client.render.model.ModelBakeSettings
import net.minecraft.client.texture.Sprite
import net.minecraft.client.render.model.ModelLoader
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.util.Identifier
import redstoneparadox.expandedtoolcraft.ExpandedToolcraft
import redstoneparadox.expandedtoolcraft.client.render.DynamicToolBakedModel
import redstoneparadox.expandedtoolcraft.item.Items
import redstoneparadox.expandedtoolcraft.parts.PartMaterials
import java.util.function.Consumer
import java.util.function.Function


object ExpandedToolcraftClient : ClientModInitializer {

    override fun onInitializeClient() {
        val materials = PartMaterials.getMaterials()
        val parts = Items.getParts()
        val toolIDs = Items.getToolIDs()

        ModelLoadingRegistry.INSTANCE.registerVariantProvider { resourceManager ->
            ModelVariantProvider { modelIdentifier, modelProviderContext ->

                for (id in toolIDs) {
                    if (modelIdentifier.namespace == id.namespace && modelIdentifier.path == id.path) {
                        return@ModelVariantProvider object : UnbakedModel {
                            override fun bake(var1: ModelLoader?, var2: Function<Identifier, Sprite>?, var3: ModelBakeSettings?): BakedModel? = DynamicToolBakedModel()

                            override fun getModelDependencies(): MutableCollection<Identifier> = mutableListOf()

                            override fun getTextureDependencies(var1: Function<Identifier, UnbakedModel>?, var2: MutableSet<String>?): MutableCollection<Identifier> = mutableListOf()

                        }
                    }
                }
                return@ModelVariantProvider null
            }
        }

        ModelLoadingRegistry.INSTANCE.registerAppender { manager, consumer ->
            for (material in materials) {
                for (part in parts) {
                    consumer.accept(ModelIdentifier(Identifier("${material.getNamespace()}:${material.getMaterialPrefix()}_${part.getPartName()}"), "inventory"))
                }
            }
        }
    }
}