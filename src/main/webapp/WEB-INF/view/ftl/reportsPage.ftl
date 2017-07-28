<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#include "header.ftl" />

<div id="rightColumn">
    <div id="reports" class="content">
        <div id="class_top" class="reports visible">
            <div class="title">Просмотр фотографий</div>

            <#assign repTypes = Session.user.reportsIndexes>

            <#if repTypes?seq_contains(1)>
                <div class="report_group">
                    <div class="group_title">1. Локальные сети: ДМП</div>
                    <div class="report_link"><a href="/reports/lkaDmp">Просмотр фото</a> </div>
                </div>
            </#if>

            <#if repTypes?seq_contains(2)>
                <div class="report_group">
                    <div class="group_title">2. Федеральные сети: фотоотчеты MLKA</div>
                    <div class="report_link"><a href="/reports/mlka">Просмотр фото</a> </div>
                    <#if Session.user.role gte 2>
                        <div class="report_link"><a href="/reports/mlkaResp">Распределение ответственных по MLKA</a> </div>
                    </#if>
                </div>
            </#if>

            <#if repTypes?seq_contains(3)>
                <div class="report_group">
                    <div class="group_title">3. Федеральные сети: RJKAM</div>
                    <div class="report_link"><a href="/reports/nka">Просмотр фото</a> </div>
                    <div class="report_link"><a href="/reports/nka_param">Параметры оценки</a> </div>
                    <div class="report_link"><a href="/reports/nka_tma_param">Акции по сетям</a> </div>
                </div>
            </#if>

            <#if repTypes?seq_contains(4)>
                <div class="report_group">
                    <div class="group_title">4. Фото НСТ</div>
                    <div class="report_link"><a href="/reports/nst">Просмотр фото</a> </div>
                    <#if Session.user.role gte 2>
                        <div class="report_link"><a href="/reports/nstResp">Распределение ответственных</a> </div>
                        <div class="report_link"><a href="/reports/nstUpload">Загрузить фотографии</a> </div>
                    </#if>
                </div>
            </#if>

            <#if repTypes?seq_contains(5) || repTypes?seq_contains(6)>
                <div class="report_group">
                    <div class="group_title">5. Локальные сети</div>
                    <#if repTypes?seq_contains(5)>
                        <div class="report_link"><a href="/reports/lka">Просмотр фото</a> </div>
                    </#if>
                    <#if repTypes?seq_contains(6)>
                        <div class="report_link"><a href="/reports/lka_ma">Фото от маркетинговых агенств</a> </div>
                    </#if>
                    <#if Session.user.role gte 2>
                        <div class="report_link"><a href="/reports/lka_criteria">Критерии по сетям</a> </div>
                    </#if>
                </div>
            </#if>

            <#if Session.user.role gte 2 && (repTypes?seq_contains(1) || repTypes?seq_contains(2) || repTypes?seq_contains(5) || repTypes?seq_contains(5))>
                <div class="report_group">
                    <div class="group_title">Загрузка данных</div>
                    <div class="report_link"><a href="/reports/upload">Загрузить</a> </div>
                </div>
            </#if>

            <#if Session.user.role gte 2 && (repTypes?seq_contains(1) || repTypes?seq_contains(2) || repTypes?seq_contains(5) || repTypes?seq_contains(5))>
                <div class="report_group">
                    <div class="group_title">Администрирование</div>
                    <#if Session.user.role == 4>
                        <div class="report_link"><a href="/reports/create_user">Добавление пользователя</a> </div>
                    </#if>
                    <div class="report_link"><a href="/reports/responsib">Распределение ответственных</a> </div>
                </div>
            </#if>
        </div>
    </div>
</div>

<#include "footer.ftl" />