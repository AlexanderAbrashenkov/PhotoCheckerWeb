<%@ page isELIgnored="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="content_pane">
    <div id="showPhoto">
        <div id="photoFull">
            <div id="toLeft">
                <div class="blockBeforeImage"></div>
                <img class="arrow" src="../images/left.png">
            </div>
            <div id="fullPhotoInfo">
                <span class="title"><b>Дата фото: </b></span>
                <span id="fullPhotoDate"></span>
                <span class="title"><b>Дата добавления фото: </b></span>
                <span id="fullPhotoAddDate"></span>
                <span class="title"><b>Комментарий: </b></span>
                <span id="fullPhotoComment"></span>
            </div>
            <div id="fullPhotoCont">
                <img id="photoView" src="">
            </div>
            <div id="toRight">
                <div class="blockBeforeImage"></div>
                <img class="arrow" src="../images/right.png">
            </div>
        </div>
        <div id="photoControl">
            <div id="fullPhotoNum" class="photoNum"> /</div>
            <div id="fullPhotoCount" class="photoNum"></div>
            <br><br>
            <div id="zoomIn" class="iconBlock">
                <img src="../images/zoom-in.png">
            </div>
            <div id="zoomOut" class="iconBlock">
                <img src="../images/zoom-out.png">
            </div>
            <div id="turnForward" class="iconBlock">
                <img src="../images/forward.png">
            </div>
            <div id="turnBack" class="iconBlock">
                <img src="../images/back.png">
            </div>
            <br><br>
            <div id="close" class="iconBlock">
                <img src="../images/close.png">
            </div>
        </div>
    </div>
    <div id="left_pane">
        <div id="filter_menu">
            <form id="lkaParamethers" action="" method="post">
                <h3>Даты:</h3>
                <input type="date" class="datePicker" id="dateFrom" name="dateFrom" value="${startDate}">
                -
                <input type="date" class="datePicker" id="dateTo" name="dateTo" value="${endDate}">
                <div class="filterBlock">
                    <h4>Регион:</h4>
                    <select class="selectBox" name="selRegion" id="selRegion">
                        <option disabled selected value="nothing"> -- регион -- </option>
                        <c:forEach items="${regionMap}" var="region">
                            <option data-value="${region.key}">${region.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Дистрибьютор:</h4>
                    <select class="selectBox" name="selDistr" id="selDistr">

                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Сеть:</h4>
                    <select class="selectBox" name="selLka" id="selLka"></select>
                </div>
            </form>
            <hr>
        </div>
        <div id="address_pane">
            <table id="addressTable">

            </table>
        </div>
    </div>
    <div id="center_pane">

    </div>
    <div id="right_pane">
        <div class="clientInfoBlock">
            <div id="clientId" class="clientInfo">ID: Client id</div>
            <div id="clientType" class="clientInfo">Тип: Client type</div>
        </div>
        <div class="clientInfoBlock" id="mzInfoBlock">
            <div class="tgName">ТГ Майонез</div>
            <b><input type="checkbox" id="mzPhoto">Наличие фото</b><br>
            <b style="color:blue"><input type="checkbox" id="mzSP">Наличие МЗ Ласка Постный</b><br>
            <b><input type="checkbox" id="mzCorrect">Корректность фото</b><br>
            <input type="checkbox" id="mzCrit1"><label for="mzCrit1" id="mzCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="mzCrit2"><label for="mzCrit2" id="mzCrit2Label">Критерий 2</label>
        </div>
        <div class="clientInfoBlock" id="kInfoBlock">
            <div class="tgName">ТГ Кетчуп</div>
            <b><input type="checkbox" id="kPhoto">Наличие фото</b><br>
            <b><input type="checkbox" id="kCorrect">Корректность фото</b><br>
            <input type="checkbox" id="kCrit1"><label for="kCrit1" id="kCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="kCrit2"><label for="kCrit2" id="kCrit2Label">Критерий 2</label>
        </div>
        <div class="clientInfoBlock" id="sInfoBlock">
            <div class="tgName">ТГ Соус</div>
            <b><input type="checkbox" id="sPhoto">Наличие фото</b><br>
            <b><input type="checkbox" id="sCorrect">Корректность фото</b><br>
            <input type="checkbox" id="sCrit1"><label for="sCrit1" id="sCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="sCrit2"><label for="sCrit2" id="sCrit2Label">Критерий 2</label>
        </div>
        <div class="clientInfoBlock" id="mInfoBlock">
            <div class="tgName">ТГ Масло</div>
            <b><input type="checkbox" id="mPhoto">Наличие фото</b><br>
            <b><input type="checkbox" id="mCorrect">Корректность фото</b><br>
            <input type="checkbox" id="mCrit1"><label for="mCrit1" id="mCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="mCrit2"><label for="mCrit2" id="mCrit2Label">Критерий 2</label>
        </div>
        <div class="clientInfoBlock">
            <b><input type="checkbox" id="oos">Наличие Out Of Stock</b><br>
            <div class="tgName">Комментарий:</div>
            <textarea id="comment" class="commentArea"></textarea>
            <input type="button" id="saveButton" name="saveButton" value="Сохранить">
            <input type="button" id="clearButton" name="clearButton" value="Очистить">
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>