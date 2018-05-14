package course.courses;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import entity.AuthInfEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(urlPatterns = "/editcourse")
public class CourseEditing extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CourseEditing.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String uuidCourse = req.getParameter("uuidCourseEdit").trim();
        String name = req.getParameter("nameCourseEdit").trim();
        String status = req.getParameter("statusCourseEdit").trim();
        String uuidAuth = req.getParameter("uuidAuth");
        try {
            if(updateCourse(session,transaction,uuidCourse,Integer.parseInt(req.getParameter("courseCategoryEdit").trim()),
                    name,status,prepareEditCourse(session,uuidCourse,name,status,req.getParameter("courseDescEdit").trim(),uuidAuth))){
                resp.sendRedirect("/pages/course.jsp?uuidAuth="+uuidAuth+"&&uuidCourse="+uuidCourse);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());

        }

    }

    private String prepareEditCourse(Session session, String uuidCourse, String nameCourse, String status, String courseDesc,String uuidAuth) {
        CourseStructureTO courseStructure = gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
        if (MethodUtil.isUniqueNameCourse(session, nameCourse,session.load(AuthInfEntity.class,MethodUtil.getIdAuthByUUID(session,uuidAuth)))) {
            courseStructure.setNameCourse(nameCourse);
            courseStructure.setDescriptionCourse(courseDesc);
            courseStructure.setStatus(status);
            return gson.toJson(courseStructure);

        } else {
            return null;
        }
    }

    private boolean updateCourse(Session session, Transaction transaction, String uuidCourse, int courseCategory, String nameCourse, String status, String newStructure) {
        try {
            session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " SET structure =:newStructure,nameCourse =:nameCourse, category =:courseCategory, status =:status WHERE uuid =:uuidCourse")
                    .setParameter("newStructure", newStructure).setParameter("nameCourse", nameCourse)
                    .setParameter("courseCategory", courseCategory).setParameter("status", status)
                    .setParameter("uuidCourse", uuidCourse).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return false;
        }
    }

}