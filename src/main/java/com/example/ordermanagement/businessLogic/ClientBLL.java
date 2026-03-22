package com.example.ordermanagement.businessLogic;

import com.example.ordermanagement.dataAccess.ClientDAO;
import com.example.ordermanagement.dataModel.Client;
import java.util.List;

/**
 * logic for clients.
 */
public class ClientBLL {
    private final ClientDAO dao = new ClientDAO();

    public List<Client> getAll() {
        return dao.findAll();
    }
    public Client findById(int id) {
        return dao.findById(id);
    }
    public void add(Client c)    {
        dao.insert(c);
    }

    public void update(Client c) {
        dao.update(c);
    }


    public void delete(int id)   {
        dao.delete(id);
    }
}
