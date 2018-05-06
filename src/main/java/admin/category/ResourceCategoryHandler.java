package admin.category;

import entity.ResourceCategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FinalValueUtil;
import util.MethodUtil;
import util.HibernateUtil;
import util.MailUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;

public class ResourceCategoryHandler extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(ResourceCategoryHandler.class);
    private Session session = null;
    private Transaction transaction = null;
    private String errorMessage;
    private static String successMessage = "category created";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = String.valueOf(req.getParameter("name_category_link")).trim();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            boolean success = addCategoryLink(String.valueOf(name));
          MethodUtil.showMessage(resp, success, errorMessage,successMessage);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    private boolean isUniqueCategoryLink(String name) {
        Query query = session.createQuery("SELECT c.id FROM " +
                FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " c WHERE c.name = :name");
        query.setParameter("name", name);
        return query.list().isEmpty();
    }

    private boolean addCategoryLink(String name) {
        logger.debug(getClass().getName() + " addCategory");
        try {
            if (isUniqueCategoryLink(name)) {
                ResourceCategoryEntity category = new ResourceCategoryEntity();
                category.setName(name);
                session.save(category);
                transaction.commit();
                return true;

            } else {
                errorMessage = "This name category already exist";
                return false;
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            errorMessage = "Category creation failed";
            return false;
        }
    }
}
