package com.example.rent.comment;
import lombok.Data;
import org.springframework.beans.BeanUtils;


import java.time.LocalDateTime;


@Data
public class CommentDTO {
    private Long id;
    private String author;
    private String text;
    private long likes;
    private LocalDateTime createTime;
    private String prettyCreateTime;

    public static CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        return commentDTO;
    }
}