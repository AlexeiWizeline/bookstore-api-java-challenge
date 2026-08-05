package com.wizeline.bookstore.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing an incoming HTTP request payload to create or update a Book.
 * Configured as a modern Java 21 Record for immutability.
 */
public record BookRequest(

        @NotBlank(message = "Title must not be blank")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Author must not be blank")
        @Size(max = 255, message = "Author must not exceed 255 characters")
        String author,

        @NotBlank(message = "ISBN must not be blank")
        @Pattern(
                regexp = "^(?:ISBN(?:-13)?:?\\s*)?(?=[0-9X]{10}$|(?=(?:[0-9]+[-\\s]){3})[0-9-\\sX]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[-\\s]){4})[0-9-\\s]{17}$)(?:97[89][-\\s]?)?[0-9]{1,5}[-\\s]?[0-9]+[-\\s]?[0-9]+[-\\s]?[0-9X]$",
                message = "ISBN must be a valid ISBN-10 or ISBN-13 format"
        )
        String isbn,

        @NotNull(message = "Published year must not be null")
        @Min(value = 1000, message = "Published year must be a valid 4-digit year starting from 1000")
        @Max(value = 2100, message = "Published year cannot exceed 2100")
        Integer publishedYear,

        Boolean available
) {
        /**
         * Compact constructor to ensure non-null default value for optional 'available' flag.
         */
        public BookRequest {
                if (available == null) {
                        available = true;
                }
        }
}
