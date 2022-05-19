package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = new ArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item;");
        while (resultSet.next()){
            items.add(new Item(
                    resultSet.getString("itemCode"),resultSet.getString("description"),
                    resultSet.getString("packSize"),resultSet.getDouble("unitPrice"),
                    resultSet.getDouble("maxDiscount"),resultSet.getInt("qoh"),
                    LocalDate.parse(String.valueOf(resultSet.getDate("addedDate")))
            ));
        }
        return items;
    }
}