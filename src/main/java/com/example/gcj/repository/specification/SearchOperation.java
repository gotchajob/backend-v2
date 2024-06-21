package com.example.gcj.repository.specification;

public enum SearchOperation {
    EQUALITY, NEGATION, LESS_THAN, GREATER_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;
    public static final String[] SIMPLE_OPERATION_SET = {":", "!", "<", ">", "~",};
    public static final String OR_PREDICATE_FLAG = "'";
    public static final String ZERO_OR_MORE_REGEX = "*";
    public static final String OR_OPERATOR = "OR";
    public static final String END_OPERATOR = "END";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";

    public static SearchOperation getSimpleOperation(final char input) {
        return switch (input) {
            case ':' -> EQUALITY;
            case '!' -> NEGATION;
            case '>' -> GREATER_THAN;
            case '<' -> LESS_THAN;
            case '~' -> LIKE;

            default -> null;
        };
    }
}
