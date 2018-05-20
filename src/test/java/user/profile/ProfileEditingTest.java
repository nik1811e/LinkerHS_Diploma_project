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

import java.util.UUID;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class ProfileEditingTest {
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

        when(mockHttpServletRequest.getParameter("login")).thenReturn("qwe");
        when(mockHttpServletRequest.getParameter("password")).thenReturn("qwe");
        when(mockHttpServletRequest.getParameter("email")).thenReturn("qwe@test.com");
        when(mockHttpServletRequest.getParameter("fname")).thenReturn("w");
        when(mockHttpServletRequest.getParameter("lname")).thenReturn("w");
        when(mockHttpServletRequest.getParameter("bday")).thenReturn("2011-11-11");
        when(mockHttpServletRequest.getParameter("desc")).thenReturn("description user "+UUID.randomUUID().toString());
        when(mockHttpServletRequest.getParameter("uuid")).thenReturn("42dd56df-04cb-428d-a37d-8573b68297e5");
        when(mockHttpServletRequest.getParameter("dateReg")).thenReturn("19/05/2018");
    }

    @Test
    public void test() {
        new Profile().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
