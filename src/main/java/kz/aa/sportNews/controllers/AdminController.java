package kz.aa.sportNews.controllers;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.util.UtilImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AdminController {

    private String uploadPath;

    private final UtilImage utilImage;
    private final ApplicationContext applicationContext;

    @Autowired
    public AdminController(UtilImage utilImage, ApplicationContext applicationContext) throws IOException {
        this.utilImage = utilImage;
        this.applicationContext = applicationContext;

        Resource resource = this.applicationContext.getResource("img_src");
        uploadPath = resource.getFile().getAbsolutePath();
    }

    @RequestMapping(value = "/admin/create-post",
            method = RequestMethod.POST,
            headers = "content-type=multipart/*")
    public String createItemCap(Model model,
                                @ModelAttribute("post") @Valid Post post,
                                BindingResult bindingResult,
                                @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (!bindingResult.hasErrors()) {

            return "/admin/page";
        } else {
            post.getUrlImg().add(utilImage.saveFile(file, uploadPath));

            return "redirect:/admin/page";
        }
    }

    @RequestMapping(value = "admin/page")
    public String adminPage(Model model){

        model.addAttribute("post", new Post());

        return "admin/page";
    }
}
