package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.ItemDAO;
import lk.ijse.tccomputer.dto.ItemDTO;
import lk.ijse.tccomputer.service.custom.ItemService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;
    private final Convertor convertor;

    public ItemServiceImpl(){
        itemDAO= DaoFactory.getInstance().getDAO(DaoType.ITEM);
        convertor=new Convertor();
    }
    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) {

        itemDAO.save(convertor.toItem(itemDTO));
        return itemDTO;

    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {

        itemDAO.update(convertor.toItem(itemDTO));
        return itemDTO;
    }

    @Override
    public void deleteItem(String pk) {

        itemDAO.delete(pk);

    }

    @Override
    public String getNextItemCode() {

        Optional<String> nextPk = itemDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;
    }

    @Override
    public ItemDTO getItemByPk(String pk) {

        ItemDTO itemDTO = itemDAO.findAll().stream().filter(item -> item.getItemCode().equalsIgnoreCase(pk)).map(item -> convertor.fromItem(item)).findAny().get();
        return itemDTO;
    }

    @Override
    public List<ItemDTO> findAllItem() {

        return itemDAO.findAll().stream().map(item -> convertor.fromItem(item)).collect(Collectors.toList());
    }

    @Override
    public List<ItemDTO> searchItemByText(String text) {

        return itemDAO.searchByText(text).stream().map(item -> convertor.fromItem(item)).collect(Collectors.toList());
    }

    @Override
    public long getItemCount() {

        List<ItemDTO> tmList = itemDAO.findAll().stream().map(item -> convertor.fromItem(item)).collect(Collectors.toList());
        long count=0;
        for (ItemDTO itemDTO: tmList){
            count+=itemDTO.getQtyOnHand();
        }
        return count;
    }

    @Override
    public List<ItemDTO> getRemainingItem() {

        return itemDAO.findAll().stream().filter(item -> item.getQtyOnHand() < 20).map(item -> convertor.fromItem(item)).collect(Collectors.toList());
    }
}
