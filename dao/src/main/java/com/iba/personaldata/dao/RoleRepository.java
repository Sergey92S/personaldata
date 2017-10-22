package com.iba.personaldata.dao;

import com.iba.personaldata.entity.Role;
import com.iba.personaldata.util.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(RoleType role);

}
