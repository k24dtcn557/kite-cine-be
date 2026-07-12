package vn.id.hph.kitecine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
