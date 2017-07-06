<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете rjkam -- </option>
<#list rjkamList as rjkam>
    <option data-value="${rjkam.id?string["0"]}">
        <#if rjkam.id gt 0>
            ${rjkam.name}
        <#else>
            Без имени
        </#if>
    </option>
</#list>