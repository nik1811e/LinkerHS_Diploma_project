package course.courses;

import com.google.gson.Gson;
import course.pojo.CourseRequestTO;
import course.pojo.RequestTO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.HibernateUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.util.List;

@WebServlet(urlPatterns = "/requestinf")
public class RequestInformation extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CourseInformation.class);

    private Gson gson = new Gson();

    public List<RequestTO> getRequest(String uuidAuth) {
        LOGGER.debug(getClass().getName() + "getRequest");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            CourseRequestTO courseStructureTO = gson.fromJson(MethodUtil.getJsonRequest(session, uuidAuth), CourseRequestTO.class);
            return courseStructureTO.getRequest();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

}
