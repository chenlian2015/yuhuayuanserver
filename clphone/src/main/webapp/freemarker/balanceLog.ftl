<#list balanceLog as bl>  
id=${bl.id}
yuhuayuanid=${bl.yuhuayuanid}
balancenum=${bl.balancenum}
manager=${bl.manager}
balancecode=${bl.balancecode}
otherInfo=
<#if bl.otherinfo??>
haha otherinfo has value!
</#if>
<br/>
<br/>
</#list> 