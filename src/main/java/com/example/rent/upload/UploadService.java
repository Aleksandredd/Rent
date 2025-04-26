package com.example.rent.upload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadService {

    @Getter
    @Value("${uploads.page-size:10}")
    private int pageSize;

    private final UploadRepository repository;

    public Page<UploadDTO> getUpload(int page) { // You may also pass the 'size' from the outside ;)
        // (1) Specify sorting order by field
        Sort sort = Sort.by(Sort.Order.by("createTime").with(Sort.Direction.DESC));

        // (2) Create pageable request object
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // (3) Pass pageable to findAll() method :)
        return repository.findAll(pageable).map(UploadDTO::fromUpload);
    }

    public List<UploadDTO> getTop5ByLikes() {
        return repository.findTop5ByOrderByLikesDesc().stream()
                .map(UploadDTO::fromUpload)
                .collect(Collectors.toList());
    }

    public List<UploadDTO> getTop5HottestUploads() {
        return repository.findTop5ByMostComments(
                        PageRequest.of(0, 5) // This will give us only 5 entries
                ).stream()
                .map(UploadDTO::fromUpload)
                .collect(Collectors.toList());
    }

    public UploadDTO getUploadById(Long id) {
        return UploadDTO.fromUpload(repository.findById(id).orElseThrow());
    }

    public void deleteUploadById(long id) {
        repository.deleteById(id);
    }

    // Note: if @Transactional is on likeUpload(Long id),
    // then it is not needed on like(Long id) in the repository.
    @Transactional // Required for modifying queries since they change the database state.
    public void likeUpload(long id) {
        repository.like(id);
    }

    @Transactional // Ensures that all operations succeed or all fail!
    public UploadDTO save(String title, String text) {
        Upload upload = new Upload();
        upload.setTitle(title);
        upload.setText(text);
        return UploadDTO.fromUpload(repository.save(upload));
    }
}
