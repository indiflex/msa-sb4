package com.msa.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uniq_User_email",
                columnNames = {"email"}
        )
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false, length = 30)
    private String name;

    @ColumnDefault("0")
    private Integer point;

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "UserRole",
            joinColumns = @JoinColumn(name = "email")
    )
    @Column(name = "role")
    @Builder.Default
    @ToString.Exclude
    private List<UserRole> roles = new ArrayList<>();

    public User addRole(UserRole role) {
        if (roles == null)
            roles = new ArrayList<>();
        roles.add(role);
        return this;
    }

    public void clearRoles() {
        if (roles != null)
            roles.clear();
    }

}
