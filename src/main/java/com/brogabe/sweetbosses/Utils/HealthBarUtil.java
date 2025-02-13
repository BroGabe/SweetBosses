package com.brogabe.sweetbosses.Utils;

public class HealthBarUtil {

    public static String getHealthBar(int maxHealth, int currentHealth) {
        String healthToString = ColorUtil.color("&a■■■■■■■■■■");
        int healthPercentage = getHealthPercentage(maxHealth, currentHealth);

        if(healthPercentage >=90) {
            healthToString = ColorUtil.color("&a■■■■■■■■■&c■");
            return healthToString;
        } else if (healthPercentage >=80) {
            healthToString = ColorUtil.color("&a■■■■■■■■&c■■");
            return healthToString;
        } else if (healthPercentage >=70) {
            healthToString = ColorUtil.color("&a■■■■■■■&c■■■");
            return healthToString;
        } else if (healthPercentage >=60) {
            healthToString = ColorUtil.color("&a■■■■■■&c■■■■");
            return healthToString;
        } else if (healthPercentage >=50) {
            healthToString = ColorUtil.color("&a■■■■■&c■■■■■");
            return healthToString;
        } else if(healthPercentage >=40) {
            healthToString = ColorUtil.color("&a■■■■&c■■■■■■");
            return healthToString;
        } else if (healthPercentage >=30) {
            healthToString = ColorUtil.color("&a■■■&c■■■■■■■");
            return healthToString;
        } else if (healthPercentage >=20) {
            healthToString = ColorUtil.color("&a■■&c■■■■■■■■");
            return healthToString;
        } else if (healthPercentage >=10) {
            healthToString = ColorUtil.color("&a■&c■■■■■■■■■");
            return healthToString;
        } else if (healthPercentage >=0) {
            healthToString = ColorUtil.color("&c■■■■■■■■■■");
            return healthToString;
        }

        return healthToString;
    }

    private static int getHealthPercentage(int maxHealth, int currentHealth) {
        return (int) (((double) currentHealth / maxHealth) * 100);
    }
}
