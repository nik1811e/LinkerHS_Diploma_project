package admin.category;

import entity.ResourceCategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns = "/removecategoryr")
public class ResourceCategoryRemoving extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CourseCategoryHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (removeCategoryResource(session, transaction, session.load(ResourceCategoryEntity.class, Integer.parseInt(req.getParameter("idCategory"))))) {
                resp.sendRedirect("/pages/admin/dashboard.jsp");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(ex.getLocalizedMessage());
            LOGGER.info(ex.getLocalizedMessage());
        }
    }

    private boolean removeCategoryResource(Session session, Transaction transaction, ResourceCategoryEntity idCategory) {
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " WHERE id =:id")
                    .setParameter("id", idCategory).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
