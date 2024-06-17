package in.sakshi.pingpong.playersapp.model;
public enum Chance {
    FIRST("FIRST"),
    SECOND("SECOND");
    private final String value;
    Chance(final String value){
        this.value = value;
    }
    public String value(){
        return this.value;
    }
}
