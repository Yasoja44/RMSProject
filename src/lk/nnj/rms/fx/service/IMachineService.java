package lk.nnj.rms.fx.service;

import lk.nnj.rms.fx.model.Machine;

import java.util.List;

public interface IMachineService {
    boolean add(Machine machine) throws Exception;
    boolean update(Machine machine) throws Exception;
    boolean delete(String id) throws Exception;
    Machine find(String id) throws Exception;
    List<Machine> findAll() throws Exception;
}
