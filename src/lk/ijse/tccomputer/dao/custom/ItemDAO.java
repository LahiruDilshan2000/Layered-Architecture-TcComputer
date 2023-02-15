package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDAO extends CrudDAO<Item,String > {

    List<Item> searchByText(String text);

    Optional<String> getNextPk();

    Item reducesItemQty(Item item);

    Item updateItemQty(Item item);

    List<Item> getRemainingItem();
}
