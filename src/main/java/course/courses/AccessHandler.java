package course.courses;

import entity.AccessEntity;
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

@WebServlet(urlPatterns = "/addaccess")
public class AccessHandler extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(AccessHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction  transaction = session.beginTransaction();
        try {
            String uuidAuth = req.getParameter("uuidAuthAc");
            String uuidCourse = req.getParameter("uuidCourseAc");
            String uuidOwner = req.getParameter("uuidAuthOwnerdAc");
            if (addAccess(session,transaction,uuidAuth,uuidCourse)) {
                MethodUtil.updateRequest(session, transaction, new RequestRemoving().prepareRemoveRequest(session,uuidCourse,uuidAuth, uuidOwner), uuidOwner);
                resp.sendRedirect("/pages/requests.jsp?uuidAuth="+uuidOwner);
                new MailUtil().sendMailCourseRequest(MethodUtil.getAuthInfByUuid(uuidAuth).get(0).getEmail(),uuidAuth,uuidOwner,uuidCourse);
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen())
                session.close();
        }

    }

    private boolean addAccess(Session session,Transaction transaction, String uuidAuthReq, String uuidCourseReq) {
        logger.debug(getClass().getName() + " addAccess");
        try {
            AccessEntity accessEntity = new AccessEntity();
            accessEntity.setIdAuth(MethodUtil.getIdAuthByUUID(session, uuidAuthReq));
            accessEntity.setIdCourse(MethodUtil.getIdCourseByUUID(session, uuidCourseReq));
            session.save(accessEntity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            return false;
        }
    }

}
