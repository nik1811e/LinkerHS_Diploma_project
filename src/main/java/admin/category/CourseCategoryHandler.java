package admin.category;

import entity.CategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;

@WebServlet(urlPatterns = "/ccategoryhandler")
public class CourseCategoryHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CourseCategoryHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            String name = String.valueOf(req.getParameter("name")).trim();
            Transaction transaction = session.beginTransaction();
            if (addCourseCategory(session, transaction, String.valueOf(name))) {
                resp.sendRedirect("/");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }

    private boolean addCourseCategory(Session session, Transaction transaction, String name) {
        LOGGER.debug(getClass().getName() + " addCategory");
        try {
            if (MethodUtil.isUniqueCourseCategory(session, name)) {
                CategoryEntity category = new CategoryEntity();
                category.setName(name);
                session.save(category);
                transaction.commit();
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
        return false;
    }
}