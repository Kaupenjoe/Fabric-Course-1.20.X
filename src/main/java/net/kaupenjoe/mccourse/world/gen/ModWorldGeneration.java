package net.kaupenjoe.mccourse.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGeneration() {
        ModGeodeGeneration.generateGeodes();

        ModOreGeneration.generateOres();

        ModTreeGeneration.generateTrees();
        ModFlowerGeneration.generateFlowers();

        ModEntitySpawns.addSpawns();
    }
}
