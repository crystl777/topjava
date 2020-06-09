package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private List<MealTo> mealsList;

    @Override
    public void init(ServletConfig config) throws ServletException {
        mealsList = MealTestData.LIST_MEALS_TO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");
        req.setAttribute("meals", mealsList);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
