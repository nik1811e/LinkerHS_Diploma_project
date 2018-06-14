package course.courses;

import entity.AuthInfEntity;
import entity.CourseEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = "/search")
public class Searching extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AccessHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            resp.setCharacterEncoding("UTF-8");
            resp.sendRedirect("/pages/search.jsp?uuid=" + req.getParameter("uuidAuth") + "&&name=" + req.getParameter("name") + "&&type=" + req.getParameter("type"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<CourseEntity> searchCourse(String name, String type, String uuid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (type.equals("all")) {
                return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE nameCourse=:name")
                        .setParameter("name", name).list();
            }else {
                return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE nameCourse=:name and authById=:id")
                        .setParameter("name", name).setParameter("id", session.load(AuthInfEntity.class,MethodUtil.getIdAuthByUUID(session, uuid)) ).list();            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
