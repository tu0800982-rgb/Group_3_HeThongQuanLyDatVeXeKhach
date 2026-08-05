package com.busbooking.dto;

import java.time.LocalDateTime;

public record TransactionHistoryDTO(LocalDateTime time, String type, String description) {
}