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
        <tbody data-value="${key}">
            <tr>
                <td rowspan="${nkaTmaMap[key]?size + 2}"><span class="nkaName">${nkaTmaMap[key]}</span></td>
                <td rowspan="${nkaTmaMap[key]?size + 2}"><span class="nkaId">${key}</span></td>
            </tr>
            <#list nkaTmaMap[key] as nkaTma>
                <tr>
                    <td><input class="startDate" type="date"></td>
                    <td><input class="endDate" type="date"></td>
                    <td>
                        <select class="selFormat">
                            <#list formatTypeList as formatType>
                                <option data-val="${formatType.id}" <#if formatType.id == nkaTma.formatType.id>selected="selected"</#if> >${formatType.name}</option>
                            </#list>
                        </select>
                    </td>
                    <td>
                        <select class="selTg">
                            <option>Майонез</option>
                            <option>Кетчуп</option>
                            <option>Соус</option>
                        </select>
                    </td>
                    <td><input type="text" class="skuComment" value="${nkaTma.skuCount}"></td>
                    <td><input type="text" class="comment" value="${nkaTma.comment}"></td>
                    <td style="background-color: orange;">Удалить</td>
                </tr>
            </#list>
        </#if>
        <tr>
            <td rowspan="${nkaTmaMap[key]?size + 1}"><span class="nkaName">${nkaTmaMap[key]}</span></td>
            <td rowspan="5"><span class="nkaId">2669</span></td>
            <td><input type="date"></td>
            <td><input type="date"></td>
            <td>
                <select>
                    <option>ГМ</option>
                    <option>СМ</option>
                    <option>ММ</option>
                </select>
            </td>
            <td>
                <select>
                    <option>Майонез</option>
                    <option>Кетчуп</option>
                    <option>Соус</option>
                </select>
            </td>
            <td><input type="text" value="1"></td>
            <td><input type="text" class="mzDpFullInput" value="фыа ыв пывпыв пы"></td>
            <td style="background-color: orange;">Удалить</td>
        </tr>
        <tr>
            <td><input type="date"></td>
            <td><input type="date"></td>
            <td>
                <select>
                    <option>ГМ</option>
                    <option>СМ</option>
                    <option>ММ</option>
                </select>
            </td>
            <td>
                <select>
                    <option>Майонез</option>
                    <option>Кетчуп</option>
                    <option>Соус</option>
                </select>
            </td>
            <td><input type="text" value="1"></td>
            <td><input type="text" class="mzDpFullInput" value="ывпы ичси чси чси"></td>
            <td style="background-color: orange;">Удалить</td>
        </tr>
        <tr>
            <td><input type="date"></td>
            <td><input type="date"></td>
            <td>
                <select>
                    <option>ГМ</option>
                    <option>СМ</option>
                    <option>ММ</option>
                </select>
            </td>
            <td>
                <select>
                    <option>Майонез</option>
                    <option>Кетчуп</option>
                    <option>Соус</option>
                </select>
            </td>
            <td><input type="text" value="1"></td>
            <td><input type="text" class="mzDpFullInput" value="цуе ц цуецу еуц"></td>
            <td style="background-color: orange;">Удалить</td>
        </tr>
        <tr>
            <td><input type="date"></td>
            <td><input type="date"></td>
            <td>
                <select>
                    <option>ГМ</option>
                    <option>СМ</option>
                    <option>ММ</option>
                </select>
            </td>
            <td>
                <select>
                    <option>Майонез</option>
                    <option>Кетчуп</option>
                    <option>Соус</option>
                </select>
            </td>
            <td><input type="text" value="1"></td>
            <td><input type="text" class="mzDpFullInput" value="рва тап ппаропа"></td>
            <td style="background-color: orange;">Удалить</td>
        </tr>
        <tr>
            <td colspan="7" style="background-color: lightgreen;">Добавить</td>
        </tr>
        </tbody>
    </table>

    <div class="button button_save" id="nka_param_save">Сохранить изменения</div>
    <div class="button button_cancel" id="nka_param_cancel">Отменить изменения</div>

</div>


<#include "../footer.ftl">