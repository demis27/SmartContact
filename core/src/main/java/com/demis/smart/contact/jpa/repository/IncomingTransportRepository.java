package com.demis.smart.contact.jpa.repository;

import com.demis.smart.contact.jpa.entity.IncomingTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("incomingTransportRepository")
public interface IncomingTransportRepository extends JpaRepository<IncomingTransport, Long> {

}
