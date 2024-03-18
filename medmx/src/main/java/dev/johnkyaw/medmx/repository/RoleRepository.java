package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String name);

    public Role findRoleByName(String role);

    @Query(value = "select * from role where role.id= (select role_id from physician_roles where physician_id = :id)", nativeQuery = true)
    public List<Role> findRoleByUser(@Param("id") long id);

}
