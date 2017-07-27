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
                    <h4>RJKAM:</h4>
                    <select class="selectBox" name="selRjkam" id="selRjkam">
                        <option disabled="" selected="" value="nothing"> -- rjkam -- </option>
                        <#list rjkamList as rjkam>
                            <option data-value="${rjkam.id?string["0"]}">
                                <#if rjkam.id gt 0>
                                    ${rjkam.name}
                                <#else>
                                    Без имени
                                </#if>
                            </option>
                        </#list>
                    </select>
                </div>
                <div class="filterBlock">
                    <h4>Сеть:</h4>
                    <select class="selectBox" name="selNka" id="selNka">

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
            <div id="clientId" class="clientInfo">ID: Client id</div>
            <div id="clientType" class="clientInfo">Тип: Client type</div>
        </div>
        <div class="clientInfoBlock" id="mzInfoBlock">
            <div class="tgName">ТГ Майонез</div>
            <input type="checkbox" id="mzDP"><label for="mzDP" id="mzDPLabel">Доля полки</label><br>
            <input type="checkbox" id="mzBB"><label for="mzBB" id="mzBBLabel">Бренд-блок</label><br>
            <input type="checkbox" id="mzMR"><label for="mzMR" id="mzMRLabel">Место размещения</label>
            <input type="text" id="mzComment" class="commentArea">
        </div>
        <div class="clientInfoBlock" id="kInfoBlock">
            <div class="tgName">ТГ Кетчуп</div>
            <input type="checkbox" id="kDP"><label for="kDP" id="kDPLabel">Доля полки</label><br>
            <input type="checkbox" id="kBB"><label for="kBB" id="kBBLabel">Бренд-блок</label><br>
            <input type="checkbox" id="kMR"><label for="kMR" id="kMRLabel">Место размещения</label>
            <input type="text" id="kComment" class="commentArea">
        </div>
        <div class="clientInfoBlock" id="sInfoBlock">
            <div class="tgName">ТГ Соус</div>
            <input type="checkbox" id="sDP"><label for="sDP" id="sDPLabel">Доля полки</label><br>
            <input type="checkbox" id="sBB"><label for="sBB" id="sBBLabel">Бренд-блок</label><br>
            <input type="checkbox" id="sMR"><label for="sMR" id="sMRLabel">Место размещения</label>
            <input type="text" id="sComment" class="commentArea">
        </div>
        <div class="clientInfoBlock" id="doubleInfoBlock">
            <div class="tgName">Дублирующая выкладка</div>
            <input type="checkbox" id="mzDouble"><label for="mzDouble" id="mzDoubleLabel">Майонез</label><br>
            <input type="checkbox" id="kDouble"><label for="kDouble" id="kDoubleLable">Кетчуп</label><br>
            <input type="checkbox" id="sDouble"><label for="sDouble" id="sDoubleLable">Соус</label>
        </div>
        <div class="clientInfoBlock" id="dmAInfoBlock">
            <div class="tgName">Доп. места акционные</div>
            <input type="text" class="tgNum" id="mzDmA"> / <label id="mzTmaPlan" class="tmaPlan"></label>   <label for="mzDmA" id="mzDmALabel">Майонез</label><br>
            <input type="text" class="tgNum" id="kDmA"> / <label id="kTmaPlan" class="tmaPlan"></label>   <label for="kDmA" id="kDmALable">Кетчуп</label><br>
            <input type="text" class="tgNum" id="sDmA"> / <label id="sTmaPlan" class="tmaPlan"></label>   <label for="sDmA" id="sDmALable">Соус</label>
        </div>
        <div class="clientInfoBlock" id="dmNaInfoBlock">
            <div class="tgName">Доп. места неакционные</div>
            <input type="checkbox" id="mzDmNa"><label for="mzDmNa" id="mzDmNaLabel">Майонез</label><br>
            <input type="checkbox" id="kDmNa"><label for="kDmNa" id="kDmNaLable">Кетчуп</label><br>
            <input type="checkbox" id="sDmNa"><label for="sDmNa" id="sDmNaLable">Соус</label>
        </div>
        <div class="clientInfoBlock">
            <input type="button" id="saveButton" name="saveButton" value="Сохранить">
            <input type="button" id="clearButton" name="clearButton" value="Очистить">
            <input type="button" id="to_xlsx" name="to_xlsx" value="Выгрузить в Excel">
        </div>
    </div>
</div>

<#include "../footer.ftl">