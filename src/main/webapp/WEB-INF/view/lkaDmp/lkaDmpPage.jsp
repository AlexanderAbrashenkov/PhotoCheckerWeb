<%@ page isELIgnored="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="regionList" type="java.util.List<com.photochecker.model.Region>" scope="request"></jsp:useBean>

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
                        <c:forEach items="${regionList}" var="region">
                            <option data-value="${region.id}">${region.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Дистрибьютор:</h4>
                    <select class="selectBox" name="selDistr" id="selDistr">

                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Канал:</h4>
                    <select class="selectBox" name="selChannel" id="selChannel"></select>
                </div>
                <div class="filterBlock hidden">
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
        <div id="dmpCounter" class="clientInfoBlock">Количество ДМП
            <select id="dmpCountSelector">
                <option data-value="1" selected="selected">1</option>
                <option data-value="2">2</option>
                <option data-value="3">3</option>
                <option data-value="4">4</option>
                <option data-value="5">5</option>
                <option data-value="6">6</option>
                <option data-value="7">7</option>
                <option data-value="8">8</option>
                <option data-value="9">9</option>
            </select>
        </div>
        <div class="tab clientInfoBlock">
            <button class="tablinks active" onclick="openDmp(event, 'dmp1')">ДМП 1</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp2')">ДМП 2</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp3')">ДМП 3</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp4')">ДМП 4</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp5')">ДМП 5</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp6')">ДМП 6</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp7')">ДМП 7</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp8')">ДМП 8</button>
            <button class="tablinks hidden" onclick="openDmp(event, 'dmp9')">ДМП 9</button>
        </div>


        <div id="dmp1" class="dmp">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
	            <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
	            <div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
	            <input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

		<div id="dmp2" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp3" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp4" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp5" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp6" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp7" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp8" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>

        <div id="dmp9" class="dmp hidden">
	        <div class="clientInfoBlock" class="corr">
	            <div class="corrTitle">Корректность фото:</div>
                <input type="button" class="allOk" value="все ОК"><br>
	            <input type="checkbox" class="isPhotoCorr"> Фото корректное<br>
	            <input type="checkbox" class="keyword"> Наличие кодового слова
	        </div>
	        <div class="clientInfoBlock" class="tgList">
				<div class="header">Наличие ТГ на ДМП:</div>
				<input type="checkbox" class="mz"><label class="bold">Майонез</label><br>
				<input type="checkbox" class="mzRicco shift">Mr. Ricco<br>
				<input type="checkbox" class="mzRSpec shift2"><label class="italic">в т.ч. Авокадо</label><br>
				<input type="checkbox" class="mzMilad shift">Миладора<br>
				<input type="checkbox" class="mzMSpec shift2"><label class="italic">в т.ч. ПЭТ 50%</label><br>
				<input type="checkbox" class="k"><label class="bold">Кетчуп</label><br>
				<input type="checkbox" class="s"><label class="bold">Соус</label><br>
				<input type="checkbox" class="sSpec shift2"><label class="italic">в т.ч. красные ПЭТ-банки</label><br>
				<input type="checkbox" class="m"><label class="bold">Масло</label><br>
				<input type="checkbox" class="mSpec shift">Mr. Ricco
	        </div>
	        <div class="clientInfoBlock" class="result">
	            <div class="header">Критерии оценки</div>
	            <input type="checkbox" class="minSize">Соответствует минимальному размеру<br>
	            <input type="checkbox" class="tmaProd">Акционный продукт<br>
	            <input type="checkbox" class="price">Наличие ценника<br>
				<input type="checkbox" class="fill80">Заполненность ДМП на 80%<br>
	            <input type="checkbox" class="place">Место размещения
	        </div>
	        <div class="clientInfoBlock" class="commentBlock">
	            <div class="header">Комментарий:</div>
	            <textarea  class="commentArea"></textarea>
	        </div>
        </div>


        <div class="clientInfoBlock">
            <input type="button" id="saveButton" name="saveButton" value="Сохранить">
            <input type="button" id="clearButton" name="clearButton" value="Очистить">
            <input type="button" id="to_xlsx" name="to_xlsx" value="Выгрузить в Excel">
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>