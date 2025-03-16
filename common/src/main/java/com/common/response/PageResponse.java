package com.common.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record PageResponse<T>(
    List<T> contents,
    long totalElements,
    int totalPages,
    int pageNumber,
    int pageSize
) {

  public static <T> PageResponse<T> from(Page<T> page) {
    Pageable pageable = page.getPageable();

    return PageResponse.<T>builder()
        .contents(page.getContent())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .pageNumber(pageable.getPageNumber() + 1)
        .pageSize(pageable.getPageSize())
        .build();
  }
}