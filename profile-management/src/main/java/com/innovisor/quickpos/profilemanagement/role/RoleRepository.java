package com.innovisor.quickpos.profilemanagement.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role searchRoleByRoleName(Roles roles);
}
