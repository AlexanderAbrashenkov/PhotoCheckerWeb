<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете регион -- </option>
<#list regionList as region>
    <option data-value="${region.id?string["0"]}">${region.name}</option>
</#list>