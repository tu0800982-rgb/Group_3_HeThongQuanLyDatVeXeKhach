package com.busbooking.dto;

<<<<<<< HEAD
public class TransactionHistoryDTO {
    
=======
import java.time.LocalDateTime;

public record TransactionHistoryDTO(LocalDateTime time, String type, String description) {
>>>>>>> origin/main
}
