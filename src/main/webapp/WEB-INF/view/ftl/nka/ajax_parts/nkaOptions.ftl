<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете сеть -- </option>
<#list nkaList as nka>
    <option data-value="${nka.id?string["0"]}">
        <#if nka.id gt 0>
            ${nka.name}
        <#else>
            Без названия
        </#if>
    </option>
</#list>