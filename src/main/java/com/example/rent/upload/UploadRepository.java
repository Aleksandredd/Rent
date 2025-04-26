package com.example.rent.upload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UploadRepository extends CrudRepository<Upload, Long> {

    Page<Upload> findAll(Pageable pageable);

    List<Upload> findTop5ByOrderByLikesDesc();

    // Returns number of comments for a given topic
    long countByCommentsUploadId(Long uploadId);

    @Query("SELECT t FROM Upload t LEFT JOIN t.comments c GROUP BY t.id ORDER BY COUNT(c) DESC")
    List<Upload> findTop5ByMostComments(Pageable pageable);

    @Modifying
    @Query("UPDATE Upload t SET t.likes = t.likes + 1 WHERE t.id = :id")
    void like(Long id);
}