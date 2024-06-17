package in.sakshi.pingpong.playersapp.utils.parsers;

import in.sakshi.pingpong.playersapp.model.Player;

public interface Parser<T,R extends Player> {
    T parse(R object);
    R parse(T object);
}
