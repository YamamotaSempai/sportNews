package kz.aa.sportNews.controllers;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.PostService;
import kz.aa.sportNews.service.UserService;
import kz.aa.sportNews.util.UtilImage;
import kz.aa.sportNews.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller@PreFilter("authentication.principal.username != null")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private PostService postService;
    private UserService userService;

    private final UtilImage utilImage;

    @Autowired
    public AdminController(PostService postService, UserService userService, UtilImage utilImage) throws IOException {
        this.postService = postService;
        this.userService = userService;
        this.utilImage = utilImage;
    }

    @RequestMapping(value = "/admin/create-post",
            method = RequestMethod.POST,
            headers = "content-type=multipart/*")
    public String createPost(@ModelAttribute("post") @Valid Post post,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "admin/page";
        } else {
            post.setUrlImg(utilImage.saveFile(file, uploadPath));
            post.setUser(userService.findCurrentUser().get());
            postService.saveOrUpdate(post);
            return "redirect:/admin/page";
        }
    }

    @PostMapping(value = "deletePost", produces = "application/json")
    public @ResponseBody
    ValidationResponse deletePost(@RequestParam(name = "id") String id) {

        postService.deletePost(Long.valueOf(id));

        return new ValidationResponse();
    }


    @RequestMapping(value = "admin/page")
    public String adminPage(Model model,
                            @RequestParam(value = "id", required = false) Long id){

        if (id != null)
            model.addAttribute("post", postService.findById(id));
        else
            model.addAttribute("post", new Post());

        return "admin/page.html";
    }
}
