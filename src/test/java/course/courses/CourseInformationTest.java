package course.courses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class CourseInformationTest {
/*    @Mock
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
    public void init() {
        mockHttpServletRequest.setCharacterEncoding("UTF-8");
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();

    }*/

    @Test
    public void testUserCourses() {
        new CourseInformation().getUserCourses("e61a37d7-c118-4ae1-abb7-2d61df870c9e");
    }

    @Test
    public void testCourseInf() {
        new CourseInformation().getCourseInformation("b00d3c02-7c27-42e0-b4a1-b036de1bcf0c");
    }

    @Test
    public void testCourseInfJson() {
        new CourseInformation().getCourseInformationFromJson("b00d3c02-7c27-42e0-b4a1-b036de1bcf0c");
    }
}
