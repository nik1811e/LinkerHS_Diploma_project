package user.following;

import entity.FollowingEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@WebServlet(urlPatterns = "/follow")
public class FollowingHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(FollowingHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (!MethodUtil.isExistFollowing(req.getParameter("uuidFollower"), req.getParameter("uuidFollowing"))) {
                if (addFollowing(session,transaction, req.getParameter("uuidFollower"), req.getParameter("uuidFollowing"))) {
                    resp.sendRedirect("/pages/profile.jsp?uuidAuth=" + req.getParameter("uuidFollowing"));
                }
            } else {
                if (deleteFollowing(session, req.getParameter("uuidFollower"), req.getParameter("uuidFollowing"))) {
                    resp.sendRedirect("/pages/profile.jsp?uuidAuth=" + req.getParameter("uuidFollowing"));
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    private boolean addFollowing(Session session,Transaction transaction, String uuidFollower, String uuidFollowing) {
        LOGGER.debug(getClass().getName() + " addFollowing");
        try {
            FollowingEntity followingEntity = new FollowingEntity();
            followingEntity.setIdAuth(MethodUtil.getIdAuthByUUID(session, uuidFollower));
            followingEntity.setIdFollowing(MethodUtil.getIdAuthByUUID(session, uuidFollowing));
            session.save(followingEntity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    private boolean deleteFollowing(Session session, String uuidFollower, String uuidFollowing) {
        LOGGER.debug(getClass().getName() + " deleteFollowing");
        try {
            session.createQuery("DELETE FROM " + FinalValueUtil.ENTITY_FOLLOWING + " WHERE idFollowing =:idFollowing AND idAuth =:idFollower")
                    .setParameter("idFollowing", new MethodUtil().getIdAuthByUUID(session, uuidFollowing))
                    .setParameter("idFollower", MethodUtil.getIdAuthByUUID(session, uuidFollower)).executeUpdate();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }
}
