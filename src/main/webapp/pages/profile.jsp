<%--suppress ALL --%>
<%@ page import="entity.AuthInfEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MailUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Profile</title>

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" media="all" href="/resources/css/style.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- Adding "maximum-scale=1" fixes the Mobile Safari auto-zoom bug: http://filamentgroup.com/examples/iosScaleBug/ -->
    <!-- JS -->
    <script src="/resources/js/jquery-1.6.4.min.js"></script>
    <script src="/resources/js/css3-mediaqueries.js"></script>
    <script src="/resources/js/custom.js"></script>
    <script src="/resources/js/tabs.js"></script>
    <!-- Tweet -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css" media="screen">
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
    <link rel="stylesheet" href="js/prettyPhoto/css/prettyPhoto.css" media="screen"/>
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
    <!-- SKIN -->
    <link rel="stylesheet" media="all" href="/resources/css/skin.css"/>
    <link rel="stylesheet" media="all" href="/resources/css/modal.css"/>
    <link href="/resources/css/paper.css" rel="stylesheet"/>

    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        String myUuid = cookieUtil.getUserUuidFromToken();

        String urlRedirect = "/pages/signin.jsp";
        String fbutton_text = "Подписаться";
        if (MethodUtil.isExistFollowing(myUuid, request.getParameter("uuidAuth"))) {
            fbutton_text = "Отписаться";
        }
        List<AuthInfEntity> authInfEntityList = null;
        if (cookieUtil.isFindCookie()) {
            try {
                authInfEntityList = MethodUtil.getAuthInfByUuid(request.getParameter("uuidAuth"));
            } catch (Exception ex) {
                new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        } else {
            response.sendRedirect(urlRedirect);
        }
        assert authInfEntityList != null;
    %>

</head>

<body>

<header>
    <div class="wrapper clearfix">
        <div id="logo">
            <a href="/pages/index.jsp"><img src="/resources/img/l.png" alt="Helper Service"></a>
        </div>

        <!-- nav -->
        <ul id="nav" class="sf-menu sf-js-enabled sf-shadow">
            <li><a href="#" style="text-decoration: none">ГЛАВНАЯ</a></li>
            <li><a href="#" style="text-decoration: none">КАТАЛОГ РЕСУРСОВ</a>
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
            <li  class="current-menu-item"><a href="/pages/profile.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                   style="text-decoration: none">ПРОФИЛЬ</a></li>
            <%}%>
        </ul>
    </div>
</header>

<!-- MAIN -->
<div id="main">
    <div class="wrapper clearfix" style="height:95vh; width: 60%">

        <!-- masthead -->
        <div class="masthead clearfix">
            <h1>Ваши запросы</h1><span class="subheading">In Photography, Recent work</span>
        </div>
        <!-- posts list -->
        <div class="row" style="margin: 0" >
            <div class="home-add clearfix" style="padding-top: 90px">
                <div class="post-heading">
                    <div class="col-lg-4 col-md-5">
                        <div class="card card-user">
                            <div class="image">
                                <img src="/resources/img/background.jpg" alt="..."/>
                            </div>
                            <div class="content">
                                <div class="author">
                                    <img class="avatar border-white" src="/resources/img/avatar.png"
                                         alt="..."/>
                                    <h4 class="title"><%=authInfEntityList.get(0).getFName()%> <%=authInfEntityList.get(0).getLName()%>
                                        <br/>
                                        <a href="#">
                                            <small><%=authInfEntityList.get(0).getEmail()%>
                                            </small>
                                        </a>
                                    </h4>
                                </div>
                                <br>
                                <%if (!cookieUtil.getUserUuidFromToken().equals(authInfEntityList.get(0).getUuid())) {%>
                                <form method="post" action="/follow">
                                    <input type="hidden" name="uuidFollower"
                                           value="<%=cookieUtil.getUserUuidFromToken()%>">
                                    <input type="hidden" name="uuidFollowing"
                                           value="<%=authInfEntityList.get(0).getUuid()%>">
                                    <input type="submit" class="btn-modal"
                                           value="<%=fbutton_text%>">
                                </form>
                                <%}%>
                                <br>
                            </div>
                            <div class="text-center">
                                <div class="row">
                                    <div class="col-md-3 col-md-offset-1">
                                        <h5>
                                            <small><a
                                                    href="/pages/catalog.jsp?uuidAuth=<%=request.getParameter("uuidAuth")%>"
                                                    style="text-decoration: none; color: #999999; font-size: 11px">Курсы</a></small>
                                        </h5>
                                    </div>
                                    <div class="col-md-4">
                                        <h5>
                                            <small><a
                                                    href="/pages/following.jsp?uuidAuth=<%=request.getParameter("uuidAuth")%>"
                                                    style="text-decoration: none; color: #999999; font-size: 11px">Подписки</a>
                                            </small>
                                        </h5>
                                    </div>
                                    <div class="col-md-3">
                                        <h5>
                                            <small><a
                                                    href="/pages/follower.jsp?uuidAuth=<%=request.getParameter("uuidAuth")%>"
                                                    style="text-decoration: none; color: #999999; font-size: 11px">Подпищики</a>
                                            </small>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8 col-md-7">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Изменить профиль</h4>
                            </div>
                            <div class="content">
                                <form method="post" action="/editprofile">
                                    <input type="hidden" name="uuid" value="<%=cookieUtil.getUserUuidFromToken()%>">
                                    <input type="hidden" name="bday"
                                           value="<%=authInfEntityList.get(0).getBDay()%>">
                                    <input type="hidden" name="statusO"
                                           value="<%=authInfEntityList.get(0).getRole() %>">
                                    <input type="hidden" name="dateReg"
                                           value="<%=authInfEntityList.get(0).getDateReg()%>">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label>Статус</label>
                                                <input type="text" class="form-control border-input" disabled
                                                       placeholder="status" name="status"
                                                       value="<%=authInfEntityList.get(0).getRole()%>">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Логин</label>
                                                <input type="text" class="form-control border-input"
                                                       placeholder="login"
                                                       value="<%=authInfEntityList.get(0).getLogin()%>"
                                                       name="login">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label>Email</label>
                                                <input type="email" class="form-control border-input"
                                                       placeholder="<%=authInfEntityList.get(0).getEmail()%>"
                                                       name="email">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Имя</label>
                                                <input type="text" class="form-control border-input"
                                                       placeholder="First name"
                                                       value="<%=authInfEntityList.get(0).getFName()%>"
                                                       name="fname">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Фамилия</label>
                                                <input type="text" class="form-control border-input"
                                                       placeholder="Last Name"
                                                       value="<%=authInfEntityList.get(0).getLName()%>"
                                                       name="lname">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Пароль</label>
                                                <input type="password" class="form-control border-input"
                                                       placeholder="password" name="password">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Обо мне</label>
                                                <textarea rows="5" class="form-control border-input"
                                                          placeholder="Здесь вы можете написать о себе"
                                                          value="<%=authInfEntityList.get(0).getAbout()%>"
                                                          name="desc"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <%if (authInfEntityList.get(0).getUuid().equals(cookieUtil.getUserUuidFromToken())) {%>
                                        <button type="submit" class="btn-modal">Update Profile
                                        </button>
                                        <%}%>
                                    </div>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="fold"></div>
            </div>
        </div>

    </div>
</div>
<!-- ENDS MAIN -->


<footer>

    <div class="wrapper clearfix">

        <!-- widgets -->
        <ul class="widget-cols clearfix">
            <li class="first-col">

                <div class="widget-block">
                    <h4>RECENT POSTS</h4>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post"/></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post"/></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post"/></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                </div>
            </li>

            <li class="second-col">

                <div class="widget-block">
                    <h4>FREE TEMPLATES &amp; THEMES</h4>
                    <p>Visit <a href="http://templatecreme.com/">Template Creme</a> and browse the selection of
                        well-made FREE Templates and WordPress Themes.</p>
                </div>

            </li>

            <li class="third-col">

                <div class="widget-block">
                    <div id="tweets" class="footer-col tweet">
                        <h4>TWITTER WIDGET</h4>
                    </div>
                </div>

            </li>

            <li class="fourth-col">

                <div class="widget-block">
                    <h4>FREE TEMPLATES &amp; THEMES</h4>
                    <p>Visit <a href="http://templatecreme.com/">Template Creme</a> and browse the selection of
                        well-made FREE Templates and WordPress Themes.</p>
                </div>

            </li>
        </ul>
        <!-- ENDS widgets -->


        <!-- bottom -->
        <div class="footer-bottom">
            <div class="left">Simpler Template by <a href="http://www.luiszuno.com">luiszuno.com</a></div>
            <div class="right">
                <ul id="social-bar">
                    <li><a href="http://www.facebook.com" title="Become a fan" class="poshytip"><img
                            src="img/social/facebook.png" alt="Facebook"/></a></li>
                    <li><a href="http://www.twitter.com" title="Follow my tweets" class="poshytip"><img
                            src="img/social/twitter.png" alt="twitter"/></a></li>
                    <li><a href="http://www.google.com" title="Add to the circle" class="poshytip"><img
                            src="img/social/plus.png" alt="Google plus"/></a></li>
                </ul>
            </div>
        </div>
        <!-- ENDS bottom -->

    </div>
</footer>


</body>
</html>