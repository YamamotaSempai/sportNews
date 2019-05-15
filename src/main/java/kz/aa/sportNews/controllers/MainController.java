package kz.aa.sportNews.controllers;

import kz.aa.sportNews.dto.SearchForm;
import kz.aa.sportNews.dto.UserDto;
import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.PostService;
import kz.aa.sportNews.service.email.EmailService;
import kz.aa.sportNews.util.ErrorMessage;
import kz.aa.sportNews.util.UtilControllers;
import kz.aa.sportNews.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Value(value = "classpath:/pdf")
    private Resource pathResources;

    private final EmailService emailService;
    private final PostService postService;
    private final UtilControllers utilControllers;

    @Autowired
    public MainController(PostService postService, UtilControllers utilControllers, EmailService emailService) {
        this.postService = postService;
        this.utilControllers = utilControllers;
        this.emailService = emailService;
    }

    @RequestMapping("pages/post_page")
    public String postPage(Model model,
                           @RequestParam(value = "id", required = true) Long id) {

        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "pages/post_page.html";
    }


    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET, produces = MediaType.ALL_VALUE + ";charset=UTF-8")
    public String main(Model model,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "key_word", defaultValue = "", required = false) String keyWord) {

        PageRequest pageable = PageRequest.of(page - 1, 12);

        Page<Post> postOptional;
        if (keyWord.equalsIgnoreCase("")) {
            postOptional = postService.findAllByOrderByDate(pageable);
            model.addAttribute("isSearch", false);
        } else {
            postOptional = postService.findByTitleLikeOrderByDate(keyWord, pageable);
            model.addAttribute("keyWord", keyWord);
            model.addAttribute("isSearch", true);
        }

        model.addAttribute("posts", postOptional);
        model.addAttribute("searchDto", new SearchForm(""));

        utilControllers.pageCountNumber(model, postOptional.getTotalPages());

        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(SearchForm searchForm) {

        String encodedId = null;
        if (searchForm.getSearchText() != null && !searchForm.getSearchText().equalsIgnoreCase("")) {
            try {
                encodedId = URLEncoder.encode(searchForm.getSearchText(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return new ModelAndView(new RedirectView("/home?key_word=" + encodedId, true, true, false));
        }

        return new ModelAndView(new RedirectView("/home", true, true, false));
    }

    @PostMapping(value = "sendEmail", produces = "application/json")
    public @ResponseBody
    ValidationResponse sendEmailViaAjax(@ModelAttribute(value = "user") UserDto userDto,
                                        BindingResult result
    ) throws MessagingException {

        ValidationResponse res = new ValidationResponse();
        final List<ErrorMessage> errorMessageList = new ArrayList<>();

        if (result.hasErrors()) {

            res.setStatus("FAIL");
            List<FieldError> allErrors = result.getFieldErrors();
            final List<ErrorMessage> errorMessages = new ArrayList<>();
            allErrors.forEach((objectError) -> errorMessages.add(new ErrorMessage(objectError.getField(), objectError.getDefaultMessage())));
            res.setErrorMessageList(errorMessages);

        } else if (userDto.getContext() != null && userDto.getTitle() != null && userDto.getName() != null) {
            res.setStatus("SUCCESS");
            emailService.sendSimpleMessage(userDto.getTitle(), userDto.getContext(), userDto.getName());
        } else {
            res.setStatus("FAIL");
            errorMessageList.add(new ErrorMessage("FORM FAIL", "Введите корректное данные!"));
        }

        res.setErrorMessageList(errorMessageList);

        return res;
    }


    @GetMapping(value = "pages/structure_progress-fb-inKZ")
    public String structureProgressPage() {
        return "pages/structure_progress-fb-inKZ.html";
    }

    @GetMapping(value = "pages/documents")
    public String documentsPage() {
        return "pages/documents.html";
    }

    @RequestMapping(value = "/pdf/{fileName:.+}", method = RequestMethod.GET)
    public void downloadPDFResource(HttpServletResponse response,
                                    @PathVariable("fileName") String fileName) throws IOException {

        Path file = Paths.get(String.valueOf(pathResources.getFile()), fileName);
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
