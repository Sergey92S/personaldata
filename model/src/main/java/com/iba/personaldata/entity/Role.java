package com.iba.personaldata.entity;

import com.iba.personaldata.util.RoleType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Role implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Getter
    @Setter
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<PersonRole> personRoleSet;

}
