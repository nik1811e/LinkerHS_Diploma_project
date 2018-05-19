package course.courses;

import com.google.gson.Gson;
import course.pojo.CourseRequestTO;
import course.pojo.RequestTO;
import course.resources.ResourceRemoving;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
@WebServlet(urlPatterns = "/removerequest")
public class RequestRemoving extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ResourceRemoving.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String uuidAuthOwner = req.getParameter("uuidAuthOwner");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if (MethodUtil.updateRequest(session, transaction, prepareRemoveRequest(session, req.getParameter("uuidCourseReq"), req.getParameter("uuidAuthReq"), uuidAuthOwner), uuidAuthOwner)) {
                resp.sendRedirect("/pages/requests.jsp?uuidAuth=" + uuidAuthOwner);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());

        }
    }

    String prepareRemoveRequest(Session session, String uuidCourse, String uuidAuth, String uuidOwner) {
        CourseRequestTO courseRequestTO = gson.fromJson(MethodUtil.getJsonRequest(session, uuidOwner), CourseRequestTO.class);
        List<RequestTO> requestTOList = new ArrayList<>(courseRequestTO.getRequest());

        for (int i = 0; i < requestTOList.size(); i++) {
            RequestTO req = requestTOList.get(i);
            if ((req.getUuidCourse().equals(uuidCourse) && req.getUuidAuth().equals(uuidAuth))) {
                requestTOList.remove(i);
            }
        }
        courseRequestTO.setRequest(requestTOList);
        return gson.toJson(courseRequestTO);
    }


}
