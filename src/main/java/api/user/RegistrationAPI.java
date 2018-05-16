package api.user;

import api.pojo.AuthRegAPITO;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import user.profile.Registration;
import util.FinalValueUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/user/add")
public class RegistrationAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info(getClass().getName() + "\t" + "doPost Method");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String responseMessage = "";
        AuthRegAPITO userRegTOAPI = new Gson().fromJson(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())), AuthRegAPITO.class);
        LOGGER.info(getClass().getName() + "\t" + "userRegTOAPI: " + userRegTOAPI.toString());
        if (validateRequest(userRegTOAPI)) {
            if (Pattern.compile(FinalValueUtil.REGEXP_EMAIL,
                    Pattern.CASE_INSENSITIVE).matcher(userRegTOAPI.getEmail()).find()) {
                boolean isUserReg =  new Registration().doRegistration(
                        userRegTOAPI.getLogin(),
                        userRegTOAPI.getPassword(),
                        userRegTOAPI.getEmail(),
                        userRegTOAPI.getFname(),
                        userRegTOAPI.getLname(),
                        userRegTOAPI.getBday()
                );
                LOGGER.info(getClass().getName() + "\t" + "isUserReg: " + isUserReg);
                if (isUserReg)
                    responseMessage = "{\"message\":\"Success\"}";
                else
                    responseMessage = "{\"message\":\"Failed\"}";
            } else
                responseMessage = "{\"message\":\"Email isn't correct\"}";
        } else
            responseMessage = "{\"message\":\"There is one or more field(s) is empty\"}";
        LOGGER.info(getClass().getName() + "\t" + "responseMessage: " + responseMessage);
        resp.getWriter().write(responseMessage);

    }

    private boolean validateRequest(AuthRegAPITO to) {
        LOGGER.info(getClass().getName() + "\t" + "validateRequest Method");
        return !StringUtils.isBlank(to.getLogin()) &&
                !StringUtils.isBlank(to.getEmail()) &&
                !StringUtils.isBlank(to.getPassword()) &&
                !StringUtils.isBlank(to.getFname()) &&
                !StringUtils.isBlank(to.getLname()) &&
                !StringUtils.isBlank(to.getBday());
    }
}
