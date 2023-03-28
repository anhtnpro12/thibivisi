<%-- 
    Document   : test
    Created on : Mar 15, 2023, 5:12:02 AM
    Author     : TNA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />       

        <style type="text/css">
            *{
                box-sizing:border-box;
                /* outline:1px solid ;*/
            }
            body{
                background: #ffffff;
                background: linear-gradient(to bottom, #ffffff 0%,#e1e8ed 100%);
                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#e1e8ed',GradientType=0 );
                height: 100%;
                margin: 0;
                background-repeat: no-repeat;
                background-attachment: fixed;

            }

            .wrapper-1{
                width:100%;
                height:100vh;
                display: flex;
                flex-direction: column;
            }
            .wrapper-2{
                padding :30px;
                text-align:center;
            }
            h1{
                font-family: 'Kaushan Script', cursive;
                font-size:4em;
                letter-spacing:3px;
                color:#5892FF ;
                margin:0;
                margin-bottom:20px;
            }
            .wrapper-2 h2 {
                margin:0;
                font-size:2em;
                color:#aaa;
                font-family: 'Source Sans Pro', sans-serif;
                letter-spacing:1px;
            }
            .wrapper-2 p{
                margin:0;
                color:#aaa;
                font-family: 'Source Sans Pro', sans-serif;
                letter-spacing:1px;
            }
            .go-home{
                color:#fff;
                background:#5892FF;
                border:none;
                padding:10px 50px;
                margin:30px 0;
                border-radius:30px;
                text-transform:capitalize;
                box-shadow: 0 10px 16px 1px rgba(174, 199, 251, 1);
            }
            .go-home:hover {
                cursor: pointer;
                opacity: 0.8;
            }
            .footer-like{
                margin-top: auto;
                background:#D7E6FE;
                padding:6px;
                text-align:center;
            }
            .footer-like p{
                margin:0;
                padding:4px;
                color:#5892FF;
                font-family: 'Source Sans Pro', sans-serif;
                letter-spacing:1px;
            }
            .footer-like p a{
                text-decoration:none;
                color:#5892FF;
                font-weight:600;
            }
            


            @media (min-width:360px){
                h1{
                    font-size:4.5em;
                }
                .go-home{
                    margin-bottom:20px;
                }
            }

            @media (min-width:600px){
                .content{
                    max-width:1000px;
                    margin:0 auto;
                }
                .wrapper-1{
                    height: initial;
                    max-width:620px;
                    margin:0 auto;
                    margin-top:50px;
                    box-shadow: 4px 8px 40px 8px rgba(88, 146, 255, 0.2);
                }

            }
        </style>
    </head>

    <body style="background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;">
        <div class=content>
            <div class="wrapper-1">
                <div class="wrapper-2">
                    <h1>Thank you !</h1>
                    <h2><strong>Please use this account to log in!!!</strong></h2>
                    <div>
                        <p>
                            Your Account:
                            <br>
                            Your Password:
                        </p>                   
                    </div>
                    <a href="customerlogin">
                        <input class="go-home" type="button" value="LOG IN" />                        
                    </a>
                </div>
                <div class="footer-like">
                    <p>If you want to continue shopping, you can return to the  
                        <a href="shop">shop</a>
                    </p>
                </div>
            </div>
        </div>



        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Source+Sans+Pro" rel="stylesheet">
        
    </body>

</html>