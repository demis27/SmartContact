package com.demis.smart.contact.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name="incoming_transport")
public class IncomingTransport extends AbstractModel implements Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingTransport.class);

    private Long id = null;
    private String name = null;

    public IncomingTransport() {
        // no op
    }

    @Id
    @Column(name = "incoming_transport_id", precision = 10)
    @GeneratedValue(generator="incoming_transport_sequence")
    @SequenceGenerator(name="incoming_transport_sequence", sequenceName="incoming_transport_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = true, unique = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
