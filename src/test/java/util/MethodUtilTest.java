package util;

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
public class MethodUtilTest {
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

    @SuppressWarnings("Duplicates")
    @Before
    public void init() {
        initMocks(this);
        mockHttpServletRequest.setCharacterEncoding("UTF-8");
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();

    }

    @Test
    public void testCourseCategory() {
        MethodUtil.getCourseCategory();
    }

    @Test
    public void testResourceCategory() {
        MethodUtil.getResourceCategory();
    }

    @Test
    public void testAccessCheck() {
        MethodUtil.checkAccess("Закрыт", MethodUtil.getAuthInfByUuid("e61a37d7-c118-4ae1-abb7-2d61df870c9e"), "42dd56df-04cb-428d-a37d-8573b68297e5", "b00d3c02-7c27-42e0-b4a1-b036de1bcf0c");
    }

    @Test
    public void testFollowings() {
        MethodUtil.getUserFollowings("42dd56df-04cb-428d-a37d-8573b68297e5");
    }

    @Test
    public void testFollowers() {
        MethodUtil.getUserFollowers("42dd56df-04cb-428d-a37d-8573b68297e5");
    }

    @Test
    public void testUuidAuthById() {
        MethodUtil.getUuudAuthById(15);
    }

    @Test
    public void testgetAllUsers() {
        MethodUtil.getAllUsers();
    }

    @Test
    public void testGetAllCourses() {
        MethodUtil.getAllCourses();
    }

    @Test
    public void testGetResourceCategory() {
        MethodUtil.getResourceCategoryByid(1);
    }

    @Test
    public void testGetCourseCategory() {
        MethodUtil.getCourseCategoryByid(1);
    }
}
