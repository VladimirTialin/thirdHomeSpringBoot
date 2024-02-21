package ru.gb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private long id;
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass;
    @OneToOne
    @MapsId
    private Reader reader;

    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "readerId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    public User(String login, String pass, List<GrantedAuthority> grantedAuthorityList) {
    }

    public void addRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
