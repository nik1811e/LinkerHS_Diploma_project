package admin.category;

import entity.ResourceCategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;

public class ResourceCategoryHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ResourceCategoryHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            String name = String.valueOf(req.getParameter("name_category_link")).trim();
            if (addCategoryLink(session, transaction, String.valueOf(name))) {
                resp.sendRedirect("/");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }

    private boolean isUniqueCategoryLink(Session session, String name) {
        Query query = session.createQuery("SELECT c.id FROM " +
                FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " c WHERE c.name = :name");
        query.setParameter("name", name);
        return query.list().isEmpty();
    }

    private boolean addCategoryLink(Session session, Transaction transaction, String name) {
        LOGGER.debug(getClass().getName() + " addCategory");
        try {
            if (isUniqueCategoryLink(session, name)) {
                ResourceCategoryEntity category = new ResourceCategoryEntity();
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
