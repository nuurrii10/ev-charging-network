package com.evcharging.domain;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private final List<Client> clients = new ArrayList<>();

    public void clear() {
        clients.clear();
    }

    public void addClient(String id, int balance) {
        if (balance < 0) {
            return; // negative balance -> reject
        }
        if (findById(id) != null) {
            return; // duplicate id -> ignore
        }
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
        if (newBalance < 0) {
            return; // reject
        }
        Client client = findById(id);
        if (client != null) {
            client.setBalance(newBalance);
        }
    }
}
