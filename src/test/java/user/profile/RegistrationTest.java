package user.profile;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class RegistrationTest {
    private static final String CAPTCHA = "03AJpayVH3X0hWegbZ_6pvtgu24HCgP49lf4MpR321qlbxVxO85rY-HaxEOUF4KoVzotC9TZdF9pJULtNAi9RiuZN5K5StsUdD8-0mVtfOX79rsvGL-eErTuNKHuCmwoJmW11hQyQJLlPCkLjhJe4ujnZDFWJvcyGFNyaFzEknwTPUpU-AgwUQxkNJcQ-3RbSNgDwVkW4ihysFN_NtNxBwUutlP2D0UJnHCjO9EonSrPbUzvavIbaCJ1sBS1W0JLFTX_EVs8DmQV-U5hhW1Amnd0x-xv8ojbFs-0RtLFoVnLyGXwcjNemP567a6bjMu2lAEWeDx5bvpL-Inm1-ls51ilzJiDjBWEIG4hZQWiz6PHi8guqGu8O6AaDRmZvRULAoWP9cDAIdOX2ecfohgn32AFe9nDVyac-a5NZJiY08ux4Yyz-lxA2DaOomQ7Hms2EhCadF2kY600OlAXcDQ9BfhYONgLegs0SwQFYTKylsP_boh7W_ULkkmUg";

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Configuration configuration;

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init(){
        initMocks(this);
        mockHttpServletRequest.setCharacterEncoding("UTF-8");
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();

        when(mockHttpServletRequest.getParameter("g-recaptcha-response")).thenReturn(CAPTCHA);
        when(mockHttpServletRequest.getParameter("login")).thenReturn("qweTest2");
        when(mockHttpServletRequest.getParameter("password")).thenReturn("qweTest2");
        when(mockHttpServletRequest.getParameter("email")).thenReturn("qweTest2@qweTest2.com");
        when(mockHttpServletRequest.getParameter("fname")).thenReturn("qweTest2");
        when(mockHttpServletRequest.getParameter("lname")).thenReturn("qweTest2");
        when(mockHttpServletRequest.getParameter("bday")).thenReturn("2000-02-02");
 }

    @Test
    public void test() {
        new Registration().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }

}

