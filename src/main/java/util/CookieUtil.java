package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CookieUtil {
    private static final Logger logger = Logger.getLogger(CookieUtil.class);

    private String token = null;
    private boolean isFindCookie = false;

    public CookieUtil(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(FinalValueUtil.COOKIE_AUTH_NAME)) {
                    token = c.getValue();
                    isFindCookie = true;
                    logger.info(getClass().getName() + " constructor, token: " + token);
                }
            }
        } else {
            isFindCookie = false;
        }
    }

    public boolean isAdmin(Cookie[] cookies) throws UnsupportedEncodingException {
        for (Cookie cooky : cookies) {
            if ((cooky.getName().equals(FinalValueUtil.COOKIE_AUTH_NAME))
                    && (getRoleFromToken().equalsIgnoreCase(FinalValueUtil.ROLE_ADMIN)))
            return true;
        }
        return false;
    }

    public String getUserUuidFromToken() throws IOException {
        if (!StringUtils.isBlank(token)) {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(FinalValueUtil.COOKIE_KEY.getBytes("UTF-8"))
                    .parseClaimsJws(token);

            return String.valueOf(jws.getBody().get("uuid"));
        } else {
            return null;
        }
    }

    private String getRoleFromToken() throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(FinalValueUtil.COOKIE_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);

        return String.valueOf(jws.getBody().get("role"));
    }

    public boolean isFindCookie() {
        return isFindCookie;
    }
}