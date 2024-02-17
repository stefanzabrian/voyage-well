package com.dev.voyagewell.repository.user;

import com.dev.voyagewell.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
