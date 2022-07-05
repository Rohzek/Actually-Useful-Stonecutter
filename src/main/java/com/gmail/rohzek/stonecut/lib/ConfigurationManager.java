package com.gmail.rohzek.stonecut.lib;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationManager 
{
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();
    
    public static class General 
    {
    	public final ForgeConfigSpec.ConfigValue<Boolean> isDebug;
    	public final ForgeConfigSpec.ConfigValue<String> difficulty;
    	
    	public General(ForgeConfigSpec.Builder builder) 
        {
            builder.push("General");
            
            isDebug = builder
                    .comment("Enables/Disables debug mode logging [false/true|default:false]")
                    .translation("debugmode." + Reference.MODID + ".config")
                    .define("isDebug", false);
            
            difficulty = builder
            		.comment("Determines the difficulty level of the recipes. The easier the difficulty, the more bountiful the results [easy/normal/hard|default:normal]")
            		.translation("difficulty." + Reference.MODID + ".config")
            		.define("difficulty", "normal");
            
            builder.pop();
        }
    }
}