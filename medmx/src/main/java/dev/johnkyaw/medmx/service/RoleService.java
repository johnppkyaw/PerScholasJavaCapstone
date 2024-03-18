package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Role;
import java.util.List;


public interface RoleService {
    public void saveRole(Role role);
    public Role findRoleByRoleName(String name);
    public List<Role> getAllRoles();
    public List<Role> getRolesByUser(long id);
}
