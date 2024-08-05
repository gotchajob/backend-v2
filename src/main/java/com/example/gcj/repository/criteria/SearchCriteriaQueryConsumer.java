package com.example.gcj.repository.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteriaQueryConsumer implements Consumer<SearchCriteria> {
    private CriteriaBuilder builder;
    private Predicate predicate;
    private Root root;

    @Override
    public void accept(SearchCriteria param) {
        switch (param.getOperation()) {
            case ">" ->
                    predicate = builder.and(predicate, builder.greaterThan(root.get(param.getKey()), param.getValue().toString()));
            case "<" ->
                    predicate = builder.and(predicate, builder.lessThan(root.get(param.getKey()), param.getValue().toString()));
            case ":" ->
                    predicate = builder.and(predicate, builder.equal(root.get(param.getKey()), param.getValue().toString()));
            case "!" ->
                    predicate = builder.and(predicate, builder.notEqual(root.get(param.getKey()), param.getValue().toString()));
            case "~" ->
                    predicate = builder.and(predicate, builder.like(root.get(param.getKey()), "%" + param.getValue().toString() + "%"));
        }
    }
}
