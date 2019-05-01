package kz.aa.sportNews.controllers;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.PostService;
import kz.aa.sportNews.service.UserService;
import kz.aa.sportNews.util.UtilImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Controller@PreFilter("authentication.principal.username != null")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final String uploadPath;

    private PostService postService;
    private UserService userService;

    private final UtilImage utilImage;
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void init() {

    }
    @Autowired
    public AdminController(PostService postService, UserService userService, UtilImage utilImage, ApplicationContext applicationContext) throws IOException {
        this.postService = postService;
        this.userService = userService;
        this.utilImage = utilImage;
        this.applicationContext = applicationContext;

        Resource resource = this.applicationContext.getResource("img_src");
        uploadPath = resource.getFile().getAbsolutePath();
    }

    @RequestMapping(value = "/admin/create-post",
            method = RequestMethod.POST,
            headers = "content-type=multipart/*")
    public String createItemCap(@ModelAttribute("post") @Valid Post post,
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
