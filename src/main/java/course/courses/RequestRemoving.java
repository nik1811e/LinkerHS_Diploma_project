package course.courses;

import com.google.gson.Gson;
import course.pojo.CourseRequestTO;
import course.pojo.RequestTO;
import course.resources.ResourceRemoving;
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

@WebServlet(urlPatterns = "/removerequest")
public class RequestRemoving extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(ResourceRemoving.class);
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String uuidAuth = req.getParameter("uuidAuthReq");
        String uuidCourse = req.getParameter("uuidCourseReq");
        String uuidAuthOwner = req.getParameter("uuidAuthOwner");
        try {
            if(MethodUtil.updateRequest(session, transaction,prepareRemoveRequest(session,uuidCourse, uuidAuth,uuidAuthOwner), uuidAuthOwner));
        resp.sendRedirect("/pages/requests.jsp?uuidAuth="+uuidAuthOwner);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());

        }finally {
            if(session.isOpen())
                session.close();
        }

    }

    public String prepareRemoveRequest(Session session, String uuidCourse,String uuidAuth,String uuidOwner){
        CourseRequestTO courseRequestTO = gson.fromJson(MethodUtil.getJsonRequest(session,uuidOwner),CourseRequestTO.class);
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
