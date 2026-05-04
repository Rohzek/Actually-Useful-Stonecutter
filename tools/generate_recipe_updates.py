#!/usr/bin/env python3

import json
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
RECIPES = ROOT / "src" / "main" / "resources" / "data" / "actuallyusefulstonecutter" / "recipes"

DIFFICULTIES = {
    "easy": "_easy",
    "normal": "",
    "hard": "_hard",
}

WOOD_COUNTS = {
    "stairs": {"easy": 3, "normal": 2, "hard": 1},
    "slab": {"easy": 4, "normal": 3, "hard": 2},
    "door": {"easy": 2, "normal": 1, "hard": 1},
    "trapdoor": {"easy": 6, "normal": 3, "hard": 1},
    "fence": {"easy": 4, "normal": 4, "hard": 1},
    "fence_gate": {"easy": 4, "normal": 4, "hard": 1},
    "pressure_plate": {"easy": 6, "normal": 4, "hard": 1},
    "button": {"easy": 12, "normal": 8, "hard": 4},
    "sign": {"easy": 4, "normal": 4, "hard": 1},
}

SIMPLE_COUNTS = {
    "strip": {"easy": 1, "normal": 1, "hard": 1},
    "planks_from_bamboo_block": {"easy": 3, "normal": 2, "hard": 1},
    "raft": {"easy": 1, "normal": 1, "hard": 1},
    "bamboo_mosaic": {"easy": 2, "normal": 1, "hard": 1},
    "cut_block": {"easy": 1, "normal": 1, "hard": 1},
    "stairs_from_block": {"easy": 3, "normal": 2, "hard": 1},
    "slab_from_block": {"easy": 4, "normal": 3, "hard": 2},
    "copper_grate": {"easy": 4, "normal": 2, "hard": 1},
    "copper_bulb": {"easy": 2, "normal": 1, "hard": 1},
}


def write_recipe(filename: str, difficulty: str, count: int, ingredient: str, result: str) -> None:
    payload = {
        "type": "actuallyusefulstonecutter:stonecutter",
        "difficulty": difficulty,
        "count": count,
        "ingredient": {"item": f"minecraft:{ingredient}"},
        "result": f"minecraft:{result}",
    }
    (RECIPES / f"{filename}{DIFFICULTIES[difficulty]}.json").write_text(
        json.dumps(payload, indent=2) + "\n",
        encoding="utf-8",
    )


def clone_mangrove_to_cherry() -> int:
    count = 0
    for path in RECIPES.glob("*mangrove*.json"):
        data = json.loads(path.read_text(encoding="utf-8"))
        new_name = path.name.replace("mangrove", "cherry")
        text = json.dumps(data)
        text = text.replace("mangrove", "cherry")
        (RECIPES / new_name).write_text(json.dumps(json.loads(text), indent=2) + "\n", encoding="utf-8")
        count += 1
    return count


def generate_bamboo() -> int:
    count = 0

    for difficulty, suffix in DIFFICULTIES.items():
        write_recipe("stripped_bamboo_block", difficulty, SIMPLE_COUNTS["strip"][difficulty], "bamboo_block", "stripped_bamboo_block")
        count += 1
        write_recipe(
            "planks_bamboo_block",
            difficulty,
            SIMPLE_COUNTS["planks_from_bamboo_block"][difficulty],
            "bamboo_block",
            "bamboo_planks",
        )
        count += 1
        write_recipe(
            "planks_bamboo_stripped_block",
            difficulty,
            SIMPLE_COUNTS["planks_from_bamboo_block"][difficulty],
            "stripped_bamboo_block",
            "bamboo_planks",
        )
        count += 1
        write_recipe("raft_bamboo_block", difficulty, SIMPLE_COUNTS["raft"][difficulty], "bamboo_block", "bamboo_raft")
        count += 1
        write_recipe(
            "raft_bamboo_stripped_block",
            difficulty,
            SIMPLE_COUNTS["raft"][difficulty],
            "stripped_bamboo_block",
            "bamboo_raft",
        )
        count += 1
        write_recipe(
            "mosaic_bamboo",
            difficulty,
            SIMPLE_COUNTS["bamboo_mosaic"][difficulty],
            "bamboo_planks",
            "bamboo_mosaic",
        )
        count += 1

    for prefix, counts in WOOD_COUNTS.items():
        result = f"bamboo_{prefix}" if prefix not in {"pressure_plate", "fence_gate"} else f"bamboo_{prefix}"
        for difficulty in DIFFICULTIES:
            write_recipe(f"{prefix}_bamboo", difficulty, counts[difficulty], "bamboo_planks", result)
            count += 1

    for prefix, counts in (("stairs", WOOD_COUNTS["stairs"]), ("slab", WOOD_COUNTS["slab"])):
        for difficulty in DIFFICULTIES:
            write_recipe(
                f"{prefix}_bamboo_mosaic",
                difficulty,
                counts[difficulty],
                "bamboo_mosaic",
                f"bamboo_mosaic_{prefix}",
            )
            count += 1

    return count


def generate_tuff() -> int:
    count = 0
    tuff_block_recipes = [
        ("polished_tuff", "polished_tuff", "cut_block"),
        ("brick_tuff", "tuff_bricks", "cut_block"),
        ("chiseled_tuff", "chiseled_tuff", "cut_block"),
        ("stairs_tuff", "tuff_stairs", "stairs_from_block"),
        ("slab_tuff", "tuff_slab", "slab_from_block"),
    ]
    polished_tuff_recipes = [
        ("stairs_polished_tuff", "polished_tuff_stairs", "stairs_from_block"),
        ("slab_polished_tuff", "polished_tuff_slab", "slab_from_block"),
        ("chiseled_tuff_from_polished", "chiseled_tuff", "cut_block"),
    ]
    tuff_brick_recipes = [
        ("stairs_tuff_brick", "tuff_brick_stairs", "stairs_from_block"),
        ("slab_tuff_brick", "tuff_brick_slab", "slab_from_block"),
        ("chiseled_tuff_bricks", "chiseled_tuff_bricks", "cut_block"),
    ]

    for difficulty in DIFFICULTIES:
        for filename, result, count_key in tuff_block_recipes:
            write_recipe(filename, difficulty, SIMPLE_COUNTS[count_key][difficulty], "tuff", result)
            count += 1
        for filename, result, count_key in polished_tuff_recipes:
            write_recipe(filename, difficulty, SIMPLE_COUNTS[count_key][difficulty], "polished_tuff", result)
            count += 1
        for filename, result, count_key in tuff_brick_recipes:
            write_recipe(filename, difficulty, SIMPLE_COUNTS[count_key][difficulty], "tuff_bricks", result)
            count += 1

    return count


def generate_copper() -> int:
    count = 0
    states = [
        ("copper_block", "copper_block", "cut_copper", "chiseled_copper", "copper_door", "copper_trapdoor", "copper_grate", "copper_bulb"),
        ("exposed_copper", "exposed_copper", "exposed_cut_copper", "exposed_chiseled_copper", "exposed_copper_door", "exposed_copper_trapdoor", "exposed_copper_grate", "exposed_copper_bulb"),
        ("weathered_copper", "weathered_copper", "weathered_cut_copper", "weathered_chiseled_copper", "weathered_copper_door", "weathered_copper_trapdoor", "weathered_copper_grate", "weathered_copper_bulb"),
        ("oxidized_copper", "oxidized_copper", "oxidized_cut_copper", "oxidized_chiseled_copper", "oxidized_copper_door", "oxidized_copper_trapdoor", "oxidized_copper_grate", "oxidized_copper_bulb"),
        ("waxed_copper", "waxed_copper_block", "waxed_cut_copper", "waxed_chiseled_copper", "waxed_copper_door", "waxed_copper_trapdoor", "waxed_copper_grate", "waxed_copper_bulb"),
        ("waxed_exposed_copper", "waxed_exposed_copper", "waxed_exposed_cut_copper", "waxed_exposed_chiseled_copper", "waxed_exposed_copper_door", "waxed_exposed_copper_trapdoor", "waxed_exposed_copper_grate", "waxed_exposed_copper_bulb"),
        ("waxed_weathered_copper", "waxed_weathered_copper", "waxed_weathered_cut_copper", "waxed_weathered_chiseled_copper", "waxed_weathered_copper_door", "waxed_weathered_copper_trapdoor", "waxed_weathered_copper_grate", "waxed_weathered_copper_bulb"),
        ("waxed_oxidized_copper", "waxed_oxidized_copper", "waxed_oxidized_cut_copper", "waxed_oxidized_chiseled_copper", "waxed_oxidized_copper_door", "waxed_oxidized_copper_trapdoor", "waxed_oxidized_copper_grate", "waxed_oxidized_copper_bulb"),
    ]

    for base_name, block, cut, chiseled, door, trapdoor, grate, bulb in states:
        stairs = f"{cut}_stairs"
        slab = f"{cut}_slab"

        for difficulty in DIFFICULTIES:
            write_recipe(f"cut_{base_name}", difficulty, SIMPLE_COUNTS["cut_block"][difficulty], block, cut)
            count += 1
            write_recipe(f"chiseled_{base_name}", difficulty, SIMPLE_COUNTS["cut_block"][difficulty], cut, chiseled)
            count += 1
            write_recipe(f"stairs_{base_name}", difficulty, SIMPLE_COUNTS["stairs_from_block"][difficulty], cut, stairs)
            count += 1
            write_recipe(f"slab_{base_name}", difficulty, SIMPLE_COUNTS["slab_from_block"][difficulty], cut, slab)
            count += 1
            write_recipe(f"door_{base_name}", difficulty, WOOD_COUNTS["door"][difficulty], block, door)
            count += 1
            write_recipe(f"trapdoor_{base_name}", difficulty, WOOD_COUNTS["trapdoor"][difficulty], block, trapdoor)
            count += 1
            write_recipe(f"grate_{base_name}", difficulty, SIMPLE_COUNTS["copper_grate"][difficulty], block, grate)
            count += 1
            write_recipe(f"bulb_{base_name}", difficulty, SIMPLE_COUNTS["copper_bulb"][difficulty], block, bulb)
            count += 1

    return count


def main() -> None:
    cherry = clone_mangrove_to_cherry()
    bamboo = generate_bamboo()
    tuff = generate_tuff()
    copper = generate_copper()
    print(f"Generated {cherry} cherry recipes, {bamboo} bamboo recipes, {tuff} tuff recipes, and {copper} copper recipes.")


if __name__ == "__main__":
    main()
