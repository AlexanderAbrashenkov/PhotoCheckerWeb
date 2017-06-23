<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#include "../header.ftl">

<div id="content_pane">
    <div id="showPhoto">
        <div id="photoFull">
            <div id="toLeft">
                <div class="blockBeforeImage"></div>
                <img class="arrow" src="<@spring.url "/resources/images/left.png"/>">
            </div>
            <div id="fullPhotoInfo">
                <span class="title"><b>Дата фото: </b></span>
                <span id="fullPhotoDate"></span>
            </div>
            <div id="fullPhotoCont">
                <img id="photoView" src="">
            </div>
            <div id="toRight">
                <div class="blockBeforeImage"></div>
                <img class="arrow" src="<@spring.url "/resources/images/right.png"/>">
            </div>
        </div>
        <div id="photoControl">
            <div id="fullPhotoNum" class="photoNum"> /</div>
            <div id="fullPhotoCount" class="photoNum"></div>
            <br><br>
            <div id="zoomIn" class="iconBlock">
                <img src="<@spring.url "/resources/images/zoom-in.png"/>">
            </div>
            <div id="zoomOut" class="iconBlock">
                <img src="<@spring.url "/resources/images/zoom-out.png"/>">
            </div>
            <div id="turnForward" class="iconBlock">
                <img src="<@spring.url "/resources/images/forward.png"/>">
            </div>
            <div id="turnBack" class="iconBlock">
                <img src="<@spring.url "/resources/images/back.png"/>">
            </div>
            <br><br>
            <div id="close" class="iconBlock">
                <img src="<@spring.url "/resources/images/close.png"/>">
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
                    <h4>Область:</h4>
                    <select class="selectBox" name="selNstObl" id="selNstObl">
                        <option disabled="" selected="" value="nothing"> -- область -- </option>
                        <#list nstOblList as nstObl>
                            <option data-value="${nstObl.id?string["0"]}">${nstObl.name}</option>
                        </#list>
                    </select>
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
            <div id="visitCountField">Количество посещений:
                <select name="selVisitCount" id="selVisitCount">
                    <option data-value="1">1</option>
                    <option data-value="2">2</option>
                    <option data-value="3">3</option>
                    <option data-value="4">4</option>
                    <option data-value="5">5</option>
                    <option data-value="6">6</option>
                    <option data-value="7">7</option>
                </select>
            </div>
        </div>
        <div class="clientInfoBlock" id="mzInfoBlock">
            <div class="tgName">ТГ Майонез</div>

            <input type="checkbox" selected="selected" id="mzMatrix">
            <label for="mzMatrix">ТГ есть в матрице</label><br>

            <b><input type="checkbox" id="mzPhoto">
                <label for="mzPhoto">Наличие фото</label>
            </b><br>

            <b><input type="checkbox" id="mzBorders">
                <label for="mzBorders">Видны границы полки</label>
            </b><br>
            <input type="checkbox" id="mzVert">
            <label for="mzVert">вертикальный блок</label><br>

            <input type="checkbox" id="mz30">
            <label for="mz30">30% полки</label><br>

            <input type="checkbox" id="mzCenter">
            <label for="mzCenter">по центру</label><br>

            <input type="text" class="commInput" id="mzCommInput">
            <select class="commVars">
                <option></option>
                <option>Выставить по центру вертикальным бренд-блоком во всю высоту полочного пространства</option>
                <option>Расширить вертикальный бренд-блок от верхней до нижней полки</option>
                <option>Выровнять вертикальный бренд-блок</option>
                <option>По наличию товара выставить вертикальным бренд-блоком во всю высоту полочного пр-ва занимая не менее 30% полочного пр-ва</option>
                <option>Фото ДМП. Предоставить фото основной полки</option>
                <option>Фото плохого качества</option>
                <option>Занять долю полки 30%</option>
                <option>Выставить по центру</option>
            </select>
            <br><br>
        </div>
        <div class="clientInfoBlock" id="ksInfoBlock">
            <div class="tgName">ТГ Кетчуп + Соус</div>

            <input type="checkbox" selected="selected" id="ksMatrix">
            <label for="ksMatrix">ТГ есть в матрице</label><br>

            <b><input type="checkbox" id="ksPhoto">
                <label for="ksPhoto">Наличие фото</label>
            </b><br>

            <b><input type="checkbox" id="ksBorders">
                <label for="ksBorders">Видны границы полки</label>
            </b><br>

            <input type="checkbox" id="ksVert">
            <label for="ksVert">вертикальный блок</label><br>

            <input type="checkbox" id="ks30">
            <label for="ks30">30% полки</label><br>

            <input type="checkbox" id="ksCenter">
            <label for="ksCenter">по центру</label><br>

            <input type="text" class="commInput" id="ksCommInput">
            <select class="commVars">
                <option></option>
                <option>Выставить по центру вертикальным бренд-блоком во всю высоту полочного пространства</option>
                <option>Расширить вертикальный бренд-блок от верхней до нижней полки</option>
                <option>Выровнять вертикальный бренд-блок</option>
                <option>По наличию товара выставить вертикальным бренд-блоком во всю высоту полочного пр-ва занимая не менее 30% полочного пр-ва</option>
                <option>Фото ДМП. Предоставить фото основной полки</option>
                <option>Фото плохого качества</option>
                <option>Занять долю полки 30%</option>
                <option>Выставить по центру</option>
            </select>
            <br><br>
        </div>
        <div class="clientInfoBlock" id="mInfoBlock">
            <div class="tgName">ТГ Масло</div>

            <input type="checkbox" selected="selected" id="mMatrix">
            <label for="mMatrix">ТГ есть в матрице</label><br>

            <b><input type="checkbox" id="mPhoto">
                <label for="mPhoto">Наличие фото</label>
            </b><br>

            <b><input type="checkbox" id="mBorders">
                <label for="mBorders">Видны границы полки</label>
            </b><br>

            <input type="checkbox" id="mVert">
            <label for="mVert">вертикальный блок</label><br>

            <input type="checkbox" id="mCenter">
            <label for="mCenter">по центру</label><br>

            <input type="text" class="commInput" id="mCommInput">
            <select class="commVars">
                <option></option>
                <option>Выставить по центру вертикальным бренд-блоком во всю высоту полочного пространства</option>
                <option>Расширить вертикальный бренд-блок от верхней до нижней полки</option>
                <option>Выровнять вертикальный бренд-блок</option>
                <option>По наличию товара выставить вертикальным бренд-блоком во всю высоту полочного пр-ва занимая не менее 30% полочного пр-ва</option>
                <option>Фото ДМП. Предоставить фото основной полки</option>
                <option>Фото плохого качества</option>
                <option>Занять долю полки 30%</option>
                <option>Выставить по центру</option>
            </select>
            <br><br>
        </div>
        <div class="clientInfoBlock">
            <input type="button" id="saveButton" name="saveButton" value="Сохранить">
            <input type="button" id="clearButton" name="clearButton" value="Очистить">
            <input type="button" id="to_xlsx" name="to_xlsx" value="Выгрузить в Excel">
        </div>
    </div>
</div>

<#include "../footer.ftl">