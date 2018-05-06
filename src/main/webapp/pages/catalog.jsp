<%@ page import="course.courses.CourseInformation" %>
<%@ page import="entity.CategoryEntity" %>
<%@ page import="entity.CourseEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Catalog</title>
    <link rel="stylesheet" media="all" href="/resources/css/style.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- Adding "maximum-scale=1" fixes the Mobile Safari auto-zoom bug: http://filamentgroup.com/examples/iosScaleBug/ -->
    <!-- JS -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css" media="screen">
    <script src="/resources/js/jquery-1.6.4.min.js"></script>
    <script src="/resources/js/css3-mediaqueries.js"></script>
    <script src="/resources/js/custom.js"></script>
    <script src="/resources/js/tabs.js"></script>
    <!-- Tweet -->
    <link rel="stylesheet" href="/resources/css/jquery.tweet.css" media="all"/>
    <script src="/resources/js/tweet/jquery.tweet.js"></script>
    <!-- ENDS Tweet -->
    <!-- superfish -->
    <link rel="stylesheet" media="screen" href="/resources/css/superfish.css"/>
    <script src="/resources/js/superfish-1.4.8/js/hoverIntent.js"></script>
    <script src="/resources/js/superfish-1.4.8/js/superfish.js"></script>
    <script src="/resources/js/superfish-1.4.8/js/supersubs.js"></script>
    <!-- ENDS superfish -->
    <!-- prettyPhoto -->
    <script src="/resources/js/prettyPhoto/js/jquery.prettyPhoto.js"></script>
    <link rel="stylesheet" href="/resources/js/prettyPhoto/css/prettyPhoto.css" media="screen"/>
    <!-- ENDS prettyPhoto -->
    <!-- poshytip -->
    <link rel="stylesheet" href="/resources/js/poshytip-1.1/src/tip-twitter/tip-twitter.css"/>
    <link rel="stylesheet" href="/resources/js/poshytip-1.1/src/tip-yellowsimple/tip-yellowsimple.css"/>
    <script src="/resources/js/poshytip-1.1/src/jquery.poshytip.min.js"></script>
    <!-- ENDS poshytip -->
    <!-- GOOGLE FONTS -->
    <link href='http://fonts.googleapis.com/css?family=Arvo:400,700' rel='stylesheet' type='text/css'>
    <!-- Flex Slider -->
    <link rel="stylesheet" href="/resources/css/flexslider.css">
    <script src="/resources/js/jquery.flexslider-min.js"></script>
    <!-- ENDS Flex Slider -->
    <!-- Masonry -->
    <script src="/resources/js/masonry.min.js"></script>
    <script src="/resources/js/imagesloaded.js"></script>
    <!-- ENDS Masonry -->
    <!-- Less framework -->
    <link rel="stylesheet" media="all" href="/resources/css/lessframework.css"/>
    <!-- modernizr -->
    <script src="/resources/js/modernizr.js"></script>
    <script src="/resources/js/modal_win.js"></script>
    <!-- SKIN -->
    <link rel="stylesheet" media="all" href="/resources/css/skin.css"/>
    <link rel="stylesheet" media="all" href="/resources/css/modal.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

    <%

        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";
        List<CourseEntity> userCourseList = null;
        List<CategoryEntity> categoryEntityList = null;
        if (cookieUtil.isFindCookie()) {
            userCourseList = new CourseInformation().getUserCourse(request.getParameter("uuidAuth"));
            categoryEntityList = MethodUtil.getCourseCategory();
        } else {
            response.sendRedirect(urlRedirect);
            return;
        }
        assert userCourseList != null;
    %>
</head>

<body>
<header>
    <div class="wrapper clearfix">

        <div id="logo">
            <a href="index.jsp"><img src="/resources/img/l.png" alt="Helper Service"></a>
        </div>

        <!-- nav -->
        <ul id="nav" class="sf-menu">
            <li><a href="/pages/index.jsp" style="text-decoration: none">ГЛАВНАЯ</a></li>
            <li class="current-menu-item"><a href="#" style="text-decoration: none">КАТАЛОГ РЕСУРСОВ</a>
                <ul>
                    <li><a href="/pages/catalog.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                           style="text-decoration: none">Мои
                        ресурсы</a></li>
                    <li><a href="" style="text-decoration: none">Избранное</a></li>
                </ul>
            </li>
            <li><a href="#" style="text-decoration: none">ПОЛЬЗОВАТЕЛИ</a>
                <ul>
                    <li><a href="/pages/users.jsp" style="text-decoration: none">Список пользователей</a></li>
                    <li><a href="/pages/following.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>" style="text-decoration: none">Подписки</a></li>
                    <li><a href="/pages/follower.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>" style="text-decoration: none">Подпищики</a></li>
                </ul>
            </li>
            <%if (!cookieUtil.isFindCookie()) {%>
            <li><a href="/pages/signin.jsp" style="text-decoration: none">АВТОРИЗАЦИЯ</a></li>
            <%} else {%>
            <li><a href="/pages/profile.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                   style="text-decoration: none">ПРОФИЛЬ</a></li>
            <%}%>
        </ul>
    </div>
</header>

<!-- MAIN -->
<div id="main">
    <div class="wrapper clearfix">
        <!-- masthead -->
        <div class="masthead clearfix">
            <h1>КУРСЫ</h1>
            <span class="subheading">
                <button id="btn2" class="btn-modal">Добавить курс</button></span>
        </div>
        <div class='mh-div'></div>
        <!-- ENDS masthead -->

        <!-- posts list -->
        <div id="posts-list" class="clearfix" style="min-height: 600px">
            <%
                if (!userCourseList.isEmpty()) {
                    for (int i = 0; i < userCourseList.size(); i++) {
            %>
            <!-- Courses -->
            <article class="format-standard">
                <div class="entry-date">
                    <div class="number"><%%>
                    </div>
                    <div class="month"><%%></div>
                    <div class="year"><%%></div>
                    <em></em></div>
                <div class="post-heading">
                    <h3>
                        <a href="course.jsp?uuidAuth=<%=request.getParameter("uuidAuth")%>&&uuidCourse=<%=userCourseList.get(i).getUuid()%>"><%=userCourseList.get(i).getNameCourse()%>
                        </a></h3>
                    <div class="meta">
                        <span class="user"><%=userCourseList.get(i).getStatus().toUpperCase()%> | </span>
                        <span class="comments"><%=MethodUtil.getNameCourseCategoryByid(userCourseList.get(i).getCategory()).toUpperCase()%></span>
                    </div>
                </div>
                <div class="feature-image">
                    <a href="" data-rel="prettyPhoto">
                        <img src="/resources/img/slides/01.jpg" alt="<%=userCourseList.get(i).getNameCourse()%>"/>
                    </a>
                </div>
                <div class="excerpt" style="width: 480px">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam
                    malesuada neque non mi maximus
                    mattis. Etiam nulla turpis, placerat sed turpis a, malesuada interdum nibh. Vivamus tristique,
                    diam vitae tincidunt scelerisque, est ligula dictum mauris, in suscipit orci nunc ut massa. Cras
                    sagittis nisl blandit leo facilisis accumsan. Maecenas egestas, ante ac finibus mollis, velit
                    nulla faucibus odio, quis egestas massa dolor in arcu. Lorem ipsum dolor sit amet, consectetur
                    adipiscing elit. Cras tincidunt vel dui et pharetra. Maecenas odio risus, ultrices sollicitudin
                    tempor sed, pulvinar id mi. Suspendisse at magna sit amet velit lobortis tempor. Suspendisse
                    potenti.
                </div>
            </article>
            <%
                }
            %>
            <div class="page-navigation clearfix">
                <div class="nav-next"><a href="#">&#8592; Назад</a></div>
                <div class="nav-previous"><a href="#">Вперед &#8594;</a></div>
            </div>
            <%
            } else {
            %>
            <h2 class="text-center thin">Пусто</h2>
            <%}%>

        </div>
        <!-- ENDS posts list -->

        <!-- sidebar -->
        <aside id="sidebar">
            <ul>
                <li class="block">
                    <h4>Text Widget</h4>
                    Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.
                    Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit
                    amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit
                    amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae,
                    ornare sit amet, wisi.
                </li>
            </ul>
            <em id="corner"></em>
        </aside>
        <!-- ENDS sidebar -->

        <!-- Fold image -->
        <div id="fold"></div>
    </div>
</div>
<!-- ENDS MAIN -->

<footer>
    <div class="wrapper clearfix">
        <!-- bottom -->
        <div class="footer-bottom">
            <div class="left">Developed by <a href="https://vk.com/xxxnikgtxxx">@Nikita1811E</a></div>
            <div class="right">
                <ul id="social-bar">
                </ul>
            </div>
        </div>
        <!-- ENDS bottom -->
    </div>
</footer>
<div id="myModal2" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Добавление курса</h4>
            </div>
            <div class="modal-body">
                <form action="/coursehandler" role="form" method="post">
                    <input type="hidden" name="uuidAuth" value="<%=request.getParameter("uuidAuth")%>">
                    <div class="form-group">
                        <input type="text" class="form-control" id="name" name="name_course" required
                               maxlength="50" placeholder="Название">
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="status" name="status">
                            <option value="" disabled>Выберите тип доступа</option>
                            <option>Открыт</option>
                            <option>Закрыт</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="id_category" name="id_category">
                            <option value="" disabled selected>Выберите категорию</option>
                            <%
                                assert categoryEntityList != null;
                                for (int i = 0; i < categoryEntityList.size(); i++) {
                                    int id = categoryEntityList.get(i).getId();
                                    String name = categoryEntityList.get(i).getName();

                            %>
                            <option value="<%=id%>"><%=name%>
                            </option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                                    <textarea class="form-control" type="textarea" name="desc" id="desc"
                                              placeholder="Описание курса" maxlength="6000" rows="7"></textarea>
                    </div>
                    <button type="submit" class="btn btn-lg btn-modal btn-block" id="btnContactUs">
                        Добавить
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $("#btn2").click(function () {
            $("#myModal2").modal('show');
        });
    });
</script>
</body>

</html>