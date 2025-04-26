package com.example.rent.upload;

import com.example.rent.comment.CommentForm;
import com.example.rent.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;
    private final CommentService commentService;

    @GetMapping("/upload")
    public String page() {
        return "redirect:/upload/0";
    }

    @GetMapping("/upload/{page}")
    public String page(@PathVariable(name = "page") Integer page, Model model) {
        Page<UploadDTO> uploadsPage = uploadService.getUpload(page);
        model.addAttribute("topics", uploadsPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", uploadsPage.getTotalPages());
        return "upload/page";
    }

    @GetMapping("/upload/{id}")
    public String single(@PathVariable("id") long id, Model model) {
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm(id));
        }
        model.addAttribute("upload", uploadService.getUploadById(id));
        return "upload/single";
    }

    @PostMapping("/upload/{id}")
    public String writeComment(@Valid @ModelAttribute("commentForm") CommentForm form,
                               BindingResult result,
                               @PathVariable("id") long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentForm", form);
            return single(id, model);
        }
        try {
            commentService.saveComment(id, form.getAuthor(), form.getText());
            redirectAttributes.addFlashAttribute("successMessage", "Comment created!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", String.format("An error occurred: %s",
                    e.getMessage()));
        }
        return String.format("redirect:/upload/%d", form.getUploadId());
    }

    @GetMapping("/upload/{id}/like")
    public String like(@PathVariable("id") long id) {
        uploadService.likeUpload(id);
        return String.format("redirect:/upload/%d", id);
    }

    @GetMapping("/upload/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        uploadService.deleteUploadById(id);
        return "redirect:/uploads";
    }

    @GetMapping("/upload/new")
    public String writeNewUpload(Model model) {
        if (!model.containsAttribute("uploadForm")) {
            model.addAttribute("uploadForm", new UploadForm());
        }
        return "upload/new";
    }

    @PostMapping("/upload/new")
    public String writeNewUpload(@Valid @ModelAttribute("uploadForm") UploadForm form,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("uploadForm", form);
            return "upload/new";
        }
        try {
            UploadDTO newUpload = uploadService.save(form.getTitle(), form.getText());
            redirectAttributes.addFlashAttribute("successMessage", "Topic created!");

            // Also, construct URL for newly created topic
            String newUploadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/upload/{id}")
                    .buildAndExpand(newUpload.getId())
                    .toUriString();
            redirectAttributes.addFlashAttribute("newuploadURL", newUploadURL);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", String.format("An error occurred: %s",
                    e.getMessage()));
        }
        return "redirect:/upload/new";
    }
}
