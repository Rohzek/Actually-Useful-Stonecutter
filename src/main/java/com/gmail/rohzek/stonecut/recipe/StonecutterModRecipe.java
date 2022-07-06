package com.gmail.rohzek.stonecut.recipe;

import com.gmail.rohzek.stonecut.lib.ConfigurationManager;
import com.gmail.rohzek.stonecut.lib.Reference;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class StonecutterModRecipe extends StonecuttingRecipe
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
	public boolean matches(IInventory inventory, World world) 
	{
		if(ConfigurationManager.GENERAL.difficulty.get().toLowerCase().equals(this.difficulty.toLowerCase())) 
		{
			return super.matches(inventory, world);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() 
	{
		return Serializer.INSTANCE;
	}
	
	@Override
	public IRecipeType<?> getType() 
	{
		return Type.STONECUTTING;
	}
	
	public static class Type implements IRecipeType<StonecutterModRecipe>
	{
		private Type() {}
		public static final Type INSTANCE = new Type();
		public static final String ID = "stonecutter";
	}
	
	public static class Serializer implements IRecipeSerializer<StonecutterModRecipe>
	{
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "stonecutter");

		@Override
		public IRecipeSerializer<?> setRegistryName(ResourceLocation name) 
		{
			return INSTANCE;
		}

		@Override
		public ResourceLocation getRegistryName() 
		{
			return ID;
		}

		@Override
		public Class<IRecipeSerializer<?>> getRegistryType()
		{
			return Serializer.castClass(IRecipeSerializer.class);
		}

		// Wrapper due to generics? Or something? Not sure but it's required apparently due to above function
		@SuppressWarnings("unchecked")
		private static <G> Class<G> castClass(Class<?> cls) 
		{
			return (Class<G>)cls;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public StonecutterModRecipe read(ResourceLocation location, JsonObject json) 
		{
			String group = JSONUtils.getString(json, "group", "");
			
			Ingredient ingredient;
	        if (JSONUtils.isJsonArray(json, "ingredient")) 
	        {
	        	ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
	        } 
	        else 
	        {
	            ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
	        }

	        int count = JSONUtils.getInt(json, "count");
	        String difficulty = JSONUtils.getString(json, "difficulty", "");
	        String result = JSONUtils.getString(json, "result");
			ItemStack itemstack = new ItemStack(Registry.ITEM.getOrDefault(new ResourceLocation(result)), count);

	        return new StonecutterModRecipe(location, group, ingredient, new ItemStack(itemstack.getItem(), count), difficulty);
		}

		@Override
		public StonecutterModRecipe read(ResourceLocation location, PacketBuffer buffer) 
		{
			String group = buffer.readString(32767);
			Ingredient ingredient = Ingredient.read(buffer);
			ItemStack itemstack = buffer.readItemStack();
			return new StonecutterModRecipe(location, group, ingredient, itemstack, "normal");
		}

		@Override
		public void write(PacketBuffer buffer, StonecutterModRecipe recipe) 
		{
			buffer.writeString(recipe.group);
			recipe.ingredient.write(buffer);
			buffer.writeItemStack(recipe.result);
		}
	}
}
