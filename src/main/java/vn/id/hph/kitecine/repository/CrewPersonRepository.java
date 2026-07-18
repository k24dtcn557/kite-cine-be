package vn.id.hph.kitecine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.CrewPerson;

@Repository
public interface CrewPersonRepository extends JpaRepository<CrewPerson, Long>, JpaSpecificationExecutor<CrewPerson> {}
