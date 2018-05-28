<%--suppress JspAbsolutePathInspection --%>
<%@ page import="entity.AuthInfEntity" %>
<%@ page import="entity.CategoryEntity" %>
<%@ page import="entity.CourseEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.Random" %>
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
    <%

        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";
        List<CourseEntity> coursesList;
        List<AuthInfEntity> usersList;
        List<CategoryEntity> cateries;
        if (cookieUtil.isFindCookie()) {
            coursesList = MethodUtil.getAllCourses();
            usersList = MethodUtil.getAllUsers();
            cateries = MethodUtil.getCourseCategory();
        } else {
            response.sendRedirect(urlRedirect);
            return;
        }
        assert coursesList != null;
        assert usersList != null;
        assert cateries != null;
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
                <li class="active">
                    <a href="dashboard.html">
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
                <li>
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
                    <a class="navbar-brand" href="#">Dashboard</a>
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
                    <div class="col-lg-3 col-sm-6">
                        <div class="card">
                            <div class="content">
                                <div class="row">
                                    <div class="col-xs-5">
                                        <div class="icon-big icon-warning text-center">
                                            <i class="ti-server"></i>
                                        </div>
                                    </div>
                                    <div class="col-xs-7">
                                        <div class="numbers">
                                            <p>Courses</p>
                                            <%=coursesList.size()%>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer">
                                    <hr/>
                                    <div class="stats">
                                        <a href="tables.jsp" style="text-decoration: none"> <i class="ti-link"></i>
                                            Updated now </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6">
                        <div class="card">
                            <div class="content">
                                <div class="row">
                                    <div class="col-xs-5">
                                        <div class="icon-big icon-success text-center">
                                            <i class="ti-wallet"></i>
                                        </div>
                                    </div>
                                    <div class="col-xs-7">
                                        <div class="numbers">
                                            <p>Courses per day</p>
                                            <%=MethodUtil.coursePerDay().get(0)%>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer">
                                    <hr/>
                                    <div class="stats">
                                        <i class="ti-calendar"></i> Last day
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6">
                        <div class="card">
                            <div class="content">
                                <div class="row">
                                    <div class="col-xs-5">
                                        <div class="icon-big icon-danger text-center">
                                            <i class="ti-pulse"></i>
                                        </div>
                                    </div>
                                    <div class="col-xs-7">
                                        <div class="numbers">
                                            <p>Users per day</p>
                                            <%=MethodUtil.usersPerDay().get(0)%>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer">
                                    <hr/>
                                    <div class="stats">
                                        <i class="ti-timer"></i> In the last hour
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6">
                        <div class="card">
                            <div class="content">
                                <div class="row">
                                    <div class="col-xs-5">
                                        <div class="icon-big icon-info text-center">
                                            <i class="ti-user"></i>
                                        </div>
                                    </div>
                                    <div class="col-xs-7">
                                        <div class="numbers">
                                            <p>Users</p>
                                            <%=usersList.size()%>
                                        </div>
                                    </div>
                                </div>
                                <div class="footer">
                                    <hr/>
                                    <div class="stats">
                                        <i class="ti-reload"></i> Updated now
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Users Behavior</h4>
                                <p class="category">24 Hours performance</p>
                            </div>
                            <div class="content">
                                <center>
                                    <canvas id="bar-chart-horizontal" width="800" height="450"
                                            style="max-height: 550px;max-width: 980px"></canvas>
                                </center>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Email Statistics</h4>
                                <p class="category">Last Campaign Performance</p>
                            </div>
                            <div class="content">
                                <center>
                                    <canvas id="myChart" width="300px" height="300px"
                                            style="max-width: 455px; max-height: 455px"></canvas>
                                </center>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card ">
                            <div class="header">
                                <h4 class="title">2015 Sales</h4>
                                <p class="category">All products including Taxes</p>
                            </div>
                            <div class="content">
                                <div id="chartActivity" class="ct-chart"></div>
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
<script src="/resources/js/admin/chart.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: [
                    <% for (int j=0;j<=cateries.size()-1;j++){
                        if(j<cateries.size()-1){
                    %>
                    "<%=cateries.get(j).getName()%>",
                    <%}else{%>
                    "<%=cateries.get(j).getName()%>"
                    <%}}%>
                ],
                datasets: [{
                    label: '# of Votes',
                    data: [
                        <% for (int j=0;j<=cateries.size()-1;j++){
                            if(j<cateries.size()-1){
                        %>
                        "<%=Objects.requireNonNull(MethodUtil.courseCategoryCharts(cateries.get(j).getId())).get(0)%> ",
                        <%}else{%>
                        "<%=Objects.requireNonNull(MethodUtil.courseCategoryCharts(cateries.get(j).getId())).get(0)%>"
                        <%}}%>
                    ],
                    backgroundColor: [
                        <% for (int j=0;j<=cateries.size();j++){
                        if(j < cateries.size()) {
                        %>
                        'rgba(<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>)',
                        <%}else{%>
                        'rgba(<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>)'
                        <%}}%>
                    ],
                    borderColor: [
                        <% for (int j=0;j<=cateries.size();j++){
                        if(j < cateries.size()) {
                        %>
                        'rgba(<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>)',
                        <%}else{%>
                        'rgba(<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>,<%= new Random().nextInt((255-1)+1)+1%>)'
                        <%}}%>
                    ],
                    borderWidth:
                        1
                }
                ]
            },

            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });

        new Chart(document.getElementById("bar-chart-horizontal"), {
            type: 'horizontalBar',
            data: {
                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                        data: [2478, 5267, 734, 784, 433]
                    }
                ]
            },
            options: {
                legend: {display: false},
                title: {
                    display: true,
                    text: 'Predicted world population (millions) in 2050'
                }
            }
        });


        $.notify({
            icon: 'ti-gift',
            message: "Welcome to <b>Admin Page</b>."

        }, {
            type: 'success',
            timer: 3000
        });

    });
</script>

</html>
