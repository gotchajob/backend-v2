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

    public PageResponseDTO<ExpertAccountResponse> advanceSearchExpert(int pageNumber, int pageSize, String sortBy, String... search) {

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

        Long totalElements = getTotalElement(criteriaList);
        int totalPage = (int)Math.ceil(totalElements.doubleValue()/pageSize);
        List<Expert> experts = getExperts(pageNumber, pageSize, criteriaList, sortBy);
        return new PageResponseDTO<>(experts.stream().map(ExpertMapper::toDto).toList(), totalPage);
    }

    private List<Expert> getExperts(int pageNumber, int pageSize, List<SearchCriteria> criteriaList, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expert> query = criteriaBuilder.createQuery(Expert.class);
        Root<Expert> root = query.from(Expert.class);

        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);
        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        query.where(predicate);
        //sort
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

        return entityManager.createQuery(query).setFirstResult((pageNumber-1)*pageSize).setMaxResults(pageSize).getResultList();
    }

    private Long getTotalElement(List<SearchCriteria> criteriaList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Expert> root = query.from(Expert.class);

        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaQueryConsumer queryConsumer = new SearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);
        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        query.select(criteriaBuilder.count(root));
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }
}
