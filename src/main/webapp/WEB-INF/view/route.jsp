
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Маршруты</title>
    <link type="text/css" href="../css/style.css" rel="stylesheet">
    <style>
        /* Always set the map height explicitly to define the size of the div
   * element that contains the map. */

        #map {
            height: calc(100% - 50px);
        }
        /* Optional: Makes the sample page fill the window. */

        html,
        body {
            height: 100%;
            margin: 0;
            padding: 0;
        }

        #floating-panel {
            background: #fff;
            font-size: 14px;
            padding: 5px;
            border-style: solid;
            border-width: 1px;
        }

        #right-panel:hover {
            width: 390px;
        }

        #start,
        #end {
            width: 300px;
        }

        #right-panel {
            font-family: 'Roboto', 'sans-serif';
            line-height: 30px;
            padding-left: 10px;
            transition-property: width;
            transition-duration: 2s;
        }

        .adp-directions {
            display: none;
        }

        #right-panel select,
        #right-panel input {
            font-size: 15px;
        }

        #right-panel select {
            width: 100%;
        }

        #right-panel i {
            font-size: 12px;
        }

        #right-panel {
            height: calc(100% - 50px);
            float: right;
            width: 25px;
            overflow: auto;
        }

        #but,
        #butSav {
            width: 150px;
            height: 30px;
        }

        #centr {
            text-align: center;
            padding-top: 8px
        }

        textarea {
            resize: none;
        }
        /* #map {
       //     margin-right: 400px;
       // }*/

        @media print {
            #map {
                height: 500px;
                margin: 0;
            }
            #right-panel {
                float: none;
                width: auto;
            }
        }
    </style>
    <script type="text/javascript">
        function showOrHide(che, chet) {
            che = document.getElementById(che);
            chet = document.getElementById(chet);
            if (che.checked) {
                chet.style.display = "block";
                chet.style.width = "298px";
                chet.style.height = "190px";
            }
            else {
                chet.style.display = "none";
            }
        }
    </script>
</head>

<body>
<div id="head_pane">
    <div class="report_title">
        <a href="/" style="border: 0;">Главная</a> /
    </div>
    <div id="filter_pane">
        <div id="logoff_pane">
                        <span id="lbl_user_name">${sessionScope.user.userName}
                            <input type="button" id="exit" value="Выход" onclick="window.location = '/logoff';"><br>
                        </span>
        </div>
    </div>
</div>
<div id="top_separate"></div>
<div id="floating-panel">
    <strong>Начало:</strong>
    <div id="box">
        <input type="text" id="start">
        <br>
    </div>
    <strong>Конец:</strong>
    <div>
        <input type="text" id="end">
    </div>
    <strong>Промежуточные точки:
        <input type="checkbox" id='che' onchange = 'showOrHide("che", "chet")'>
    </strong>
    <div>
        <textarea placeholder="Максимум 23 промежуточных точек. Для разделения адресов использовать - ;" id="chet" style='display: none'></textarea>
    </div>
    <div id="centr">
        <button id="but">Построить маршрут</button>
        <button id="butSav">Сохранить маршрут</button>
    </div>
    <img>
</div>
<div id="right-panel">
</div>
<div id="map"></div>

<script>
    function initMap() {
        var directionsDisplay = new google.maps.DirectionsRenderer;
        var directionsService = new google.maps.DirectionsService;
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 4,
            center: { lat: 55.79, lng: 49.10 }
        });
        directionsDisplay.setMap(map);
        directionsDisplay.setPanel(document.getElementById('right-panel'));
        var control = document.getElementById('floating-panel');
        control.style.display = 'block';
        map.controls[google.maps.ControlPosition.TOP_RIGHT].push(control);

        var onChangeHandler = function () {
            calculateAndDisplayRoute(directionsService, directionsDisplay);
        };
        document.getElementById('but').addEventListener('click', onChangeHandler);
        var description = document.getElementsByTagName("adp-text");
    }
    function calculateAndDisplayRoute(directionsService, directionsDisplay) {
        var start = document.getElementById('start').value;
        var end = document.getElementById('end').value;
        var val = document.getElementById('chet').value;

        j = 0;
        l = 0;
        if (val != "") {
            for (var i = 0; i < val.length; i++) {
                if (val.charAt(i) == ';') {
                    var street = val.slice(j, i);
                    if (wpt == undefined) {
                        var wpt = [{ location: street, stopover: true }];
                    }
                    else {
                        wpt.push({ location: street, stopover: true });
                    }
                    j = i + 2;
                    l = l + 1
                };
            };
        }
        var request = {
            origin: start,
            destination: end,
            waypoints: wpt,
            optimizeWaypoints: true,
            travelMode: google.maps.DirectionsTravelMode.DRIVING
        };
        directionsService.route(request, function (response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            }
        });

    }
</script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCqy7bZuU0AFm5UsnZXNB0JOGP5S3UEoVM&callback=initMap">
</script>
<script>
    ////////////сохраняем в текстовый файл то что есть///////////////////////////////
    function sav() {
        var list = document.body.innerHTML;
        var name = "filename.txt";
    };
    //////////////////////////////////////////////////////////////////////////////////
</script>
</body>

</html>