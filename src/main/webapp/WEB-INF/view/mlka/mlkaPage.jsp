<%@ page isELIgnored="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="nkaTypeList" type="java.util.List<com.photochecker.model.mlka.NkaType>" scope="request"></jsp:useBean>

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
                    <h4>Фед. сеть:</h4>
                    <select class="selectBox" name="selNka" id="selNka">
                        <option disabled="" selected="" value="nothing"> -- сеть -- </option>
                        <c:forEach var="nkaType" items="${nkaTypeList}">
                            <option data-value="${nkaType.id}">${nkaType.name}</option>
                        </c:forEach>

                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Регион:</h4>
                    <select class="selectBox" name="selRegion" id="selRegion">

                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Дистрибьютор:</h4>
                    <select class="selectBox" name="selDistr" id="selDistr">

                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Сотрудник:</h4>
                    <select class="selectBox" name="selMlka" id="selMlka"></select>
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
            <b><input type="checkbox" id="mzCorrect">Корректность фото</b><br>
            <input type="checkbox" id="mzCrit1"><label for="mzCrit1" id="mzCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="mzCrit2"><label for="mzCrit2" id="mzCrit2Label">Критерий 2</label><br>
            <input type="checkbox" id="mzCrit3"><label for="mzCrit3" id="mzCrit3Label">Критерий 3</label>
        </div>
        <div class="clientInfoBlock" id="kInfoBlock">
            <div class="tgName">ТГ Кетчуп</div>
            <b><input type="checkbox" id="kPhoto">Наличие фото</b><br>
            <b><input type="checkbox" id="kCorrect">Корректность фото</b><br>
            <input type="checkbox" id="kCrit1"><label for="kCrit1" id="kCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="kCrit2"><label for="kCrit2" id="kCrit2Label">Критерий 2</label><br>
            <input type="checkbox" id="kCrit3"><label for="kCrit3" id="kCrit3Label">Критерий 3</label>
        </div>
        <div class="clientInfoBlock" id="sInfoBlock">
            <div class="tgName">ТГ Соус</div>
            <b><input type="checkbox" id="sPhoto">Наличие фото</b><br>
            <b><input type="checkbox" id="sCorrect">Корректность фото</b><br>
            <input type="checkbox" id="sCrit1"><label for="sCrit1" id="sCrit1Label">Критерий 1</label><br>
            <input type="checkbox" id="sCrit2"><label for="sCrit2" id="sCrit2Label">Критерий 2</label><br>
            <input type="checkbox" id="sCrit3"><label for="sCrit3" id="sCrit3Label">Критерий 3</label>
        </div>
        <div class="clientInfoBlock">
            <b><input type="checkbox" id="oos">Наличие Out Of Stock</b><br>
            <div class="tgName">Комментарий:</div>
            <textarea id="comment" class="commentArea"></textarea>
            <input type="button" id="saveButton" name="saveButton" value="Сохранить">
            <input type="button" id="clearButton" name="clearButton" value="Очистить">
            <input type="button" id="to_xlsx" name="to_xlsx" value="Выгрузить в Excel">
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>