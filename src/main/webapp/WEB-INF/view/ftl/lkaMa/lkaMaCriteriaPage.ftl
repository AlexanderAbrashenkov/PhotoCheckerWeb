<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#include "../header.ftl">

<div class="report_title">Редактирование критериев по сетям</div>
<div id="lka_crit_container">
    <div class="lka_criteria_title">
        <div class="crit lka_name">Название сети</div>
        <div class="crit lka_id">ID сети</div>
        <div class="crit crit_name crit1_name">Критерий 1</div>
        <div class="crit crit_value crit1_mz">Майонез</div>
        <div class="crit crit_value crit1_k">Кетчуп</div>
        <div class="crit crit_value crit1_s">Соус</div>
        <div class="crit crit_value crit1_m">Масло</div>
        <div class="crit crit_name crit2_name">Критерий 2</div>
    </div>
    <div id="criteria_editor_pane">
        <#list critList as critItem>
            <div class="lka_criteria">
                <input type="text" class="crit lka_name" value="${critItem.lka.name}" readonly>
                <input type="text" class="crit lka_id" value="${critItem.lka.id?string["0"]}">
                <input type="text" class="crit crit_name crit1_name" value="${critItem.crit1Name}">
                <input type="text" class="crit crit_value crit1_mz" value="${critItem.crit1Mz}">
                <div class="crit perc">%</div>
                <input type="text" class="crit crit_value crit1_k" value="${critItem.crit1K}">
                <div class="crit perc">%</div>
                <input type="text" class="crit crit_value crit1_s" value="${critItem.crit1S}">
                <div class="crit perc">%</div>
                <input type="text" class="crit crit_value crit1_m" value="${critItem.crit1M}">
                <div class="crit perc">%</div>
                <input type="text" class="crit crit_name crit2_name" value="${critItem.crit2Name}">
                <img class="remove_row" src="<@spring.url "/resources/images/cancel.png"/>">
            </div>
        </#list>
        <img class="add_row" src="<@spring.url "/resources/images/add.png"/>">
    </div>
    <div class="button button_save" id="lka_crit_save">Сохранить изменения</div>
    <div class="button button_cancel" id="lka_crit_cancel">Отменить изменения</div>
</div>

<#include "../footer.ftl">