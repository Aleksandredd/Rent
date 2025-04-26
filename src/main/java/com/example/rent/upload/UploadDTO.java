package com.example.rent.upload;

import com.example.rent.comment.CommentDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UploadDTO {
    private Long id;
    private String title;
    private String text;
    private long likes;
    private LocalDateTime createTime;
    private String prettyCreateTime;
    private List<CommentDTO> comments;

    public static UploadDTO fromUpload(Upload upload) {
        var uploadDTO = new UploadDTO();
        BeanUtils.copyProperties(upload, uploadDTO);
        uploadDTO.setComments(upload.getComments().stream().map(CommentDTO::fromComment).collect(Collectors.toList()));
        return uploadDTO;
    }
}
