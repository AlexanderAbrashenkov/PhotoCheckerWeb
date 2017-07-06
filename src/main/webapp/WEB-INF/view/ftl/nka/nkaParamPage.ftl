<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#include "../header.ftl">

<div class="report_title">Редактирование параметров оценки</div>

<div class="paramTableContainer">
<table>
    <thead>
        <tr>
            <th style="width:150px">Название сети</th>
            <th style="width:80px">ID сети</th>
            <th style="width:100px">Товарная группа</th>
            <th style="width:180px">Группа оценки</th>
            <th style="width:250px">Краткое наименование</th>
            <th style="width: 400px">Полное наименование</th>
        </tr>
    </thead>
    <#list paramList as nkaParam>
    <tbody data-value="${nkaParam.nkaId?string["0"]}">
        <tr>
            <td rowspan="9"><span class="nkaName">${nkaParam.nkaName}</span></td>
            <td rowspan="9"><span class="nkaId">${nkaParam.nkaId?string["0"]}</span></td>
            <td rowspan="3">Майонез</td>
            <td>Доля полки</td>
            <td><input type="text" class="mzDpShortInput" value="${nkaParam.mzDpShort}"></td>
            <td><input type="text" class="mzDpFullInput" value="${nkaParam.mzDpFull}"></td>
        </tr>
        <tr>
            <td>Бренд-блок</td>
            <td><input type="text" class="mzBbShortInput" value="${nkaParam.mzBbShort}"></td>
            <td><input type="text" class="mzBbFullInput" value="${nkaParam.mzBbFull}"></td>
        </tr>
        <tr>
            <td>Место размещения</td>
            <td><input type="text" class="mzMrShortInput" value="${nkaParam.mzMrShort}"></td>
            <td><input type="text" class="mzMrFullInput" value="${nkaParam.mzMrFull}"></td>
        </tr>
        <tr>
            <td rowspan="3">Кетчуп</td>
            <td>Доля полки</td>
            <td><input type="text" class="kDpShortInput" value="${nkaParam.kDpShort}"></td>
            <td><input type="text" class="kDpFullInput" value="${nkaParam.kDpFull}"></td>
        </tr>
        <tr>
            <td>Бренд-блок</td>
            <td><input type="text" class="kBbShortInput" value="${nkaParam.kBbShort}"></td>
            <td><input type="text" class="kBbFullInput" value="${nkaParam.kBbFull}"></td>
        </tr>
        <tr>
            <td>Место размещения</td>
            <td><input type="text" class="kMrShortInput" value="${nkaParam.kMrShort}"></td>
            <td><input type="text" class="kMrFullInput" value="${nkaParam.kMrFull}"></td>
        </tr>
        <tr>
            <td rowspan="3">Соус</td>
            <td>Доля полки</td>
            <td><input type="text" class="sDpShortInput" value="${nkaParam.sDpShort}"></td>
            <td><input type="text" class="sDpFullInput" value="${nkaParam.sDpFull}"></td>
        </tr>
        <tr>
            <td>Бренд-блок</td>
            <td><input type="text" class="sBbShortInput" value="${nkaParam.sBbShort}"></td>
            <td><input type="text" class="sBbFullInput" value="${nkaParam.sBbFull}"></td>
        </tr>
        <tr>
            <td>Место размещения</td>
            <td><input type="text" class="sMrShortInput" value="${nkaParam.sMrShort}"></td>
            <td><input type="text" class="sMrFullInput" value="${nkaParam.sMrFull}"></td>
        </tr>
    </tbody>
    </#list>
</table>

<div class="button button_save" id="nka_param_save">Сохранить изменения</div>
<div class="button button_cancel" id="nka_param_cancel">Отменить изменения</div>

</div>


<#include "../footer.ftl">