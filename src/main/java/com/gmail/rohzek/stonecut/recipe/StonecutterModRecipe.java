package com.gmail.rohzek.stonecut.recipe;

import javax.annotation.Nullable;

import com.gmail.rohzek.stonecut.lib.ConfigurationManager;
import com.gmail.rohzek.stonecut.lib.Reference;
import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;

public class StonecutterModRecipe extends StonecutterRecipe
{
	Serializer serializer;
	
	String difficulty;
	Ingredient input;
	ItemStack stack;
	
	public StonecutterModRecipe(ResourceLocation location, String group, Ingredient input, ItemStack itemstack, String difficulty) 
	{
		super(location, group, input, itemstack);
		
		this.input = input;
		this.stack = itemstack;
		this.difficulty = difficulty;
	}
	
	@Override
	public boolean matches(Container container, Level world) 
	{
		if(ConfigurationManager.GENERAL.difficulty.get().toLowerCase().equals(this.difficulty.toLowerCase())) 
		{
			return super.matches(container, world);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() 
	{
		return Serializer.INSTANCE;
	}
	
	@Override
	public RecipeType<?> getType() 
	{
		return Type.STONECUTTING;
	}
	
	public static class Type implements RecipeType<StonecutterModRecipe>
	{
		private Type() {}
		public static final Type INSTANCE = new Type();
		public static final String ID = "stonecutter";
	}
	
	public static class Serializer implements RecipeSerializer<StonecutterModRecipe>
	{
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "stonecutter");
		
		@SuppressWarnings("deprecation")
		@Override
		public StonecutterModRecipe fromJson(ResourceLocation location, JsonObject json) 
		{
			String group = GsonHelper.getAsString(json, "group", "");
			
			Ingredient ingredient;
	        if (GsonHelper.isArrayNode(json, "ingredient")) 
	        {
	        	ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "ingredient"));
	        } 
	        else 
	        {
	            ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
	        }

	        int count = GsonHelper.getAsInt(json, "count");
	        String difficulty = GsonHelper.getAsString(json, "difficulty", "");
	        String result = GsonHelper.getAsString(json, "result");
	        ItemStack itemstack = new ItemStack(Registry.ITEM.get(new ResourceLocation(result)), count);

	        return new StonecutterModRecipe(location, group, ingredient, new ItemStack(itemstack.getItem(), count), difficulty);
		}

		@Override
		public @Nullable StonecutterModRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf bytebuf) 
		{
			String group = bytebuf.readUtf();
			Ingredient ingredient = Ingredient.fromNetwork(bytebuf);
			ItemStack itemstack = bytebuf.readItem();
			String diff = bytebuf.readUtf();
			
			return new StonecutterModRecipe(location, group, ingredient, itemstack, diff);
		}

		@Override
		public void toNetwork(FriendlyByteBuf bytebuf, StonecutterModRecipe recipe) 
		{
			bytebuf.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(bytebuf);
			bytebuf.writeItem(recipe.result);
			bytebuf.writeUtf(recipe.difficulty);
		}
	}
}
