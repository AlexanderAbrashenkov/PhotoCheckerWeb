/**
 * Created by market8 on 02.05.2017.
 */
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
function zap() {
    document.getElementById('but').addEventListener('click', tim);
    var tim = setTimeout(function () {
        var elem = document.getElementById('right-panel')
        if (elem.length != 0) {
            sav(0);
        }
    }, 2000);
}
function sav(oh) {
    km = 0;
    f = 0;
    d = 0;
    fl = 0;
    l = 0;
    var addr = '';
    var list = document.querySelector('div.adp').innerText;

    //Убедимся в правильном разбиении...
    stop = 0;
    for (i = 0; i <= list.length - 39; i++) {
        if (d % 2 == 1 && list[i] == "к" || list[i] == "м" && list[i - 1] != "к" && l < 7) {
            if (list[i] == "к" && list[i + 1] == "м" && list[i - 1] == " ") {
                mer = 1;
            } else {
                mer = 0;
            }
            chis = list.slice(f, i - 1)
            /*for (m = 0; m <= chis.length; m++) {
             if (chis[m] == ",") {
             chis = chis.slice(0, m) + "." + chis.slice(m + 1, chis.length)
             }
             if (chis[m] === " ") {
             chis = chis.slice(0, m) + "" + chis.slice(m + 1, chis.length)
             }
             }*/
            if (chis.indexOf(',') > -1) {
                chis = chis.replace(',', '.');
            }

            console.log(chis.indexOf(' '));
            if (chis.indexOf(' ') > -1 || chis.indexOf(String.fromCharCode(160)) > -1) {
                chis = chis.replace(' ', '');
                chis = chis.replace(String.fromCharCode(160), '');
            }

            if (mer == 0) {
                wer = Number(chis) / 1000
            } else {
                wer = Number(chis)
            }

            km = km + wer
        }
        l++;
        if (list[i] == "\n") {
            if (d % 2 != 1) {
                addr = addr + list.slice(stop, i) + "\n "
            }
            stop = i + 1;
            f = i + 1;
            d++;
            fl = i + 1;
            l = 0;
        };
    }
    if (oh == 1) {
        document.getElementById('a').href = 'data:text/csv;charset=utf-8,%EF%BB%BF' + encodeURIComponent(addr);
    }
    var elem = document.getElementsByTagName('p')
    var textnode = document.createTextNode("Итоговое расстояние " + km.toFixed(3) + " км");
    if (elem.length != 0) {
        var $outer = $("#right-panel"),
            $p = $outer.find('p');
        $p.each(function () {
            $(this).remove();
            var node;
            node = document.createElement("p");
            node.appendChild(textnode);
            document.getElementById("right-panel").appendChild(node);
        });
    } else {
        var node;
        node = document.createElement("p");
        node.appendChild(textnode);
        document.getElementById("right-panel").appendChild(node);
    }
}