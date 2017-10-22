package com.iba.personaldata.entity;

import com.iba.personaldata.util.Position;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "personRoleSet")
@ToString(exclude = "personRoleSet")
@Entity
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 2, max = 30, message = "name must be 2-30 symbols")
    @Getter
    @Setter
    @Column
    private String name;

    @Length(min = 5, max = 30, message = "surname must be 5-30 symbols")
    @Getter
    @Setter
    @Column
    private String surname;

    @Email
    @Length(min = 10, max = 30, message = "login validation is wrong")
    @Getter
    @Setter
    @Column(unique = true)
    private String login;

    @Length(min = 10, max = 100, message = "password must be 10-100 symbols")
    @Getter
    @Setter
    @Column
    private String password;

    @Getter
    @Setter
    @Column
    private int active;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<PersonRole> personRoleSet;

    @Getter
    @Setter
    @Column
    private Date experience;

    @Getter
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private Position position;


}
