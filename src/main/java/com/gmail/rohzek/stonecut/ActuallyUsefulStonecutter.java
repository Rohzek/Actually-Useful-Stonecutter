package com.gmail.rohzek.stonecut;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gmail.rohzek.stonecut.lib.ConfigurationManager;
import com.gmail.rohzek.stonecut.lib.DeferredRegistration;
import com.gmail.rohzek.stonecut.lib.Reference;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class ActuallyUsefulStonecutter 
{
	public final static Logger LOGGER = LogManager.getLogger();
	
	public ActuallyUsefulStonecutter() 
	{
		// Register the mod
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		
		DeferredRegistration.register();
		// Register configuration file
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigurationManager.spec);
	}
}
