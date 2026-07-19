package vn.id.hph.kitecine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    List<Movie> findAllByOrderByIdDesc();

    List<Movie> findAllByHighlightedAndStatus(boolean highlighted, String status);

    List<Movie> findAllByStatus(String name);
}
