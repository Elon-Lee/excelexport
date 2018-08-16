select 
t.jgid,
t.zyh as "患者编号", 
trunc((trunc(sysdate,'YYYY') - trunc(t.csny,'YYYY'))/365 ) as "年龄",
to_char(t.csny,'yyyy-mm-dd') as "出生日期",
decode(t.brxb,'1','男','2','女') as "性别",
to_char(t.ryrq,'yyyy-mm-dd') as "入院日期",
to_char(t.cyrq,'yyyy-mm-dd') as "出院日期",
trunc( t.cyrq -t.ryrq  ) as "实际住院天数",
(select ksmc from gy_ksdm where gy_ksdm.KSDM = t.brks) as "入院科室",
(select ksmc from gy_ksdm where gy_ksdm.KSDM = t.brks) as "出院科室",
(select jbbm from emr_zyzdjl where emr_zyzdjl.zdlb = '45'  and rownum<2
and emr_zyzdjl.jzxh = t.zyh) as "损伤、中毒的外部原因",
(select jbmc from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.jzxh = t.zyh and emr_zyzdjl.zzbz = '1'  and rownum < 2) as "首要诊断-疾病名称",
(select jbbm from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.jzxh = t.zyh and emr_zyzdjl.zzbz = '1' and rownum < 2 ) as "首要诊断-ICD-10编码" , 
(
select  jbmc from(
(
select jzxh,jbmc , rank() over ( partition by emr_zyzdjl.jzxh order by jlxh  desc  ) as nu  from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.zzbz = 0 
) 
)zdjl where zdjl.jzxh =t.zyh   and nu = 1 
)
as "其他诊断1-疾病名称",
(
select  jbbm from(
(
select jzxh,jbbm , rank() over ( partition by emr_zyzdjl.jzxh order by jlxh  desc  ) as nu  from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.zzbz = 0 
) 
)zdjl where zdjl.jzxh =t.zyh   and nu = 1 
)
as "其他诊断1-ICD-10编码",

--诊断2
(
select  jbmc from(
(
select jzxh,jbmc , rank() over ( partition by emr_zyzdjl.jzxh order by jlxh  desc  ) as nu  from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.zzbz = 0 
) 
)zdjl where zdjl.jzxh =t.zyh   and nu = 2 
)
as "其他诊断2-疾病名称",
(
select  jbbm from(
(
select jzxh,jbbm , rank() over ( partition by emr_zyzdjl.jzxh order by jlxh  desc  ) as nu  from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.zzbz = 0 
) 
)zdjl where zdjl.jzxh =t.zyh   and nu = 2 
)
as "其他诊断2-ICD-10编码",
--其它诊断3

--诊断2
(
select  jbmc from(
(
select jzxh,jbmc , rank() over ( partition by emr_zyzdjl.jzxh order by jlxh  desc  ) as nu  from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.zzbz = 0 
) 
)zdjl where zdjl.jzxh =t.zyh   and nu = 3
)
as "其他诊断3-疾病名称",
(
select  jbbm from(
(
select jzxh,jbbm , rank() over ( partition by emr_zyzdjl.jzxh order by jlxh  desc  ) as nu  from emr_zyzdjl where emr_zyzdjl.zdlb = '51'
and emr_zyzdjl.zzbz = 0 
) 
)zdjl where zdjl.jzxh =t.zyh   and nu = 3
)
as "其他诊断3-ICD-10编码",
t3.fyhj as "住院总费用",
t4."药品费" ,
t4."治疗费",
t4."西药" ,
t4."中成药",
t4."中草药" ,
t4."床位费" , 
t4."诊察费",
t4."检查费",
t4."手术费",
t4."化验费",
t4."护理费",
t4. "其他费", 
decode(t.brxz,'6021','城职医保','6031','城乡医保','其它')as "参保类型",
0 as "医疗救助负担", 
decode(t5."患者自付费用",null,t3.fyhj,t5."患者自付费用") as "患者自付费用",
t5."个人账户支付", 
t5."保险统筹基金支付费用" 
from zy_brry t 
inner join  zy_zyjs t3 
on t.zyh = t3.zyh 
and t.jscs = t3.jscs 
inner join 
(
select  
sum( sfxm.xyf) +  sum( sfxm.zcyf) + sum(sfxm.cyf) as "药品费" ,
sum(sfxm.zlf) as "治疗费",
sum(sfxm.xyf) as "西药" ,
sum(sfxm.zcyf )as "中成药",
sum(sfxm.cyf) as "中草药" ,
sum(sfxm.cwf) as "床位费" , 
sum(sfxm.zcf) as "诊察费",
sum(sfxm.jcf) as "检查费",
sum(sfxm.ssf) as "手术费",
sum(sfxm.hyf) as "化验费",
sum(sfxm.hlf) as "护理费",
sum(sfxm.qtf) as "其他费",
jscs,zyh
from (
select
zyh,jscs,
			 '' as ypf, 
			 decode( g.fyxm , '1', g.zjje ,  0) as cwf, 
			 decode( g.fyxm , '2', g.zjje ,  0) as xyf,
			 decode( g.fyxm , '3', g.zjje ,  0) as zcyf,
			 decode( g.fyxm , '4', g.zjje ,  0) as cyf, 
			 decode(g.fyxm , 6, g.zjje ,  0) as ZLF,
			 decode( g.fyxm , 35, g.zjje ,  36,g.zjje,0) as zcf,  
			 decode( g.fyxm , '5', g.zjje  , '73', g.zjje , '20', g.zjje ,  0) as jcf, 
			 decode( g.fyxm , '8', g.zjje ,  0) as ssf,
			 decode( g.fyxm , '9', g.zjje ,  0) as hyf,   
			 decode( g.fyxm , '18', g.zjje ,  0) as hlf,
			 decode( g.fyxm 
			  , '7', g.zjje  
			 , '15', g.zjje  
			 , '16', g.zjje 
			 , '17', g.zjje 
			 , '22', g.zjje
			 , '10', g.zjje
			 , '11', g.zjje
			 , '12', g.zjje
			 , '13', g.zjje
			 , '14', g.zjje
			 , '15', g.zjje
			 , '16', g.zjje
			 , '17', g.zjje
			 , '19', g.zjje
			 , '21', g.zjje
			 , '22', g.zjje
			 , '37', g.zjje
			 , '71', g.zjje 
			 , '72', g.zjje ,  0) as qtf 
from  zy_jsmx g ) sfxm
group by zyh ,jscs 
) t4  
on t.zyh = t4.zyh  
and t.jscs = t4.jscs 
left join( 
select 
f.zyh , f.zyjscs ,
0 as "医疗救助负担", 
max(f.xjjqtzf) as "患者自付费用",
max(f.grzhzfze) as "个人账户支付", 
max(f.sbjjzfze) as "保险统筹基金支付费用" 
from yb_js02  f where mzzy = 2 
group by    f.zyh , f.zyjscs    
) t5
on t.zyh = t5.zyh 
and t.jscs = t5.zyjscs
where 
t.ryrq   >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') 
and 
t.ryrq   <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')
and
t.ryrq >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') 
and 
t.ryrq   <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')
and  t.jgid = ?
