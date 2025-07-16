package org.example.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserInfo {
    @Id
    @Column(name = "user_info")

    private String userId;
    private String username;
    private String password;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns =@JoinColumn(name = "role_id")
    )
    private Set<UserRoles> roles = new HashSet<>();
}
