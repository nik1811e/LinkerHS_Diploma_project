<%@ page import="entity.AuthInfEntity" %>
<%@ page import="entity.CourseEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="util.ReCaptchaUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
    <link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title>Paper Dashboard by Creative Tim</title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <meta name="viewport" content="width=device-width"/>


    <!-- Bootstrap core CSS     -->
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- Animation library for notifications   -->
    <link href="/resources/css/admin/animate.min.css" rel="stylesheet"/>

    <!--  Paper Dashboard core CSS    -->
    <link href="/resources/css/admin/paper-dashboard.css" rel="stylesheet"/>

    <!--  Fonts and icons     -->
    <link href="/resources/css/admin/font-awesome.min.css" rel="stylesheet">
    <link href="/resources/css/admin/font-muli.css" rel='stylesheet' type='text/css'>
    <link href="/resources/css/admin/themify-icons.css" rel="stylesheet">
    <link href="/resources/css/admin/demo.css" rel="stylesheet">
    <link href="/resources/css/modal.css" rel="stylesheet">

    <link href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css" rel='stylesheet' type='text/css'>
    <%

        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";
        List<CourseEntity> coursesList;
        List<AuthInfEntity> usersList;
        if (cookieUtil.isFindCookie()) {
            coursesList = MethodUtil.getAllCourses();
            usersList = MethodUtil.getAllUsers();
        } else {
            response.sendRedirect(urlRedirect);
            return;
        }
        assert coursesList != null;
        assert usersList != null;
    %>
</head>
<body>

<div class="wrapper">
    <div class="sidebar" data-background-color="white" data-active-color="danger">

        <div class="sidebar-wrapper">
            <div class="logo">
                <a href="http://www.creative-tim.com" class="simple-text">
                    Creative Tim
                </a>
            </div>

            <ul class="nav">
                <li>
                    <a href="dashboard.jsp">
                        <i class="ti-panel"></i>
                        <p>Dashboard</p>
                    </a>
                </li>

                <li class="active">
                    <a href="tables.jsp">
                        <i class="ti-view-list-alt"></i>
                        <p>Table List</p>
                    </a>
                </li>
                <li>
                    <a href="/pages/index.jsp">
                        <i class="ti-user"></i>
                        <p>Sevice</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div class="main-panel">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar bar1"></span>
                        <span class="icon-bar bar2"></span>
                        <span class="icon-bar bar3"></span>
                    </button>
                    <a class="navbar-brand" href="#">Table List</a>
                </div>
                <div class="collapse navbar-collapse">


                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <form method="post" action="/coursesDoc" >
                        <input type="submit" class="btn-modal" style="background-color: #b1a04d;font-size: 12px;width: 150px;height: 30px;text-align: center; padding: 11px; margin: 10px; display: inline-block; text-decoration: none" value="Cources in Excel">
                    </form>
                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Users list</h4>
                                <p class="category" style="display: inline-block">A full list of users is displayed
                                    here.</p>
                            </div>
                            <div class="content table-responsive table-full-width">
                                <table class="table table-striped" id="datatable">
                                    <thead>
                                    <th>Uuid</th>
                                    <th>FirstName</th>
                                    <th>LastName</th>
                                    <th>Birth day</th>
                                    <th>Date registration</th>
                                    <th>Email</th>
                                    <th>Login</th>
                                    <th>Password</th>
                                    <th>Permission</th>
                                    <th>Action</th>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (AuthInfEntity user : usersList) {
                                            if (!user.getUuid().equals(cookieUtil.getUserUuidFromToken())) {
                                    %>
                                    <tr>
                                        <td><%=user.getUuid()%>
                                        </td>
                                        <td><%=user.getFName()%>
                                        </td>
                                        <td><%=user.getLName()%>
                                        </td>
                                        <td><%=user.getBDay()%>
                                        </td>
                                        <td><%=user.getDateReg()%>
                                        </td>
                                        <td><%=user.getEmail()%>
                                        </td>
                                        <td><%=user.getLogin()%>
                                        </td>
                                        <td><%=user.getPassword()%>
                                        </td>
                                        <td><%=user.getRole()%>
                                        </td>
                                        <td>
                                            <div style="display: inline-block">
                                                <form action="/userremove" method="post" style="display: inline-block">
                                                    <input type="hidden" value="<%=user.getUuid()%>" name="uuidAuth">
                                                    <button type="submit" class="btn ti-close"
                                                            style="color: #d9534f"></button>
                                                </form>
                                            </div>
                                            <div style="display: inline-block">
                                                <form action="/changeperm" method="post" style="display: inline-block">
                                                    <input type="hidden" value="<%=user.getUuid()%>" name="uuidAuth">
                                                    <input type="hidden" value="<%=user.getRole()%>" name="role">
                                                    <button type="submit" class="btn ti-shield"
                                                            style="color: #edcd4e"></button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        }
                                    %>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">

                        <div class="card">
                            <div class="header">
                                <h4 class="title">Cources list</h4>
                                <p class="category" style="display: inline-block">A full list of users is displayed
                                    here.</p>
                            </div>
                            <div class="content table-responsive table-full-width">
                                <table class="table table-hover" id="datatable1">
                                    <thead>
                                    <th>Uuid</th>
                                    <th>Category</th>
                                    <th>Name course</th>
                                    <th>Status</th>
                                    <th>Date create</th>
                                    <th>Action</th>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (CourseEntity course : coursesList) {
                                    %>
                                    <tr>
                                        <td><%=course.getUuid()%></td>
                                        <td><%=MethodUtil.getCourseCategoryByid(course.getCategory()).getName()%></td>
                                        <td><%=course.getNameCourse()%></td>
                                        <td><%=course.getStatus()%></td>
                                        <td><%=course.getDateCreate()%></td>
                                        <td>
                                            <div style="display: inline-block">
                                                <form action="/removecourseadmin" method="post" style="display: inline-block">
                                                    <input type="hidden" value="<%=course.getUuid()%>" name="uuidCourse">
                                                    <button type="submit" class="btn ti-close"
                                                            style="color: #d9a470"></button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <footer class="footer">
            <div class="container-fluid">
                <nav class="pull-left">
                    <ul>

                        <li>
                            <a href="http://www.creative-tim.com">
                                Creative Tim
                            </a>
                        </li>
                        <li>
                            <a href="http://blog.creative-tim.com">
                                Blog
                            </a>
                        </li>
                        <li>
                            <a href="http://www.creative-tim.com/license">
                                Licenses
                            </a>
                        </li>
                    </ul>
                </nav>
                <div class="copyright pull-right">
                    &copy;
                    <script>document.write(new Date().getFullYear())</script>
                    , made with <i class="fa fa-heart heart"></i> by <a href="http://www.creative-tim.com">Creative
                    Tim</a>
                </div>
            </div>
        </footer>


    </div>
</div>

</body>

<!--   Core JS Files   -->

<!--   Core JS Files   -->
<script src="/resources/js/admin/jquery-1.10.2.js" type="text/javascript"></script>
<script src="/resources/js/admin/bootstrap.min.js" type="text/javascript"></script>

<!--  Checkbox, Radio & Switch Plugins -->
<script src="/resources/js/admin/bootstrap-checkbox-radio.js"></script>

<!--  Charts Plugin -->
<script src="/resources/js/admin/chartist.min.js"></script>

<!--  Notifications Plugin    -->
<script src="/resources/js/admin/bootstrap-notify.js"></script>

<!-- Paper Dashboard Core javascript and methods for Demo purpose -->
<script src="/resources/js/admin/paper-dashboard.js"></script>
<script src="/resources/js/admin/demo.js"></script>


<script src="/resources/js/admin/1.js" type="text/javascript"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#datatable').dataTable();
        $('#datatable1').dataTable();
    });
</script>
</html>
