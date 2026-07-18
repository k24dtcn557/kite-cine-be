package vn.id.hph.kitecine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.CrewMember;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long>, JpaSpecificationExecutor<CrewMember> {
    List<CrewMember> findByMovieId(Long movieId);
}
