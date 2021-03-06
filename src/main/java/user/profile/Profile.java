package user.profile;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;

@WebServlet(urlPatterns = "/editprofile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 100,
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 100)
public class Profile extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(Profile.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            Transaction transaction = session.beginTransaction();
            if(req.getParameter("type").equals("img")){
                if (MethodUtil.updateAuthInfImg(session, transaction, MethodUtil.saveUploadFile(req).trim(),req.getParameter("uuidAuth"))) {
                    resp.sendRedirect("/pages/profile.jsp?uuidAuth=" + req.getParameter("uuidAuth"));
                }
            }else{
                if (MethodUtil.updateAuthInf (session, transaction,
                        req.getParameter("login"),
                        req.getParameter("email"), req.getParameter("fname"), req.getParameter("lname"),
                        req.getParameter("bday"), req.getParameter("uuidAuth"), req.getParameter("desc"),
                        req.getParameter("dateReg"))){
                    resp.sendRedirect("/pages/profile.jsp?uuidAuth=" + req.getParameter("uuidAuth"));
            }
            }

        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }
}
