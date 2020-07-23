package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class JspMealController {

    @Autowired
    private MealRestController mealController;

    @GetMapping("/")
    String getAll(Model model){
        model.addAttribute("meals", mealController.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    String delete(HttpServletRequest request) {
        int id = getId(request);
        mealController.delete(id);
        return "redirect:meals";
    }

    @GetMapping("/create")
    String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", mealController.get(getId(request)));
        return "mealForm";
    }

    @GetMapping("/filter")
    String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";

    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}