package com.atweaks.bowtweaks;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static int smallBowDrawTicks;
    public static double smallBowVelocityMultiplier;
    public static int smallBowDurability;

    public static int proBowDrawTicks;
    public static double proBowVelocityMultiplier;
    public static int proBowDurability;

    public static double woodenArrowDamage;
    public static double ironArrowDamage;

    public static int woodenArrowCraftCount;
    public static int ironArrowCraftCount;

    public static boolean removeVanillaBowAndArrow;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        try {
            config.load();

            smallBowDrawTicks = config.get("smallBow", "drawTicks", 10,
                    "За скільки тіків маленький лук досягає повної сили натягу (ванільний лук: 20). Менше число = швидше натягується").getInt();
            smallBowVelocityMultiplier = config.get("smallBow", "velocityMultiplier", 0.6,
                    "Множник дальності/швидкості польоту стріли (1.0 = як ванільний лук на повній силі, менше = коротша дальність").getDouble();
            smallBowDurability = config.get("smallBow", "durability", 200,
                    "Міцність маленького лука").getInt();

            proBowDrawTicks = config.get("professionalBow", "drawTicks", 30,
                    "За скільки тіків професійний лук досягає повної сили натягу. Більше число = довше натягується").getInt();
            proBowVelocityMultiplier = config.get("professionalBow", "velocityMultiplier", 1.4,
                    "Множник дальності/швидкості польоту стріли, більше 1.0 = далі за ванільний лук").getDouble();
            proBowDurability = config.get("professionalBow", "durability", 400,
                    "Міцність професійного лука").getInt();

            woodenArrowDamage = config.get("arrows", "woodenArrowDamage", 1.5,
                    "Базовий урон дерев'яної стріли (ванільна стріла: 2.0)").getDouble();
            ironArrowDamage = config.get("arrows", "ironArrowDamage", 3.5,
                    "Базовий урон залізної стріли").getDouble();

            woodenArrowCraftCount = config.get("arrows", "woodenArrowCraftCount", 4,
                    "Скільки дерев'яних стріл виходить за 1 крафт (1 перо + 1 палка)").getInt();
            ironArrowCraftCount = config.get("arrows", "ironArrowCraftCount", 2,
                    "Скільки залізних стріл виходить за 1 крафт (1 перо + 1 палка + 1 злиток заліза)").getInt();

            removeVanillaBowAndArrow = config.get("general", "removeVanillaBowAndArrow", true,
                    "Прибрати ванільний крафт лука і стріл (щоб можна було крафтити тільки Small Bow / Professional Bow)").getBoolean(true);

        } catch (Exception e) {
            System.err.println("[BowTweaks] Помилка читання конфігу: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
