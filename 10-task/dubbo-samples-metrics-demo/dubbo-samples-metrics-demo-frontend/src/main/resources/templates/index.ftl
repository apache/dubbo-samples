<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="jquery-3.6.3.min.js"></script>
    <title>Dubbo Onling Shopping Demo</title>
    <style type="text/css">
        form {
            width: 100%;
            height: 100%;
            font-size: 1.2rem;
        }

        div {
            display: inline-block;
            padding-bottom: 255px;
            padding-left: 1px;
            padding-right: 1px;
        }

        h2 {
            font-size: 40px;
            color: black;
        }

        #log {
            color: blue;
        }

        ul li {
            text-align: left;
        }

        .image img {
            width: 550px;
            height: 300px;
            margin: 5px;
            border: 1px solid black;
        }
    </style>

    <script type="text/javascript">
        function normalLogin() {
            var username = $("#username").val();
            var password = $("#password").val();

            window.location.href = "/login?username=" + username + "&password=" + password;
        }

        function timeoutLogin() {
            var username = $("#username").val();
            var password = $("#password").val();

            window.location.href = "/timeoutLogin?username=" + username + "&password=" + password;
        }

        function grayLogin() {
            var username = $("#username").val();
            var password = $("#password").val();

            window.location.href = "/grayLogin?username=" + username + "&password=" + password;
        }
    </script>
</head>
<body>
<!--div class="div">
    <img src="favicon.png" width="100%" height="200px" alt=""  id="picture">
</div-->
<form id="loginForm" name="login" method="post">
    <center>
        <div>
            <h2>
                The Dubbo Online Shopping Mall Demo
            </h2>
            <p class="image">
                <img src="architecture.png"/>
            </p>
            <#if result??>
                <p style="color:red; font-size: 1.3rem">
                    ${result}
                </p>
            </#if>
            <p>
            <p>
                Username: <input id="username" type="text" name="username"/>
            </p>
            <p>
                Password: <input id="password" type="password" name="password"/>
            </p>
            </p>
            <p>
                <input id=login onclick="normalLogin()" type="button" value="Login"/>
                <input id=timeout-login onclick="timeoutLogin()" type="button" value="Timeout Login"/>
                <input id=gray-login onclick="grayLogin()" type="button" value="Login To Gray"/>
            </p>

            <p>
            <ul style="list-style-position:inside;">
                <li>Input any username and password to login.</li>
                <li>Use username 'dubbo' to test features like traffic isolation, argument routing, etc.</li>
            </ul>
            </p>
        </div>
    </center>
</form>
</body>
</html>