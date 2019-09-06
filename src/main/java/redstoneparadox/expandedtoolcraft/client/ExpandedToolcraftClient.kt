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
import java.util.function.Consumer
import java.util.function.Function


object ExpandedToolcraftClient : ClientModInitializer {

    override fun onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerVariantProvider { resourceManager ->
            ModelVariantProvider { modelIdentifier, modelProviderContext ->
                if (modelIdentifier.namespace == ExpandedToolcraft.ID && modelIdentifier.path == "pickaxe") {
                    return@ModelVariantProvider object : UnbakedModel {
                        override fun bake(var1: ModelLoader?, var2: Function<Identifier, Sprite>?, var3: ModelBakeSettings?): BakedModel? = DynamicToolBakedModel()

                        override fun getModelDependencies(): MutableCollection<Identifier> = mutableListOf()

                        override fun getTextureDependencies(var1: Function<Identifier, UnbakedModel>?, var2: MutableSet<String>?): MutableCollection<Identifier> = mutableListOf()

                    }
                }
                else {
                    return@ModelVariantProvider null
                }
            }
        }

        ModelLoadingRegistry.INSTANCE.registerAppender { manager, consumer ->
            consumer.accept(ModelIdentifier(Identifier(ExpandedToolcraft.ID, "wood_handle"), "inventory"))
            consumer.accept(ModelIdentifier(Identifier(ExpandedToolcraft.ID, "wood_pickaxe_head"), "inventory"))
        }
    }
}