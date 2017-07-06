<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете формат -- </option>
<#list nstFormatList as nstFormat>
    <option data-value="${nstFormat.id?string["0"]}">${nstFormat.name}</option>
</#list>