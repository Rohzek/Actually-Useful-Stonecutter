package com.gmail.rohzek.stonecut.events;

import com.gmail.rohzek.stonecut.recipe.StonecutterModRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EventBusEvents 
{
	@SubscribeEvent
	public static void registerRecipeTypes(final RegistryEvent.Register<IRecipeSerializer<?>> event) 
	{
		Registry.register(Registry.RECIPE_TYPE, StonecutterModRecipe.Type.ID, StonecutterModRecipe.Type.INSTANCE);
	}
}