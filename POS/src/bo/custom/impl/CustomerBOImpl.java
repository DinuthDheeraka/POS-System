package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAO.CUSTOMER_IMPL);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList();
        for(Customer customer : customers){
            customerDTOS.add(new CustomerDTO(
                    customer.getCustID(),customer.getCusTitle(),customer.getCustName(),
                    customer.getCustAddress(),customer.getCity(),customer.getProvince(),
                    customer.getPostalCode(),customer.getJoinedDate()
            ));
        }
        return customerDTOS;
    }

    @Override
    public void insertCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        customerDAO.insert(new Customer(
                customerDTO.getCustID(), customerDTO.getCusTitle(),customerDTO.getCustName(),
                customerDTO.getCustAddress(),customerDTO.getCity(),customerDTO.getProvince(),
                customerDTO.getPostalCode(),customerDTO.getJoinedDate()
        ));
    }

    @Override
    public void deleteCustomer(String custID) throws SQLException, ClassNotFoundException {
        customerDAO.delete(custID);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        customerDAO.update(new Customer(
                customerDTO.getCustID(),customerDTO.getCusTitle(),customerDTO.getCustName(),
                customerDTO.getCustAddress(),customerDTO.getCity(),customerDTO.getProvince(),
                customerDTO.getPostalCode(),customerDTO.getJoinedDate()
        ));
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> custIds = customerDAO.getAllIds();
        return custIds;
    }

    @Override
    public CustomerDTO getCustomer(String custId) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.get(custId);
        return new CustomerDTO(
                customer.getCustID(),customer.getCusTitle(),customer.getCustName(),
                customer.getCustAddress(),customer.getCity(),customer.getProvince(),
                customer.getPostalCode()
        );
    }

    @Override
    public String getCustomerLastId() throws SQLException, ClassNotFoundException {
        return customerDAO.getLastId();
    }
}
