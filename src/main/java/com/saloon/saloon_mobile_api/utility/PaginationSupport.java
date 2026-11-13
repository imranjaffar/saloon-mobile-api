package com.saloon.saloon_mobile_api.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginationSupport {

    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    private PaginationSupport() {
    }

    public static Pageable page(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page must be zero or greater.");
        }
        if (size < 1 || size > MAX_SIZE) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and " + MAX_SIZE + "."
            );
        }

        return PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );
    }
}
