package com.evcharging.domain;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private final List<Client> clients = new ArrayList<>();

    public void clear() {
        clients.clear();
    }

    public void addClient(String id, int balance) {
        clients.add(new Client(id, balance));
    }

    public int count() {
        return clients.size();
    }

    public Client findById(String id) {
        return clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateBalance(String id, int newBalance) {
        Client client = findById(id);
        if (client != null) {
            client.setBalance(newBalance);
        }
    }
}
