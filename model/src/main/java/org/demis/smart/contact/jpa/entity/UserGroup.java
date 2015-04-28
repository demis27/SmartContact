package org.demis.smart.contact.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="user_group")
public class UserGroup extends AbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroup.class);

    private Long id = null;
    private String name = null;
    private List<UserGroupLabel> labels = new ArrayList<UserGroupLabel>();
    private List<ApplicationUser> users = new ArrayList<ApplicationUser>();

    public UserGroup() {
        // no op
    }

    @Id
    @Column(name = "user_group_id", precision = 10)
    @GeneratedValue(generator="user_group_sequence")
    @SequenceGenerator(name="user_group_sequence", sequenceName="user_group_sequence")
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

    @OneToMany(mappedBy = "userGroup")
    public List<UserGroupLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<UserGroupLabel> labels) {
        this.labels = labels;
    }

    public boolean addLabel(UserGroupLabel label) {
        if (getLabels().add(label)) {
            label.setUserGroup(this);
            return true;
        }
        return false;
    }

    public boolean removeLabel(UserGroupLabel label) {
        if (getLabels().remove(label)) {
            label.setUserGroup(null);
            return true;
        }
        return false;
    }

    @OneToMany(mappedBy = "userGroup")
    public List<ApplicationUser> getUsers() {
        return users;
    }

    public void setUsers(List<ApplicationUser> users) {
        this.users = users;
    }

    public boolean addUser(ApplicationUser user) {
        if (getUsers().add(user)) {
            user.setUserGroup(this);
            return true;
        }
        return false;
    }

    public boolean removeUser(ApplicationUser user) {
        if (getUsers().remove(user)) {
            user.setUserGroup(null);
            return true;
        }
        return false;
    }

}