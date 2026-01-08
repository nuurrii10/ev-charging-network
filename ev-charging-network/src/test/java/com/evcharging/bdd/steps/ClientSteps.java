package com.evcharging.bdd.steps;

import com.evcharging.domain.Client;
import com.evcharging.domain.ClientManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

public class ClientSteps {

    private ClientManager clientManager;

    @Given("there are no clients in the system")
    public void there_are_no_clients_in_the_system() {
        clientManager = new ClientManager();
        clientManager.clear();
    }

    @When("I add a client with id {string} and balance {int}")
    public void i_add_a_client(String id, Integer balance) {
        clientManager.addClient(id, balance);
    }

    @Then("the system should contain {int} client")
    public void the_system_should_contain_client(Integer expectedCount) {
        assertEquals(expectedCount.intValue(), clientManager.count());
    }

    @Then("the client {string} should have balance {int}")
    public void the_client_should_have_balance(String id, Integer expectedBalance) {
        Client client = clientManager.findById(id);
        assertNotNull(client);
        assertEquals(expectedBalance.intValue(), client.getBalance());
    }

    @Given("a client {string} with balance {int} exists")
    public void a_client_with_balance_exists(String id, Integer balance) {
        clientManager = new ClientManager();
        clientManager.addClient(id, balance);
    }

    @When("I update the balance of client {string} to {int}")
    public void i_update_the_balance(String id, Integer newBalance) {
        clientManager.updateBalance(id, newBalance);
    }
}
