package course.courses;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.util.Arrays;

@SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
@WebServlet(urlPatterns = "/addaccess")
public class AccessHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(AccessHandler.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            resp.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            String uuidAuth = req.getParameter("uuidAuthAc");
            String uuidCourse = req.getParameter("uuidCourseAc");
            String uuidOwner = req.getParameter("uuidAuthOwnerdAc");
            if (addAccess(session, transaction, uuidAuth, uuidCourse)) {
                MethodUtil.updateRequest( new RequestRemoving().prepareRemoveRequest(session, uuidCourse, uuidAuth, uuidOwner), uuidOwner);
                 /*URL url = new URL(req.getRequestURL().toString());
                String body = "<br/> " + new SimpleDateFormat(FinalValueUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime()) + "<br/>" +
                        "<p>Здравствуйте,</p>" +
                        "<p>Вам открыт доступ к курсу</p>" +
                        "<p>" +
                        "<b>Создатель курса: </b>" + Objects.requireNonNull(MethodUtil.getAuthInfByUuid(uuidOwner)).getLogin() + "" +
                        "<br/><b>Курс: </b>" + new CourseInformation().getCourseInformation(uuidCourse).getNameCourse() + "" +
                        "<p>Курс по ссылке: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/course?uuidAuth=" + uuidAuth + "&&uuidCourse" + uuidCourse + "\">" +
                         "[ Перейти ] </a></p>";

                String subject = "Доступ открыт";
                new MailUtil().sendMail(req.getParameter("email"), body, subject);*/
                resp.sendRedirect("/pages/requests.jsp?uuidAuth=" + uuidOwner);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }

    private boolean addAccess(Session session, Transaction transaction, String uuidAuthReq, String uuidCourseReq) {
        LOGGER.debug(getClass().getName() + " addAccess");
        try {
            AccessEntity accessEntity = new AccessEntity();
            accessEntity.setIdAuth(MethodUtil.getIdAuthByUUID(session, uuidAuthReq));
            accessEntity.setIdCourse(MethodUtil.getIdCourseByUUID(session, uuidCourseReq));
            session.save(accessEntity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }

}
