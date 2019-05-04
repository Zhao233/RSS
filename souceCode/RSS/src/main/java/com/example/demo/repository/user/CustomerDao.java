package com.example.demo.repository.user;

import com.example.demo.domain.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Long> {
    @Query(value = "SELECT customer.id FROM Customer customer WHERE customer.openID = ?1")
    Long getIdByOpenID(String openID);

    /**==============================For Customer=========================================**/
    @Query(value = "SELECT customer FROM Customer customer WHERE customer.openID = ?1")
    Customer getCustomerByOpenID(String openid);

}

