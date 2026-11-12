package com.denison.springecom.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {}