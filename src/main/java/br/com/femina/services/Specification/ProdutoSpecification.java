package br.com.femina.services.Specification;

import br.com.femina.dto.produto.Filters;
import br.com.femina.entities.Produto;
import br.com.femina.enums.Enums;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProdutoSpecification {
    public Specification<Produto> getProductsFilters(Filters filters) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filters.getCategoriaIds().size() > 0 && !filters.getCategoriaIds().isEmpty()) {
                predicates.add(criteriaBuilder.in(root.join("categoria").get("id")).value(filters.getCategoriaIds()));
            }
            if (filters.getMarcaIds().size() > 0 && !filters.getMarcaIds().isEmpty()) {
                predicates.add(criteriaBuilder.in(root.join("marca").get("id")).value(filters.getMarcaIds()));
            }
            if (filters.getCor() != null && !filters.getCor().isEmpty() && !filters.getCor().equals("")) {
                predicates.add(criteriaBuilder.like(root.get("cor"), filters.getCor()));
            }
            if (!filters.getTamanho().equals(Enums.Tamanhos.ALL)) {
                predicates.add(criteriaBuilder.equal(root.get("tamanho"), filters.getTamanho()));
            }
            predicates.add(criteriaBuilder.equal(root.get("isActive"), true));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
