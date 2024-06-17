package in.sakshi.pingpong.refereeapp.utils.parser;

import in.sakshi.pingpong.refereeapp.models.Player;

public interface Parser<T,U  extends Player> {
    T parse(U data);
    U parse(T data);
}
