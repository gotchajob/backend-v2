package com.example.gcj.Shared.util;

public interface Regex {
    String PHONE = "";
    String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";
    String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    String SORT_BY = "(\\w+?)(:)(asc|desc)";
    String SEARCH_OPERATION = "(\\w+?)(:|<|>|!|~)(.*)";
    String SEARCH_SPEC_OPERATOR = "(\\w+?)([<:>~!])(.*)(\\p{Punct}?)(\\p{Punct}?)";

}
