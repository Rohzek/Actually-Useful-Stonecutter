# Actually Useful Stonecutter NeoForge 1.21.1

Unofficial NeoForge 1.21.1 fork of Rohzek's **Actually Useful Stonecutter**.

This fork ports the mod from its original Forge 1.19.x codebase to NeoForge 1.21.1 while preserving the original gameplay concept: adding many missing stonecutter recipes for wood, stone, and related block families.

## Upstream

- Original GitHub repository: <https://github.com/Rohzek/Actually-Useful-Stonecutter>
- Original Modrinth project: <https://modrinth.com/mod/actually-useful-stonecutter>

This repository is unofficial and is not endorsed by the original author.

## Licensing Basis

The original Modrinth project is listed as **CC0-1.0**. This fork relies on that public project listing as the basis for redistribution and modification of the mod.

The upstream GitHub repository contains older licensing artifacts inherited from the Forge MDK era. To avoid hiding that history, this fork keeps the original upstream files in place and documents the publication basis here instead of implying endorsement or authorship by the original maintainer.

## What Changed

- Ported the build from ForgeGradle to NeoGradle for NeoForge 1.21.1
- Updated the custom stonecutter recipe serializer to the 1.21.1 codec-based recipe API
- Updated loader metadata from `mods.toml` to `neoforge.mods.toml`
- Updated runtime/toolchain targets to Java 21

## Building

Use Java 21.

```bash
./gradlew build
```

The built jar is written to `build/libs/`.
