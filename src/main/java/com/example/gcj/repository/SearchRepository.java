package com.example.gcj.repository;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.ExpertAccountResponse;
import com.example.gcj.model.Expert;
import com.example.gcj.model.User;
import com.example.gcj.repository.criteria.SearchCriteria;
import com.example.gcj.repository.criteria.SearchCriteriaQueryConsumer;
import com.example.gcj.util.mapper.ExpertMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.gcj.util.Regex.SEARCH_OPERATION;
import static com.example.gcj.util.Regex.SORT_BY;

@Component
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public <T> Page<T> getEntitiesPage(Class<T> entityClass, int pageNumber, int pageSize, String sortBy, String... search) {
        List<SearchCriteria> criteriaList = getCriteriaList(search);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);
        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        query.where(predicate);

        // sort
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String name = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("desc")) {
                    query.orderBy(criteriaBuilder.desc(root.get(name)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(name)));
                }
            }
        }

        List<T> results = entityManager.createQuery(query)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        long totalElements = getTotalElement(entityClass, criteriaList);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return new PageImpl<>(results, pageable, totalElements);
    }

    public <T> Long getTotalElement(Class<T> entityClass, List<SearchCriteria> criteriaList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<T> root = query.from(entityClass);

        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);
        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        query.select(criteriaBuilder.count(root));
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    private List<SearchCriteria> getCriteriaList(String ...search) {
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (search != null) {
            for (String s : search) {
                Pattern pattern = Pattern.compile(SEARCH_OPERATION);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        return criteriaList;
    }
}
