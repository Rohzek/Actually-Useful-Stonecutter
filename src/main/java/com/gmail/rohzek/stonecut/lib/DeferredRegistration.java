package com.gmail.rohzek.stonecut.lib;

import com.gmail.rohzek.stonecut.recipe.StonecutterModRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DeferredRegistration 
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Reference.MODID);
	
	public static final RegistryObject<RecipeSerializer<StonecutterModRecipe>> STONECUTTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("stonecutter", () -> StonecutterModRecipe.Serializer.INSTANCE);
	
	public static void register() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		RECIPE_SERIALIZERS.register(bus);
		RECIPE_TYPES.register(bus);
	}
}
