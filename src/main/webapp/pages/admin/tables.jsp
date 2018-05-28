<%@ page import="entity.AuthInfEntity" %>
<%@ page import="entity.CourseEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="util.ReCaptchaUtil" %>
<%@ page import="java.util.List" %>
<!doctype html>
<html lang="en">
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
                <li>
                    <a href="/pages/admin/profile.jsp?uuidAuth=#">
                        <i class="ti-user"></i>
                        <p>User Profile</p>
                    </a>
                </li>
                <li class="active">
                    <a href="tables.jsp">
                        <i class="ti-view-list-alt"></i>
                        <p>Table List</p>
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
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="ti-panel"></i>
                                <p>Stats</p>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="ti-bell"></i>
                                <p class="notification">5</p>
                                <p>Notifications</p>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Notification 1</a></li>
                                <li><a href="#">Notification 2</a></li>
                                <li><a href="#">Notification 3</a></li>
                                <li><a href="#">Notification 4</a></li>
                                <li><a href="#">Another notification</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <i class="ti-settings"></i>
                                <p>Settings</p>
                            </a>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Users list</h4>
                                <p class="category" style="display: inline-block">A full list of users is displayed
                                    here.</p>
                            </div>
                            <div class="content table-responsive table-full-width">
                                <div style="display: inline-block;float: right">
                                    <button type="button" class="btn ti-plus" id="addUserBtn"
                                            style="color: #67b168"></button>
                                </div>
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

<div id="addUser" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close ti-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title" style="color: #3A3A3A">Add user</h4>
            </div>
            <div class="modal-body">
                <form action="/registration" role="form" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="fname"
                               maxlength="50" placeholder="Firstname" required>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="lname"
                               maxlength="50" placeholder="Lastname" required>
                    </div>
                    <div class="form-group">
                        <input type="date" class="form-control" name="bday" placeholder="Birth day" required>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="login"
                               maxlength="50" placeholder="Login" required>
                    </div>
                    <div class="form-group">
                        <input type="email" class="form-control" name="email"
                               maxlength="70" placeholder="E-mail" required>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password" required
                               maxlength="50" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="confirm_password"
                               maxlength="50" placeholder="Confirm password" required>
                    </div>
                    <div class="g-recaptcha" data-sitekey="<%=ReCaptchaUtil.PUBLIC%>"></div>
                    <input type="submit" class="btn-modal" value="ADD">
                </form>
            </div>
        </div>
    </div>
</div>

<%--
<div id="editCourse" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Редиктирование</h4>
            </div>
            <div class="modal-body">
                <form action="/editcourse" role="form" method="post">
                    <input type="hidden" name="uuidAuth" value="<%=request.getParameter("uuidAuth")%>">
                    <input type="hidden" name="uuidCourseEdit" value="<%=request.getParameter("uuidCourse")%>">
                    <div class="form-group">
                        <input type="text" class="form-control" name="nameCourseEdit" required
                               maxlength="50" placeholder="Название"
                               value="<%=courseInformationList.get(0).getNameCourse()%>">
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="status" name="statusCourseEdit">
                            <%if (courseInformationList.get(0).getStatus().equals("Открыт")) {%>
                            <option selected>Открыт</option>
                            <option>Закрыт</option>
                            <%} else {%>
                            <option>Открыт</option>
                            <option selected>Закрыт</option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="id_category" name="courseCategoryEdit">
                            <%
                                assert categoryEntityList != null;
                                for (int i = 0; i < categoryEntityList.size(); i++) {
                                    int id = categoryEntityList.get(i).getId();
                                    String name = categoryEntityList.get(i).getName();
                                    assert currentCategory != null;
                                    if (!currentCategory.equals(name)) {
                            %>
                            <option value="<%=id%>"><%=name%>
                            </option>
                            <%} else {%>
                            <option selected value="<%=id%>"><%=name%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                                    <textarea class="form-control" name="courseDescEdit" id="desc"
                                              placeholder="Описание курса" maxlength="6000" rows="7">
                                        <%=courseInformationFromJson.getDescriptionCourse().trim()%>
                                    </textarea>
                    </div>
                    <button type="submit" class="btn-modal" id="btnContactUs">
                        Внести изменения
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
--%>

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
