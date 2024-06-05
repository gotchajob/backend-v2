package com.example.gcj.dto.other;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class PageResponseDTO<T> implements Serializable {
    private List<T> list;
    private int totalPage;

    public PageResponseDTO(List<T> list, int totalPage) {
        this.list = list;
        this.totalPage = totalPage;
    }
}
