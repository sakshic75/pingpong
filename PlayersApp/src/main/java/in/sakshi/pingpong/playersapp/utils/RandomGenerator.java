package in.sakshi.pingpong.playersapp.utils;

import java.util.Random;

public class RandomGenerator {
    private final Random randomApi;
    private int from;
    private int to;
    public RandomGenerator(final int from, final int to){
        randomApi = new Random(System.currentTimeMillis());
        setFrom(from);
        setTo(to);
    }
    public int getFrom(){
        return this.from;
    }
    private void setFrom(final int from){
        this.from = from;
    }
    public int getTo(){
        return this.to;
    }
    private void setTo(final int to){
        this.to = to;
    }
    public int getRandomNumber(){
        return randomApi.nextInt(getFrom(),getTo());
    }

}
