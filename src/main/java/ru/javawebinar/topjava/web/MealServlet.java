package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.config.Config;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        //toDo преобразовать строку в дату
       // LocalDateTime localDateTime = LocalDateTime.of() req.getParameter("locaDateTime");

        final boolean isCreate = (id == null || id.length() == 0);

        Meal meal;

        if (isCreate) {
     //       meal = new Meal(req.getParameter());
        } else {
            meal = storage.get(id);
        }



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");

        String id = req.getParameter("id");
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("meals", MealsUtil.filteredByStreams(storage.getListMeals(), LocalTime.MIN,
                    LocalTime.MAX, MealsUtil.MAX_CALORIES));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        Meal meal;
        switch (action) {
            case "add":
                meal = new Meal();
                break;
            case "delete":
                storage.delete(id);
                //toDo поставить break
            case "edit":
                meal = storage.get(id);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }
}
