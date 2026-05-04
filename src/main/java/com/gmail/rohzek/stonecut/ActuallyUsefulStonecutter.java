package com.gmail.rohzek.stonecut;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gmail.rohzek.stonecut.lib.ConfigurationManager;
import com.gmail.rohzek.stonecut.lib.DeferredRegistration;
import com.gmail.rohzek.stonecut.lib.Reference;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Reference.MODID)
public class ActuallyUsefulStonecutter {
    public static final Logger LOGGER = LogManager.getLogger();

    public ActuallyUsefulStonecutter(IEventBus modEventBus, ModContainer modContainer) {
        DeferredRegistration.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, ConfigurationManager.SPEC);
    }
}
