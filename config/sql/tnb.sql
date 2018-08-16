  
select a.personname,a.idcard as "身份证",
to_char(t.visitdate,'yyyy-mm-dd') as "随访日期" ,  
case 
t.visitway when  '1' then '门诊'
when   '2' then '家庭'
when  '3' then '电话'
when  '4' then '短信'
when  '5' then '网络'         
when  '6' then '群组'  else '其他' end  as "随访方式", 
case  when instr(t.symptoms,1,1,1) > 0  then '无症状' else '' end as "症状1",
case  when instr(t.symptoms,2,1,1) > 0  then '多饮' else '' end  as "症状1", 
case  when instr(t.symptoms,3,1,1) > 0  then '多食' else '' end  as "症状2",
case  when instr(t.symptoms,4,1,1) > 0  then '多尿' else '' end  as "症状3",
case  when instr(t.symptoms,5,1,1) > 0  then '视力模糊' else '' end  as "症状4",
case  when instr(t.symptoms,6,1,1) > 0  then '感染' else '' end  as "症状5",
case  when instr(t.symptoms,7,1,1) > 0  then '下肢浮肿' else '' end  as "症状6",
case  when instr(t.symptoms,8,1,1) > 0  then '手脚麻木' else '' end  as  "症状7",
case  when instr(t.symptoms,9,1,1) > 0  then '体重明显下降' else '' end  as "症状8",
case  when instr(t.symptoms,99,1,1) > 0  then '其他' else '' end  as "症状9",
t.pulsation as "足背动脉搏动",
t.weight as "体重(kg)",

t.fbs "空腹血糖" ,
t.hbA1c "糖化血红蛋白(%)",
to_char(t.testDate,'yyyy-mm-dd') "糖化血红蛋白检查日期",
t.smokeCount "吸烟量1(支/天)",
t.drinkCount "饮酒量1(两/天)",
t.trainTimesWeek "运动频率1(次/周)" ,
t.trainMinute "每次运动时间1(min)", 
case  t.medicine when  '1' then '规律'
when   '2' then '间断'
when  '3' then '不服药' 
else '拒绝服药' end  as "服药依从性" ,
case  t.glycopenia when  '1' then '无'
when   '2' then '偶尔'
when  '3' then '频繁' 
else '' end as "低血糖反应" ,
'' as "此次随访分类"
           
         
from MDC_DiabetesVisit t , mpi_demographicinfo a,MDC_DiabetesRecord b 
where t.empiid = a.empiid 
and t.empiid = b.empiid
and b.status = '0'
and trunc((trunc(sysdate) - a.birthday)/365) >= 60  
and t.visitdate >= date'2015-01-01'
and t.visitdate <= date'2017-6-30'
order by t.empiid,t.visitdate 
  