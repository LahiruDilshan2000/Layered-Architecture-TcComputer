package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.ItemDTO;
import lk.ijse.tccomputer.service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {

    ItemDTO saveItem(ItemDTO itemDTO);

    ItemDTO updateItem(ItemDTO itemDTO);

    void deleteItem(String pk);

    String getNextItemCode();

    ItemDTO getItemByPk(String pk);

    List<ItemDTO> findAllItem();

    List<ItemDTO> searchItemByText(String text);

    long getItemCount();

    List<ItemDTO> getRemainingItem();
}
