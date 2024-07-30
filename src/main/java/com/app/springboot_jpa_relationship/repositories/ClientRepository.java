package com.app.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.springboot_jpa_relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("select c from Client c left join fetch c.addresses where c.id = :id")
    Optional<Client> findOneWithAdresses(@Param("id") Long id);

    @Query("select c from Client c left join fetch c.invoices where c.id = :id")
    Optional<Client> findOneWithInvoices(@Param("id") Long id);

    @Query("select c From Client c left join fetch c.invoices left join fetch c.addresses left join fetch c.clientDetails where c.id = :id")
    Optional<Client> findOne(@Param("id") Long id);

}
