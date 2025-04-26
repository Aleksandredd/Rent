package com.example.rent.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentForm {

    @NotNull(message = "Upload ID is required")
    private final Long uploadId;

    @NotBlank(message = "Author name cannot be blank")
    @Size(max = 50, message = "Author name must be at most 50 characters")
    private String author;

    @NotBlank(message = "Comment text cannot be blank")
    @Size(max = 500, message = "Comment text must be at most 500 characters")
    private String text;
}
