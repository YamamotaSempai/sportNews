package kz.aa.sportNews.controllers;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.PostService;
import kz.aa.sportNews.util.UtilControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final PostService postService;

    private int totalPages;
    @Autowired
    private UtilControllers utilControllers;

    @Autowired
    public MainController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public String mainForAdmin(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int page) {

        PageRequest pageable = PageRequest.of(page - 1, 12);
        Page<Post> postOptional = postService.findAll(pageable);

        model.addAttribute("posts", postOptional);

        utilControllers.pageCountNumber(model, postOptional.getTotalPages());

        return "index";
    }

    @GetMapping(value = "pages/structure_progress-fb-inKZ")
    public String structureProgressPage() {
        return "pages/structure_progress-fb-inKZ.html";
    }

    @GetMapping(value = "pages/documents")
    public String documentsPage() {
        return "pages/documents.html";
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
        return "/pages/mini_football_sredi-lic-ogr-vozmozh.html.html";
    }

    @GetMapping(value = "/pages/amateur_tournaments")
    public String amateurTournamentsPage() {
        return "/pages/amateur_tournaments.html";
    }

    @GetMapping(value = "pages/calendar")
    public String calendarPage() {
        return "/pages/calendar.html.html";
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
        return "/pages/vistavka.html.html";
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
