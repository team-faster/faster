package com.common.response;

import java.util.List;
import java.util.function.Function;
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

  private static <U, T> PageResponse<U> from(PageResponse<T> PageResponse, List<U> list) {
    return PageResponse.<U>builder()
        .contents(list)
        .totalElements(PageResponse.totalElements())
        .totalPages(PageResponse.totalPages())
        .pageNumber(PageResponse.pageNumber())
        .pageSize(PageResponse.pageSize())
        .build();
  }

  public <U> PageResponse<U> map(Function<T, U> mapper) {

    return PageResponse.from(this, this.contents.<T>stream()
        .map(t -> mapper.apply(t))
        .toList());
  }
}