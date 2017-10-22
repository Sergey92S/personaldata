package com.iba.personaldata.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class PersonRole implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @Getter
    @Setter
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "fk_role_id")
    private Role role;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "fk_person_id")
    private Person person;

}



