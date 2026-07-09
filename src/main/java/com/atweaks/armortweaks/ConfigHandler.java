package com.atweaks.armortweaks;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static double weightHelmet;
    public static double weightChest;
    public static double weightLegs;
    public static double weightBoots;

    public static boolean leatherEnabled;
    public static double leatherWalkSpeedBonus;
    public static double leatherFallDamageReduction;
    public static boolean leatherBetterSwim;
    public static double leatherSwimBonus;

    public static double leatherArrowExplosionProtectionPenalty;
    public static double leatherArrowExplosionDurabilityMultiplier;

    public static int leatherHelmetArmor;
    public static int leatherChestArmor;
    public static int leatherLegsArmor;
    public static int leatherBootsArmor;
    public static int leatherHelmetDurability;
    public static int leatherChestDurability;
    public static int leatherLegsDurability;
    public static int leatherBootsDurability;

    public static boolean ironEnabled;
    public static double ironWalkSpeedPenalty;
    public static double ironFallDamageIncrease;
    public static boolean ironWorseSwim;
    public static double ironSwimPenalty;

    public static int ironHelmetArmor;
    public static int ironChestArmor;
    public static int ironLegsArmor;
    public static int ironBootsArmor;
    public static int ironHelmetDurability;
    public static int ironChestDurability;
    public static int ironLegsDurability;
    public static int ironBootsDurability;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        try {
            config.load();

            weightHelmet = config.get("pieceWeights", "helmet", 0.1333,
                    "Вага шолома у розрахунку бафів/дебафів (частка від повного ефекту)").getDouble();
            weightChest = config.get("pieceWeights", "chestplate", 0.4,
                    "Вага нагрудника у розрахунку бафів/дебафів").getDouble();
            weightLegs = config.get("pieceWeights", "leggings", 0.3333,
                    "Вага поножів у розрахунку бафів/дебафів").getDouble();
            weightBoots = config.get("pieceWeights", "boots", 0.1333,
                    "Вага черевиків у розрахунку бафів/дебафів").getDouble();

            leatherEnabled = config.get("leather", "enabled", true,
                    "Увімкнути ефекти шкіряної броні").getBoolean(true);
            leatherWalkSpeedBonus = config.get("leather", "walkSpeedBonus", 0.02,
                    "Приріст швидкості ходьби при ПОВНОМУ комплекті шкіри (масштабується вагою одягнених елементів)").getDouble();
            leatherFallDamageReduction = config.get("leather", "fallDamageReduction", 0.5,
                    "Наскільки менше урону від падіння при ПОВНОМУ комплекті (0.5 = на 50% менше)").getDouble();
            leatherBetterSwim = config.get("leather", "betterSwim", true,
                    "Чи легше плавати в шкіряній броні").getBoolean(true);
            leatherSwimBonus = config.get("leather", "swimBonus", 0.02,
                    "Сила виштовхування до поверхні при ПОВНОМУ комплекті").getDouble();

            leatherArrowExplosionProtectionPenalty = config.get("leather", "arrowExplosionProtectionPenalty", 0.6,
                    "Наскільки менше шкіра захищає саме від стріл і вибухів (0.6 = на 60% гірше)").getDouble();
            leatherArrowExplosionDurabilityMultiplier = config.get("leather", "arrowExplosionDurabilityMultiplier", 3.0,
                    "У скільки разів сильніше стріли/вибухи псують міцність шкіряної броні").getDouble();

            leatherHelmetArmor = config.get("leather", "helmetArmorPoints", 1,
                    "Захист шолома зі шкіри (ванільно: 1)").getInt();
            leatherChestArmor = config.get("leather", "chestplateArmorPoints", 3,
                    "Захист нагрудника зі шкіри (ванільно: 3)").getInt();
            leatherLegsArmor = config.get("leather", "leggingsArmorPoints", 2,
                    "Захист поножів зі шкіри (ванільно: 2)").getInt();
            leatherBootsArmor = config.get("leather", "bootsArmorPoints", 1,
                    "Захист черевиків зі шкіри (ванільно: 1)").getInt();

            leatherHelmetDurability = config.get("leather", "helmetDurability", 55,
                    "Міцність шолома зі шкіри (ванільно: 55)").getInt();
            leatherChestDurability = config.get("leather", "chestplateDurability", 80,
                    "Міцність нагрудника зі шкіри (ванільно: 80)").getInt();
            leatherLegsDurability = config.get("leather", "leggingsDurability", 75,
                    "Міцність поножів зі шкіри (ванільно: 75)").getInt();
            leatherBootsDurability = config.get("leather", "bootsDurability", 65,
                    "Міцність черевиків зі шкіри (ванільно: 65)").getInt();

            ironEnabled = config.get("iron", "enabled", true,
                    "Увімкнути ефекти залізної броні").getBoolean(true);
            ironWalkSpeedPenalty = config.get("iron", "walkSpeedPenalty", 0.02,
                    "Зменшення швидкості ходьби при ПОВНОМУ комплекті заліза").getDouble();
            ironFallDamageIncrease = config.get("iron", "fallDamageIncrease", 0.5,
                    "Наскільки більше урону від падіння при ПОВНОМУ комплекті (0.5 = на 50% більше)").getDouble();
            ironWorseSwim = config.get("iron", "worseSwim", true,
                    "Чи важче плавати в залізній броні").getBoolean(true);
            ironSwimPenalty = config.get("iron", "swimPenalty", 0.02,
                    "Сила затягування під воду при ПОВНОМУ комплекті").getDouble();

            ironHelmetArmor = config.get("iron", "helmetArmorPoints", 2,
                    "Захист шолома з заліза (ванільно: 2)").getInt();
            ironChestArmor = config.get("iron", "chestplateArmorPoints", 6,
                    "Захист нагрудника з заліза (ванільно: 6)").getInt();
            ironLegsArmor = config.get("iron", "leggingsArmorPoints", 5,
                    "Захист поножів з заліза (ванільно: 5)").getInt();
            ironBootsArmor = config.get("iron", "bootsArmorPoints", 2,
                    "Захист черевиків з заліза (ванільно: 2)").getInt();

            ironHelmetDurability = config.get("iron", "helmetDurability", 165,
                    "Міцність шолома з заліза (ванільно: 165)").getInt();
            ironChestDurability = config.get("iron", "chestplateDurability", 240,
                    "Міцність нагрудника з заліза (ванільно: 240)").getInt();
            ironLegsDurability = config.get("iron", "leggingsDurability", 225,
                    "Міцність поножів з заліза (ванільно: 225)").getInt();
            ironBootsDurability = config.get("iron", "bootsDurability", 195,
                    "Міцність черевиків з заліза (ванільно: 195)").getInt();

        } catch (Exception e) {
            System.err.println("[ArmorTweaks] Помилка читання конфігу: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
