package vn.id.hph.kitecine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.PriceModel;

@Repository
public interface PriceModelRepository extends JpaRepository<PriceModel, Long>, JpaSpecificationExecutor<PriceModel> {
    List<PriceModel> findAllByOrderByIdDesc();
}
