package com.gmail.rohzek.stonecut.recipe;

import com.gmail.rohzek.stonecut.lib.ConfigurationManager;
import com.gmail.rohzek.stonecut.lib.DeferredRegistration;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
    private static final Codec<Either<ItemStack, Holder<Item>>> RESULT_CODEC = Codec.either(
            ItemStack.STRICT_CODEC,
            ItemStack.ITEM_NON_AIR_CODEC
    );

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
        return RecipeType.STONECUTTING;
    }

    public static class Serializer implements RecipeSerializer<StonecutterModRecipe> {
        public static final MapCodec<StonecutterModRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                RESULT_CODEC.fieldOf("result").forGetter(recipe -> Either.left(recipe.result)),
                Codec.intRange(1, 99).optionalFieldOf("count").forGetter(recipe -> java.util.Optional.empty()),
                Codec.STRING.optionalFieldOf("difficulty", "normal").forGetter(StonecutterModRecipe::getDifficulty)
        ).apply(instance, (group, ingredient, result, count, difficulty) -> new StonecutterModRecipe(
                group,
                ingredient,
                result.map(
                        stack -> {
                            ItemStack resolved = stack.copy();
                            count.ifPresent(resolved::setCount);
                            return resolved;
                        },
                        item -> new ItemStack(item, count.orElse(1))
                ),
                difficulty
        )));

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
