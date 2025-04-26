package com.example.rent.comment;

import com.example.rent.upload.Upload;
import com.example.rent.upload.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final UploadRepository uploadRepository;

    @Transactional
    public Comment saveComment(Long uploadId, String author, String text) {
        Upload upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("Rent object not found with ID: " + uploadId));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(author);
        comment.setUpload(upload);

        return repository.save(comment);
    }
}