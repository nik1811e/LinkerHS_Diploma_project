<%@ page import="util.ReCaptchaUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Helper Service | Auth</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900'>
    <link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Montserrat:400,700'>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="/resources/css/auth_form.css">
</head>
<body>
<div class="form">
    <a href="index.jsp" style="text-decoration: none; cursor: pointer"><div class="thumbnail"><img src="/resources/img/hat.svg"/></div></a>
    <form class="register-form" action="/registration" method="post">
        <input type="text" class="form-control" name="fname" placeholder="Имя">
        <input type="text" class="form-control" name="lname" placeholder="Фамилия">
        <input type="date" class="form-control" name="bday" placeholder="День рождения">
        <input type="text" class="form-control" name="login" placeholder="Логин">
        <input type="email" class="form-control" name="email" placeholder="E-mail">
        <input type="password" class="form-control" name="password" placeholder="Пароль">
        <input type="password" class="form-control" name="confirm_password" placeholder="Подтвердите пароль">
        <div class="g-recaptcha" data-sitekey="<%=ReCaptchaUtil.PUBLIC%>"></div>
        <button type="submit">create</button>
        <p class="message">Уже зарегистрированны? <a href="#">Войти</a></p>
    </form>
    <form class="login-form" action="/auth" method="post">
        <input type="text" name="login_or_email" placeholder="Логин или E-mail"/>
        <input type="password" name="password" placeholder="Пароль"/>
        <button type="submit">Вход</button>
        <p class="message">Не зарегистрированы? <a href="#">Создать аккаунт</a></p>
    </form>
</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script  src="/resources/js/auth_form.js"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>
</body>
</html>
