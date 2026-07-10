package com.atweaks.xphearts;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class XpConfigHandler {

    public static int baseHearts;
    public static int maxHearts;
    public static double baseXpCost;
    public static double xpCostMultiplier;
    public static boolean announceInChat;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        try {
            config.load();

            baseHearts = config.get("general", "baseHearts", 4,
                    "Скільки сердець на старті (1 серце = 2 одиниці здоров'я). Ванільно: 10").getInt();
            maxHearts = config.get("general", "maxHearts", 20,
                    "Максимальна кількість сердець, яку можна накачати досвідом").getInt();
            baseXpCost = config.get("general", "baseXpCost", 20,
                    "Скільки всього очок досвіду (за все життя) треба для ПЕРШОГО додаткового серця").getDouble();
            xpCostMultiplier = config.get("general", "xpCostMultiplier", 1.3,
                    "У скільки разів дорожче кожне наступне серце за попереднє (1.3 = +30%)").getDouble();
            announceInChat = config.get("general", "announceInChat", true,
                    "Писати гравцю в чат, коли він отримує нове серце").getBoolean(true);

        } catch (Exception e) {
            System.err.println("[XpHearts] Помилка читання конфігу: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
