package kz.aa.sportNews.controllers;

import kz.aa.sportNews.dto.SearchForm;
import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.PostService;
import kz.aa.sportNews.util.UtilControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MainController {

    private final PostService postService;
    private final UtilControllers utilControllers;
    private final ApplicationContext applicationContext;

    private final String uploadPath;

    @Autowired
    public MainController(PostService postService, UtilControllers utilControllers, ApplicationContext applicationContext) throws IOException {
        this.postService = postService;
        this.utilControllers = utilControllers;
        this.applicationContext = applicationContext;

        Resource resource = this.applicationContext.getResource("pdf");
        uploadPath = resource.getFile().getAbsolutePath();
    }

    @RequestMapping("pages/post_page")
    public String postPage(Model model,
                           @RequestParam(value = "id", required = true) Long id) {

        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "pages/post_page.html";
    }

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public String main(Model model,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "key_word", defaultValue = "", required = false) String keyWord) {

        PageRequest pageable = PageRequest.of(page - 1, 12);

        Page<Post> postOptional;
        if (keyWord.equalsIgnoreCase("")) {
            postOptional = postService.findAll(pageable);
            model.addAttribute("isSearch", false);
        } else {
            postOptional = postService.findByTitleLike(keyWord, pageable);
            model.addAttribute("keyWord", keyWord);
            model.addAttribute("isSearch", true);
        }

        model.addAttribute("posts", postOptional);
        model.addAttribute("searchDto", new SearchForm(""));

        utilControllers.pageCountNumber(model, postOptional.getTotalPages());

        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(SearchForm searchForm) {

        if (searchForm.getSearchText() != null && !searchForm.getSearchText().equalsIgnoreCase(""))
            return "redirect:/home?key_word=" + searchForm.getSearchText();

        return "redirect:/home";
    }

    @GetMapping(value = "pages/structure_progress-fb-inKZ")
    public String structureProgressPage() {
        return "pages/structure_progress-fb-inKZ.html";
    }

    @GetMapping(value = "pages/documents")
    public String documentsPage() {
        return "pages/documents.html";
    }

    @RequestMapping("/pdf/{fileName:.+}")
    public void downloadPDFResource(HttpServletResponse response,
                                    @PathVariable("fileName") String fileName) {
        //If user is not authorized - he should be thrown out from here itself

        //Authorized user will download the file
        Path file = Paths.get(uploadPath, fileName);
        if (Files.exists(file)) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping(value = "pages/structure")
    public String structurePage() {
        return "/pages/structure.html";
    }

    @GetMapping(value = "pages/program_development")
    public String programDevelopmentPage() {
        return "/pages/program_development.html";
    }

    @GetMapping(value = "pages/infra_structure")
    public String infraStructurePage() {
        return "/pages/infra_structure.html";
    }

    @GetMapping(value = "pages/football_center")
    public String footballCenterPage() {
        return "/pages/football_center.html";
    }

    @GetMapping(value = "pages/mini_football_futzal")
    public String miniFootballFutzal() {
        return "/pages/mini_football_futzal.html";
    }

    @GetMapping(value = "/pages/kids_tournaments")
    public String kidsTournamentsPage() {
        return "/pages/kids_tournaments.html";
    }

    @GetMapping(value = "/pages/woman_football")
    public String womanFootballPage() {
        return "/pages/woman_football.html";
    }

    @GetMapping(value = "/pages/mini_football_sredi-lic-ogr-vozmozh")
    public String miniFootballAmongPersonsWithDisabilitiesPage() {
        return "/pages/mini_football_sredi-lic-ogr-vozmozh.html";
    }

    @GetMapping(value = "/pages/amateur_tournaments")
    public String amateurTournamentsPage() {
        return "/pages/amateur_tournaments.html";
    }

    @GetMapping(value = "pages/calendar")
    public String calendarPage() {
        return "/pages/calendar.html";
    }

    @GetMapping(value = "pages/urok_football")
    public String lessonFootballPage() {
        return "/pages/urok_football.html";
    }

    @GetMapping(value = "pages/coach_courses")
    public String coachCoursesPage() {
        return "/pages/coach_courses.html";
    }

    @GetMapping(value = "pages/private_school")
    public String privateSchoolPage() {
        return "/pages/private_school.html";
    }

    @GetMapping(value = "pages/dushsa")
    public String dushaPage() {
        return "/pages/dushsa.html";
    }

    @GetMapping(value = "pages/young_arbitr")
    public String youngArbitrPage() {
        return "/pages/young_arbitr.html";
    }

    @GetMapping(value = "pages/vistavka")
    public String exhibitionPage() {
        return "/pages/vistavka.html";
    }

    @GetMapping(value = "pages/lesson")
    public String lessonPage() {
        return "/pages/lesson.html";
    }

    @GetMapping(value = "pages/contacts")
    public String contactsPage() {
        return "/pages/contacts.html";
    }

    @GetMapping(value = "pages/about")
    public String aboutPage() {
        return "/pages/about.html";
    }
}
