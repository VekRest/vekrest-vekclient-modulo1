package com.vekrest.vekclient.controller.dto.response;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public record ClientListResponse(
        List<ClientResponse> content,
        Pageable pageable,
        boolean last,
        long totalElements,
        int totalPages,
        boolean first,
        int size,
        int number,
        Sort sort,
        int numberOfElements,
        boolean empty
) {
}
