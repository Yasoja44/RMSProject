package lk.nnj.rms.fx.service;

import lk.nnj.rms.fx.model.Player;

import java.util.List;

public interface IPlayerService {

    boolean add(Player player) throws Exception;
    boolean update(Player player) throws Exception;
    boolean delete(String id) throws Exception;
    Player find(String id) throws Exception;
    List<Player> findAll() throws Exception;
}
