package top.mcstarlight.ffa.mechanism;

import top.mcstarlight.ffa.Main;

public class ELO {
    public static void setELO(String name,double value) {
        Main.getEloDataConfig().set("elo." + name, value);
        Main.getEloDataConfig().save();
    }

    public static int getELO(String name) {
        if (Main.isELOEnabled()){
            return Main.getEloDataConfig().getInt("elo." + name, 0);
        } else {
            return 0;
        }
    }

    private static double expectedScore(double playerRating, double opponentRating) {
        return 1.0 / (1.0 + Math.pow(10, (opponentRating - playerRating) / 400.0));
    }

    private static double CalculateELO(String A, String B, int actualResult) {
        int oldRating = getELO(A);
        return oldRating + Main.getInstance().getConfig().getInt("elo.k") * (actualResult - expectedScore(getELO(A),getELO(B)));
    }

    public static void updateELO(String a,String b) {
        setELO(a,CalculateELO(a,b,1));
        setELO(b,CalculateELO(b,a,0));
    }
}
