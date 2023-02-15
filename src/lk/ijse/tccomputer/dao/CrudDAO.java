package lk.ijse.tccomputer.dao;

import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID extends Serializable> extends SuperDAO{

    T save(T entity);

    T update(T entity);

    void delete(ID pk);

    List<T> findAll();

}
