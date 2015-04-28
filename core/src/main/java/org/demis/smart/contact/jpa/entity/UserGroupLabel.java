package org.demis.smart.contact.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;


@Entity
@Table(name="user_group_label")
public class UserGroupLabel extends AbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupLabel.class);

    private Long id = null;
    private String label = null;
    private String locale = null;
    private UserGroup userGroup = null;

    public UserGroupLabel() {
        // no op
    }

    @Id
    @Column(name = "user_group_label_id", precision = 10)
    @GeneratedValue(generator="user_group_label_sequence")
    @SequenceGenerator(name="user_group_label_sequence", sequenceName="user_group_label_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "label", nullable = false, unique = false, length = 64)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "locale", nullable = false, unique = false, length = 5)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JoinColumn(name = "user_group_id", nullable = false)
    @ManyToOne()
    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }


}