  
select a.personname,a.idcard "身份证",to_char(t.visitdate,'yyyy-mm-dd') as "随访日期" ,  
case 
t.visitway when  '1' then '门诊'
when   '2' then '家庭'
when  '3' then '电话'
when  '4' then '短信'
when  '5' then '网络'         
when  '6' then '群组'  else '其他' end  as "随访方式", 
case  when instr(t.currentsymptoms,1,1,1) > 0  then '头痛头晕' else '' end as "症状1",
case  when instr(t.currentsymptoms,2,1,1) > 0  then '恶心呕吐' else '' end  as "症状1", 
case  when instr(t.currentsymptoms,3,1,1) > 0  then '眼花耳鸣' else '' end  as "症状2",
case  when instr(t.currentsymptoms,4,1,1) > 0  then '呼吸困难' else '' end  as "症状3",
case  when instr(t.currentsymptoms,5,1,1) > 0  then '心悸胸闷' else '' end  as "症状4",
case  when instr(t.currentsymptoms,6,1,1) > 0  then '鼻衄出血不止' else '' end  as "症状5",
case  when instr(t.currentsymptoms,7,1,1) > 0  then '四肢发麻' else '' end  as "症状6",
case  when instr(t.currentsymptoms,8,1,1) > 0  then '下肢水肿' else '' end  as  "症状7",
case  when instr(t.currentsymptoms,9,1,1) > 0  then '无症状' else '' end  as "症状8",
case  when instr(t.currentsymptoms,10,1,1) > 0  then '其他' else '' end  as "症状9",
t.constriction ||'/'|| t.diastolic as "血压",
t.weight "体重" ,
t.Heartrate "心率",
t.smokeCount "吸烟量1(支/天)",
t.drinkCount "饮酒量1(两/天)",
t.trainTimesWeek "运动频率1(次/周)" ,
t.trainMinute "每次运动时间1(min)", 
case  t.salt when  1 then '轻'
when  2 then '中'   else '重' end  as "摄盐情况",
        
        
case  t.medicine when  '1' then '规律'
when   '2' then '间断'
when  '3' then '不服药' 
else '拒绝服药' end  as "服药依从性" ,
'' as "此次随访分类"
           
         
from MDC_HypertensionVisit t , mpi_demographicinfo a,mdc_hypertensionrecord b 
where t.empiid = a.empiid 
and t.empiid = b.empiid
and b.status = '0'
and trunc((trunc(sysdate) - a.birthday)/365) >= 60
and t.visitdate >= date'2015-01-01'
and t.visitdate <= date'2017-6-30'
order by t.empiid,t.visitdate
 