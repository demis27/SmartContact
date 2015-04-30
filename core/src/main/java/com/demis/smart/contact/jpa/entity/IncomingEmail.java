package com.demis.smart.contact.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name="incoming_email")
public class IncomingEmail extends AbstractModel implements Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingEmail.class);

    private Long id = null;
    private Integer size = null;
    private IncomingTransport incomingTransport = null;
    private byte[] binaryContent = null;

    public IncomingEmail() {
        // no op
    }

    @Id
    @Column(name = "incoming_email_id", precision = 10)
    @GeneratedValue(generator="incoming_email_sequence")
    @SequenceGenerator(name="incoming_email_sequence", sequenceName="incoming_email_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email_size", nullable = false)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @JoinColumn(name = "incoming_transport_id", nullable = false)
    @ManyToOne()
    public IncomingTransport getIncomingTransport() {
        return incomingTransport;
    }

    public void setIncomingTransport(IncomingTransport incomingTransport) {
        this.incomingTransport = incomingTransport;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "binary_content", nullable = false)
    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }
}
