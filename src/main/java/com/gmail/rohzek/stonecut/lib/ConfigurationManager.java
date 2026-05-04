package com.gmail.rohzek.stonecut.lib;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigurationManager {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ModConfigSpec SPEC = BUILDER.build();

    public static class General {
        public final ModConfigSpec.ConfigValue<Boolean> isDebug;
        public final ModConfigSpec.ConfigValue<String> difficulty;

        public General(ModConfigSpec.Builder builder) {
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
