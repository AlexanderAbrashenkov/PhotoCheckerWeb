<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#include "../header.ftl">

<div class="report_title">Акции по сетям</div>

<div class="paramTableContainer">

    <table>
        <thead>
        <tr>
            <th style="width:150px">Название сети</th>
            <th style="width:80px">ID сети</th>
            <th style="width:150px">Начало акции</th>
            <th style="width:150px">Конец акции</th>
            <th style="width:100px">Формат</th>
            <th style="width:100px">Товарная группа</th>
            <th style="width:80px">Количество акц. SKU</th>
            <th style="width: 400px">Комментарий</th>
            <th style="width: 80px">Удаление</th>
        </tr>
        </thead>
        <#list nkaTmaMap?keys as key>
        <tbody data-value="${nkaMap[key]?string["0"]}">
            <tr>
                <td rowspan="${nkaTmaMap[key]?size + 2}"><span class="nkaName">${key}</span></td>
                <td rowspan="${nkaTmaMap[key]?size + 2}"><span class="nkaId">${nkaMap[key]?string["0"]}</span></td>
            </tr>
            <#list nkaTmaMap[key] as nkaTma>
                <tr>
                    <td><input class="startDate" type="date" value="${nkaTma.startDate}"></td>
                    <td><input class="endDate" type="date" value="${nkaTma.endDate}"></td>
                    <td>
                        <select class="selFormat">
                            <#list formatTypeList as formatType>
                                <option data-val="${formatType.id}" <#if formatType.id == nkaTma.formatType.id>selected="selected"</#if> >${formatType.name}</option>
                            </#list>
                        </select>
                    </td>
                    <td>
                        <select class="selTg">
                            <#list tgList as tg>
                                <option <#if tg == nkaTma.tgName>selected="selected"</#if> >${tg}</option>
                            </#list>
                        </select>
                    </td>
                    <td><input type="text" class="skuCount" value="${nkaTma.skuCount}"></td>
                    <td><input type="text" class="comment" value="${nkaTma.comment}"></td>
                    <td class="removeSection">Удалить</td>
                </tr>
            </#list>
            <tr>
                <td class="addSection" colspan="7">Добавить</td>
            </tr>
        </tbody>
        </#list>
    </table>

    <div class="button button_save" id="nka_tma_save">Сохранить изменения</div>
    <div class="button button_cancel" id="nka_tma_cancel">Отменить изменения</div>

</div>


<#include "../footer.ftl">