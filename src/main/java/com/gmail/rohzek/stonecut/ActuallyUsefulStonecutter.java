package com.gmail.rohzek.stonecut;

import com.gmail.rohzek.stonecut.lib.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class ActuallyUsefulStonecutter 
{
	public ActuallyUsefulStonecutter() 
	{
		// Register the mod
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}
}
