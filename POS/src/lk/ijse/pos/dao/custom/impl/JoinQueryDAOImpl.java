package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.JoinQueryDAO;
import lk.ijse.pos.entity.CustomEntity;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class JoinQueryDAOImpl implements JoinQueryDAO {
    @Override
    public ArrayList<CustomEntity> getOrderDetailByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> entityArrayList = new ArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT orderdetail.ItemCode,Item.description,item.PackSize,orderdetail.UnitPrice,orderdetail.OrderQTY,orderdetail.Discount FROM orderdetail INNER JOIN Item ON orderdetail.ItemCode = item.ItemCode WHERE OrderId = ?;",orderId);

        while (resultSet.next()){
            entityArrayList.add(new CustomEntity(
                    resultSet.getString("ItemCode"),resultSet.getString("description"),
                    resultSet.getString("PackSize"),resultSet.getDouble("UnitPrice"),
                    resultSet.getInt("OrderQTY"),resultSet.getDouble("Discount")
            ));
        }
        return entityArrayList;
    }

    @Override
    public CustomEntity getOrderByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT Orders.CustID,orders.OrderId,orders.Date,Customer.CustName FROM orders INNER JOIN Customer ON Customer.CustId = orders.CustId WHERE OrderId = ?;",orderId);
        if(resultSet.next()){
            return new CustomEntity(
                    resultSet.getString("CustName"),resultSet.getString("OrderId"),
                    LocalDate.parse(String.valueOf(resultSet.getDate("Date"))),resultSet.getString("CustId")
            );
        }
        return null;
    }

    @Override
    public double getIncomeByYearForEachMonth(String year) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM((OrderQTY*orderdetail.UnitPrice))-Discount FROM  orderdetail INNER JOIN orders on orders.OrderId = orderdetail.OrderId WHERE orders.Date LIKE ?;",year);
        return resultSet.next()? resultSet.getDouble(1):0;
    }

    public double getDiscountByYear(String year) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(Discount) FROM  orderdetail INNER JOIN orders on orders.OrderId = orderdetail.OrderId WHERE orders.Date LIKE ?;",year);
        return resultSet.next()? resultSet.getDouble(1):0;
    }

    @Override
    public double getTotalIncomeByYear(String year) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM((orderdetail.OrderQTY*orderdetail.UnitPrice))-orderdetail.Discount FROM orderdetail INNER JOIN orders on orders.OrderId = orderdetail.OrderId WHERE orders.Date LIKE ?",year);
        return resultSet.next()? resultSet.getDouble(1):0;
    }
}
