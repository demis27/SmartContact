package org.demis.smart.contact.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;


@Entity
@Table(name="application_user")
public class ApplicationUser extends AbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUser.class);

    private Long id = null;
    private String email = null;
    private String firstName = null;
    private String lastName = null;
    private String login = null;
    private String password = null;
    private UserGroup userGroup = null;

    public ApplicationUser() {
        // no op
    }

    @Id
    @Column(name = "user_id", precision = 10)
    @GeneratedValue(generator="application_user_sequence")
    @SequenceGenerator(name="application_user_sequence", sequenceName="application_user_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email", nullable = true, unique = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "first_name", nullable = true, unique = false, length = 64)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name", nullable = true, unique = false, length = 64)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name = "login", nullable = false, unique = true, length = 64)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @Column(name = "password", nullable = false, unique = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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