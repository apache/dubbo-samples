<html>

<head>
    <title>Dubbo Onling Shopping Demo</title>
    <script src="jquery-3.6.3.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $.ajax({
                url: "/userinfo?username=" + "${username}",
                success: function (result) {
                    if (result === "") {
                        $("#userinfo").html("<label id='retry'>Failed to get user info, please add retry policy and refresh!</label>");
                    } else {
                        $("#userinfo").html("User Info: " + result.username + ", " + result.realName + ", " + result.mail + ", " + result.phone);
                    }
                },
                error: function (xhr, status, error) {
                    $("#userinfo").innerHTML = "<label id='retry'>Failed to get user info in background, please add retry policy and refresh!</label>";
                },
                complete: function (xhr, status) {
                }
            });
        });

        function buyNow() {
            console.log("${username}");
            $.post("/order?username=", {username: "${username}", sku: 1},
                function (result, status, xhr) {
                    if (result === "") {
                        $("#orderDetail").html("<label>Order created successfully!</label>");
                    } else {
                        $("#orderDetail").html("Order created successfully! <br/><br/> Here's the package delivery message " + result.receiver + ", " + result.phone + ", " + result.address);
                    }
                });
        }
    </script>
    <style>
        #retry {
            color: red;
            font-size: 1.3rem;
        }

        .image {
            width: 100%;
            height: 510px;
            border: 1px solid black;
        }

        #userinfo {
            width: 100%;
            height: 100px;
            border: 1px solid black;
        }

        .image img {
            margin: 5px;
            padding-right: 5px;
            width: 1000px;
            height: 500px;
            border: 1px solid black;
        }

        .detail {
            margin: 5px;
            float: right;
            height: 500px;
            border: 1px solid black;
        }

        #orderDetail {
            margin: 5px;
            border: 1px solid black;
        }
    </style>
</head>

<body>

<p id="userinfo"></p>

<div>
    <div>
        <div class="image">
            <img src="goods.png"/>
            <p style="width:100px;float:right;position:absolute;left:1115px;top:550px;">
                <input style="width:100px;height:50px;" id=login onclick="buyNow()" type="button" value="Buy Now"/>
            </p>
            <div class="detail">

                <ul>
                    <li>SKU: ${item.sku}</li>
                    <li>Name: ${item.itemName}</li>
                    <li>Description: ${item.description}</li>
                    <li><label>Comment: ${item.comment}</label></li>
                    <li><label>Price: ${item.price}</label></li>
                </ul>

            </div>
        </div>


    </div>

    <div id="orderDetail">

    </div>

</div>


</body>

</html>