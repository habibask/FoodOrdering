package foodOrdering.service;

import foodOrdering.domain.Customer;

public class CustomerService {

    public Customer getDefaultUser() {
        Customer c = new Customer();
        c.setName("Test");
        return c;
    }
}
