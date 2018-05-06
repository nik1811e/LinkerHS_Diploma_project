<%--suppress ALL --%>
<%@ page import="util.CookieUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Main</title>

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

    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        String uuidAuth = "unknown";
        if (cookieUtil.getUserUuidFromToken() != null) {
            uuidAuth = cookieUtil.getUserUuidFromToken();
        }
    %>
</head>
<body>
<header>
    <div class="wrapper clearfix">
        <div id="logo">
            <a href="/pages/index.jsp"><img src="/resources/img/l.png" alt="Helper Service"></a>
        </div>

        <!-- nav -->
        <ul id="nav" class="sf-menu">
            <li class="current-menu-item"><a href="#" style="text-decoration: none">ГЛАВНАЯ</a></li>
            <li><a href="#" style="text-decoration: none">КАТАЛОГ РЕСУРСОВ</a>
                <ul>
                    <li><a href="/pages/catalog.jsp?uuidAuth=<%=uuidAuth%>" style="text-decoration: none">Мои
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
            <li><a href="/pages/signin.jsp"style="text-decoration: none">АВТОРИЗАЦИЯ</a></li>
            <%} else {%>
            <li><a href="/pages/profile.jsp?uuidAuth=<%=uuidAuth%>"style="text-decoration: none">ПРОФИЛЬ</a></li>
            <%}%>
        </ul>
        <!-- ends nav -->

        <!-- slider holder -->
        <div class="clearfix"></div>
        <div id="slider-holder" class="clearfix">

            <!-- slider -->
            <div class="flexslider home-slider">
                <ul class="slides">
                    <li>
                        <img src="/resources/img/slides/01.jpg" alt="alt text"/>
                    </li>
                    <li>
                        <img src="/resources/img/slides/02.jpg" alt="alt text"/>
                    </li>
                    <li>
                        <img src="/resources/img/slides/03.jpg" alt="alt text"/>
                    </li>
                </ul>
            </div>
            <!-- ENDS slider -->

            <div class="home-slider-clearfix "></div>

            <!-- Headline -->
            <div id="headline">
                <h4>Добро пожаловать!</h4>
                <p class="headline-text">Сохраняйте закладки прямо сейчас, ведь это очень просто и бесплатно.</p>
                <p class="headline-text">Быстрая и простая авторизация/регистрация, которая занимает не более минуты. <a
                        href="signin.jsp"
                        class="read-more">Страница авторизации</a></p>

            </div>
            <!-- ENDS headline -->


        </div>
        <!-- ENDS slider holder -->

    </div>
</header>


<!-- MAIN -->
<div id="main">
    <div class="wrapper clearfix">

        <!-- home-block -->
        <div class="home-block clearfix">
            <h1 class="home-headline">Преимущества</h1>

            <!-- thumbs -->
            <div class="clearfix">
                <figure>
                    <a href="single.html" class="thumb"><img src="/resources/img/dummies/featured-1.jpg"
                                                             alt="Alt text"/></a>
                    <figcaption>
                        <strong>Упорядочивание ссылок</strong>
                        <span>Создавайте любые курсы и разделы, которые нужны именно ВАМ. Наполняйте курсы дополнительной информацией.</span>
                    </figcaption>
                </figure>

                <figure>
                    <a href="single.html" class="thumb"><img src="/resources/img/dummies/featured-2.jpg"
                                                             alt="Alt text"/></a>
                    <figcaption>
                        <strong>Приватность или Публичность?</strong>
                        <span>При создании курса имеется возможность выбора режима доступа к содержимому. Делиться ресурсами или нет - выбор за вами.</span>
                    </figcaption>
                </figure>

                <figure>
                    <a href="single.html" class="thumb"><img src="/resources/img/dummies/featured-3.jpg"
                                                             alt="Alt text"/></a>
                    <figcaption>
                        <strong>Социальность</strong>
                        <span>Подписывайтесь на интересных Вам пользователей и просматривайте их публичные материалы.</span>
                    </figcaption>
                </figure>

                <figure>
                    <a href="single.html" class="thumb"><img src="/resources/img/dummies/featured-1.jpg"
                                                             alt="Alt text"/></a>
                    <figcaption>
                        <strong>Избранное</strong>
                        <span>Часто посещаете одни и теже курсы? Можете добавить их в "избранное" для быстрого доступа в два клика.</span>
                    </figcaption>
                </figure>

                <figure>
                    <a href="single.html" class="thumb"><img src="/resources/img/dummies/featured-3.jpg"
                                                             alt="Alt text"/></a>
                    <figcaption>
                        <strong>Простой и удобный интерфейс</strong>
                        <span>Современный и наглядный интерфейс сервиса делает работу с ним максимально удобной и комфортной. Основное меню обеспечивает быстрый доступ к нужным функциям.</span>
                    </figcaption>
                </figure>

                <figure>
                    <a href="single.html" class="thumb"><img src="/resources/img/dummies/featured-2.jpg"
                                                             alt="Alt text"/></a>
                    <figcaption>
                        <strong>Расширение для браузера</strong>
                        <span>Расширение позволяет добавлять закладки еще быстрей, а также импортировать закладки в HelperService с Вашего браузера.</span>
                    </figcaption>
                </figure>

            </div>
            <!-- ENDS thumbs -->
        </div>
        <!-- ENDS home-block -->


        <!-- additional blocks -->
        <div class="home-add clearfix">

            <!-- left -->
            <div class="left-home-block home-posts">
                <h4 class="heading">FROM THE BLOG</h4>
                <article class="format-standard">
                    <div class="entry-date">
                        <div class="number">23</div>
                        <div class="month">JAN</div>
                        <div class="year">2011</div>
                        <em></em></div>
                    <div class="post-heading">
                        <h4><a href="single.html">Lorem ipsum dolor </a></h4>
                    </div>
                    <div class="excerpt">Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac
                        turpis egestas. Vestibulum tortor quam, feugiat vitae.
                    </div>
                </article>

                <article class="format-standard">
                    <div class="entry-date">
                        <div class="number">23</div>
                        <div class="month">JAN</div>
                        <div class="year">2011</div>
                        <em></em></div>
                    <div class="post-heading">
                        <h4><a href="single.html">Lorem ipsum dolor </a></h4>
                    </div>
                    <div class="excerpt">Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac
                        turpis egestas. Vestibulum tortor quam, feugiat vitae.
                    </div>
                </article>

                <article class="format-standard">
                    <div class="entry-date">
                        <div class="number">23</div>
                        <div class="month">JAN</div>
                        <div class="year">2011</div>
                        <em></em></div>
                    <div class="post-heading">
                        <h4><a href="single.html">Lorem ipsum dolor </a></h4>
                    </div>
                    <div class="excerpt">Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac
                        turpis egestas. Vestibulum tortor quam, feugiat vitae.
                    </div>
                </article>

                <article class="format-standard">
                    <div class="entry-date">
                        <div class="number">23</div>
                        <div class="month">JAN</div>
                        <div class="year">2011</div>
                        <em></em></div>
                    <div class="post-heading">
                        <h4><a href="single.html">Lorem ipsum dolor </a></h4>
                    </div>
                    <div class="excerpt">Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac
                        turpis egestas. Vestibulum tortor quam, feugiat vitae.
                    </div>
                </article>


            </div>
        </div>
        <!-- ENDS additional blocks -->

        <!-- Fold image -->
        <div id="fold"></div>
    </div>

</div>
<!-- ENDS MAIN -->

<footer>

    <div class="wrapper clearfix">

        <!-- widgets -->
        <ul  class="widget-cols clearfix">
            <li class="first-col">

                <div class="widget-block">
                    <h4>RECENT POSTS</h4>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post" /></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post" /></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post" /></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                </div>
            </li>

            <li class="second-col">

                <div class="widget-block">
                    <h4>FREE TEMPLATES &amp; THEMES</h4>
                    <p>Visit <a href="http://templatecreme.com/" >Template Creme</a> and browse the selection of well-made FREE Templates and WordPress Themes.</p>
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
                    <p>Visit <a href="http://templatecreme.com/" >Template Creme</a> and browse the selection of well-made FREE Templates and WordPress Themes.</p>
                </div>

            </li>
        </ul>
        <!-- ENDS widgets -->


        <!-- bottom -->
        <div class="footer-bottom">
            <div class="left">Simpler Template by <a href="http://www.luiszuno.com" >luiszuno.com</a></div>
            <div class="right">
                <ul id="social-bar">
                    <li><a href="http://www.facebook.com"  title="Become a fan" class="poshytip"><img src="img/social/facebook.png"  alt="Facebook" /></a></li>
                    <li><a href="http://www.twitter.com" title="Follow my tweets" class="poshytip"><img src="img/social/twitter.png"  alt="twitter" /></a></li>
                    <li><a href="http://www.google.com"  title="Add to the circle" class="poshytip"><img src="img/social/plus.png" alt="Google plus" /></a></li>
                </ul>
            </div>
        </div>
        <!-- ENDS bottom -->

    </div>
</footer>
</body>
</html>