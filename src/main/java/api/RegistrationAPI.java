package api;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/user/add")
public class RegistrationAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(getClass().getName() + "\t" + "doPost Method");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");



    }
}
