package org.lightsout.shared.dto;

import java.util.List;

public record PaginatedResponse<T>(
        long totalItems,
        List<T> items
) {
}
