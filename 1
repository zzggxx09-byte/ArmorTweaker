package com.atweaks.armortweaks;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static boolean leatherEnabled;
    public static double leatherWalkSpeedBonus;
    public static double leatherFallDamageMultiplier;
    public static boolean leatherBetterSwim;
    public static double leatherSwimBonus;

    public static boolean ironEnabled;
    public static double ironWalkSpeedPenalty;
    public static double ironFallDamageMultiplier;
    public static boolean ironWorseSwim;
    public static double ironSwimPenalty;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        try {
            config.load();

            leatherEnabled = config.get("leather", "enabled", true,
                    "Увімкнути ефекти шкіряної броні").getBoolean(true);
            leatherWalkSpeedBonus = config.get("leather", "walkSpeedBonus", 0.02,
                    "Приріст швидкості ходьби при нагруднику+поножах зі шкіри (напр. 0.02 = +2%)").getDouble();
            leatherFallDamageMultiplier = config.get("leather", "fallDamageMultiplier", 0.5,
                    "Множник урону від падіння (0.5 = удвічі менше урону, 0 = зовсім нема урону)").getDouble();
            leatherBetterSwim = config.get("leather", "betterSwim", true,
                    "Чи легше плавати / не тонути в шкіряній броні").getBoolean(true);
            leatherSwimBonus = config.get("leather", "swimBonus", 0.02,
                    "Сила виштовхування до поверхні води (більше = сильніше)").getDouble();

            ironEnabled = config.get("iron", "enabled", true,
                    "Увімкнути ефекти залізної броні").getBoolean(true);
            ironWalkSpeedPenalty = config.get("iron", "walkSpeedPenalty", 0.02,
                    "Зменшення швидкості ходьби при нагруднику+поножах з заліза").getDouble();
            ironFallDamageMultiplier = config.get("iron", "fallDamageMultiplier", 1.5,
                    "Множник урону від падіння (1.5 = у 1.5 рази більше урону)").getDouble();
            ironWorseSwim = config.get("iron", "worseSwim", true,
                    "Чи важче плавати / тоне в залізній броні").getBoolean(true);
            ironSwimPenalty = config.get("iron", "swimPenalty", 0.02,
                    "Сила затягування під воду (більше = сильніше тоне)").getDouble();

        } catch (Exception e) {
            System.err.println("[ArmorTweaks] Помилка читання конфігу: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
