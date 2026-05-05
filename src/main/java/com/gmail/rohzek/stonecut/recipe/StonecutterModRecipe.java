package com.gmail.rohzek.stonecut.recipe;

import com.gmail.rohzek.stonecut.lib.ConfigurationManager;
import com.gmail.rohzek.stonecut.lib.DeferredRegistration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class StonecutterModRecipe extends StonecutterRecipe {
    private final String difficulty;

    public StonecutterModRecipe(String group, Ingredient input, ItemStack result, String difficulty) {
        super(group, input, result);
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return ConfigurationManager.GENERAL.difficulty.get().equalsIgnoreCase(this.difficulty)
                && super.matches(input, level);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DeferredRegistration.STONECUTTER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DeferredRegistration.STONECUTTER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<StonecutterModRecipe> {
        public static final MapCodec<StonecutterModRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                Codec.STRING.optionalFieldOf("difficulty", "normal").forGetter(StonecutterModRecipe::getDifficulty)
        ).apply(instance, StonecutterModRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, StonecutterModRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
                        Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.ingredient,
                        ItemStack.STREAM_CODEC, recipe -> recipe.result,
                        ByteBufCodecs.STRING_UTF8, StonecutterModRecipe::getDifficulty,
                        StonecutterModRecipe::new
                );

        @Override
        public MapCodec<StonecutterModRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StonecutterModRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
