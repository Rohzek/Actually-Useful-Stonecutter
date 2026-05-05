package com.gmail.rohzek.stonecut.lib;

import com.gmail.rohzek.stonecut.recipe.StonecutterModRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredRegistration {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Reference.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Reference.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<StonecutterModRecipe>> STONECUTTER_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("stonecutter", StonecutterModRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<StonecutterModRecipe>> STONECUTTER_RECIPE_TYPE =
            RECIPE_TYPES.register("stonecutter", () -> new RecipeType<StonecutterModRecipe>() {
                @Override
                public String toString() {
                    return Reference.MODID + ":stonecutter";
                }
            });

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }
}
