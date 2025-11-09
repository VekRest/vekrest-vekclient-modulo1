package com.vekrest.vekclient.controller.dto.response;

import com.vekrest.entity.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public record ClientListResponse(
        List<Client> content,
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
