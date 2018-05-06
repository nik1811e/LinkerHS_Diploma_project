package course.courses;

import com.google.gson.Gson;
import course.pojo.CourseRequestTO;
import course.pojo.RequestTO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import user.following.FollowingHandler;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/courserequest")
public class CourseRequest extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(FollowingHandler.class);

    private Session session;
    private Transaction transaction;
    private Gson gson = new Gson();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            String uuidAuthOwner = req.getParameter("uuidAuthOwner");
            String uuidAuthReq = req.getParameter("uuidAuthReq");
            String uuidCourseReq = req.getParameter("uuidCourseReq");
            boolean result = new MethodUtil().updateRequest(session, transaction, prepareAddRequest(uuidCourseReq, uuidAuthReq, uuidAuthOwner), uuidAuthOwner);
            if (result) {
                resp.sendRedirect("/pages/catalog.jsp?uuidAuth=" + uuidAuthOwner);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    private String prepareAddRequest(String uuidCourse, String uuidAuth, String uuidAuthOwner) {
        CourseRequestTO courseRequestTO = gson.fromJson(MethodUtil.getJsonRequest(session, uuidAuthOwner), CourseRequestTO.class);
        List<RequestTO> requestsList = new ArrayList<>(courseRequestTO.getRequest());
        RequestTO request = new RequestTO();

        request.setUuidAuth(uuidAuth);
        request.setUuidCourse(uuidCourse);

        requestsList.add(request);
        courseRequestTO.setRequest(requestsList);

        return gson.toJson(courseRequestTO);
    }


}
