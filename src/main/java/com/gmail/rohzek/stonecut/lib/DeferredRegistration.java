package com.gmail.rohzek.stonecut.lib;

import com.gmail.rohzek.stonecut.recipe.StonecutterModRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DeferredRegistration 
{
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MODID);
	//public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Reference.MODID);
	
	public static final RegistryObject<IRecipeSerializer<StonecutterModRecipe>> STONECUTTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("stonecutter", () -> StonecutterModRecipe.Serializer.INSTANCE);
	
	public static void register() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		RECIPE_SERIALIZERS.register(bus);
		//RECIPE_TYPES.register(bus);
	}
}
