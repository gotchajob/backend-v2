package com.example.gcj.repository.specification;

import lombok.Getter;

import static com.example.gcj.repository.specification.SearchOperation.*;

@Getter
public class SpecSearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private Boolean orPredicate;

    public SpecSearchCriteria (String key, SearchOperation searchOperation, Object value) {
        super();

        this.key = key;
        this.operation = searchOperation;
        this.value = value;
    }

    public SpecSearchCriteria (String predicate, String key, SearchOperation searchOperation, Object value) {
        super();

        this.orPredicate = predicate != null && predicate.equals(OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = searchOperation;
        this.value = value;
    }
    public SpecSearchCriteria(String key, String operation, String value, String prefix, String suffix) {
        SearchOperation oper = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (oper == SearchOperation.EQUALITY) {
            boolean startWithAsterisk = prefix != null && prefix.contains(ZERO_OR_MORE_REGEX);
            boolean endWithAsterisk = suffix != null && suffix.contains(ZERO_OR_MORE_REGEX);

            if (startWithAsterisk && endWithAsterisk) {
                oper = CONTAINS;
            } else if (startWithAsterisk) {
                oper = ENDS_WITH;
            } else if (endWithAsterisk) {
                oper = STARTS_WITH;
            }

        }

        this.key = key;
        this.operation = oper;
        this.value = value;
    }
}
