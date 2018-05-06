package user.profile;

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
import java.io.IOException;
import java.io.Serializable;

@WebServlet(urlPatterns = "/editprofile")
public class Profile extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(Profile.class);

    private Session session = null;
    private Transaction transaction = null;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String bday = req.getParameter("bday");
        String uuid = req.getParameter("uuid");
        String desc = req.getParameter("desc");
        String date = req.getParameter("dateReg");
        String status = req.getParameter("statusO");
        try {
            if (updateProfile(login, password, email, fname, lname
                    , bday, uuid, desc, date, status)) {
                new MailUtil().sendMailRegistration(req.getParameter("email"),
                        req.getParameter("login"),
                        req.getParameter("password"), req);
                resp.sendRedirect("/pages/profile.jsp?uuidAuth" + req.getParameter("uuid"));
            } else {

            }
        } catch (Exception ex) {

        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    private boolean updateProfile(String login, String password, String email, String fname, String lname, String bday, String uuid, String desc, String date, String status) {
        try {
            session.createQuery("UPDATE  " + FinalValueUtil.ENTITY_AUTH_INFO + " a SET a.login=:login, a.password=:password,a.email=:email,a.FName =:fname,a.LName =:lname,a.BDay=:bday,a.about=:about,a.role=:status,a.dateReg=:date WHERE a.uuid =:uuid")
                    .setParameter("login", login).setParameter("password", password).setParameter("email", email)
                    .setParameter("fname", fname).setParameter("lname", lname)
                    .setParameter("bday", bday).setParameter("uuid", uuid).setParameter("about", desc)
                    .setParameter("date", date).setParameter("status", status).executeUpdate();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
