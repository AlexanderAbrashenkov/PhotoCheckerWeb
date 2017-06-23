<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете область -- </option>
<#list nstOblList as nstObl>
    <option data-value="${nstObl.id?string["0"]}">${nstObl.name}</option>
</#list>