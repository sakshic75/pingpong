package in.sakshi.pingpong.refereeapp.models;

public enum Chance {
    FIRST("FIRST"),
    SECOND("SECOND");
    private final String chance;
    Chance(final String chance){
        this.chance = chance;
    }

    public String getChance() {
        return chance;
    }
}
