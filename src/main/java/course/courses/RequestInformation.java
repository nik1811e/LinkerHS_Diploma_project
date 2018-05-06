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
    private static final Logger logger = Logger.getLogger(CourseInformation.class);
    private Session session;


    Gson gson = new Gson();

    public List<RequestTO> getRequets(String uuidAuth) {
        logger.debug(getClass().getName() + "getRequets");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            CourseRequestTO courseStructureTO = gson.fromJson(MethodUtil.getJsonRequest(session, uuidAuth), CourseRequestTO.class);
            return courseStructureTO.getRequest();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
