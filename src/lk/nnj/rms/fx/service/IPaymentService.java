package lk.nnj.rms.fx.service;

import lk.nnj.rms.fx.model.Payment;

import java.util.List;

public interface IPaymentService {
    boolean add(Payment payment) throws Exception;
    boolean update(Payment payment) throws Exception;
    boolean delete(int id) throws Exception;
    Payment find(int id) throws Exception;
    List<Payment> findAll() throws Exception;
}
