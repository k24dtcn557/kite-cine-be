package vn.id.hph.kitecine.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.param.PriceModelParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.PriceModel;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.PriceModelMapper;
import vn.id.hph.kitecine.repository.PriceModelRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceModelService {
    PriceModelRepository priceModelRepository;
    PriceModelMapper priceModelMapper;

    public PriceModel create(PriceModelParam param) {
        var entity = priceModelMapper.toPriceModel(param);
        return priceModelRepository.save(entity);
    }

    public PriceModel update(Long id, PriceModelParam param) {
        var entity =
                priceModelRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRICE_MODEL_NOT_FOUND));
        priceModelMapper.update(entity, param);
        return priceModelRepository.save(entity);
    }

    public PageResponse<PriceModel> search(KeywordStatusSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<PriceModel> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(param.getKeyword())) {
                Predicate searchPre = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + param.getKeyword().toLowerCase() + "%");
                predicates.add(searchPre);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        var page = priceModelRepository.findAll(query, pageRequest);
        return PageResponse.<PriceModel>builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .data(page.getContent())
                .build();
    }

    public List<PriceModel> getAll() {
        return priceModelRepository.findAllByOrderByIdDesc();
    }

    public PriceModel get(Long id) {
        return priceModelRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRICE_MODEL_NOT_FOUND));
    }

    public void delete(Long id) {
        var entity =
                priceModelRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRICE_MODEL_NOT_FOUND));
        priceModelRepository.delete(entity);
    }
}
