package com.example.gcj.Repository_Layer.specification;

import com.example.gcj.Repository_Layer.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.example.gcj.Repository_Layer.specification.SearchOperation.*;
import static com.example.gcj.Repository_Layer.specification.SearchOperation.STARTS_WITH;

public class ObjectSpecificationBuilder {
    public final List<SpecSearchCriteria> param;

    public ObjectSpecificationBuilder(List<SpecSearchCriteria> param) {
        this.param = param;
    }

    public ObjectSpecificationBuilder() {
        this.param = new ArrayList<>();
    }



    public ObjectSpecificationBuilder with(String key, String operation, Object value, String prefix, String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public ObjectSpecificationBuilder with(String orPredicate, String key, String operation, Object value, String prefix, String suffix) {
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
        param.add(new SpecSearchCriteria(orPredicate, key, oper, value));
        return this;
    }

    public Specification<User> build() {
        if (param == null || param.isEmpty()) return null;

        Specification<User> specification = new ObjectSpecification(param.get(0));

        for (int i = 1; i< param.size(); i++) {
            specification = param.get(i).getOrPredicate()
                    ? Specification.where(specification).or(new ObjectSpecification(param.get(i)))
                    : Specification.where(specification).and(new ObjectSpecification(param.get(i)));
        }

        return specification;
    }

}
