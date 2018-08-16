
select  
a.idcard as "身份证号",
a.personname as "姓名",
a.mobilenumber as "联系电话",
(select  org.organizname from  sys_organization org  where  jgid  = substr(b.manaunitid,1,9) )  as "建档机构",
to_char(b.createdate,'yyyy-mm-dd') as "建档日期",
to_char(a.birthday,'yyyy') as "出生年份",
to_char(a.birthday,'MM') as "出生月份",
(select dicvalue from phis_dictionary where dicid = 'MZDM' and diccode = a.nationcode) as "民族",
(select dicvalue from phis_dictionary where dicid = 'WHCDDM' and diccode = educationCode) as "文化程度",
(select dicvalue from phis_dictionary where dicid = 'ZYDM' and diccode = a.workCode)  as "职业",
(select dicvalue from phis_dictionary where dicid = 'HYZK' and diccode = a.maritalStatusCode)  as "婚姻状况", 
(select dicvalue from phis_dictionary where dicid = 'YLFKFS' and diccode = a.insuranceCode)  as "医疗费用支付方式",
(select  diseasetext from  ehr_pasthistory pas where  pas.pastHisTypeCode = '12' and pas.empiid = a.empiid
 and confirmdate is not null
) as "暴露史",
jbs1."既往疾病名称" as "既往疾病名称1",
jbs1."确诊年份" as "确诊年份1",
jbs1."确诊月份" as "确诊月份1", 
jbs2."既往疾病名称" as "既往疾病名称2",
jbs2."确诊年份" as "确诊年份2",
jbs2."确诊月份" as "确诊月份2", 
jbs3."既往疾病名称" as "既往疾病名称3",
jbs3."确诊年份" as "确诊年份3",
jbs3."确诊月份" as "确诊月份3", 
jbs4."既往疾病名称" as "既往疾病名称4",
jbs4."确诊年份" as "确诊年份4",
jbs4."确诊月份" as "确诊月份4",
sss1."手术史名称" as "手术史名称1", 
sss1."手术时间" as "手术时间1",
sss2."手术史名称" as "手术史名称2", 
sss2."手术时间" as "手术时间2",
wss1."外伤史" as "外伤史1", 
wss1."外伤时间" as "外伤时间1",
wss2."外伤史" as "外伤史2", 
wss2."外伤时间" as "外伤时间2",
sxs1."输血史" as "输血史1", 
sxs1."输血时间" as "输血时间1",
sxs2."输血史" as "输血史2", 
sxs2."输血时间" as "输血时间2",
jzs_fq1."家族史-父亲" as "家族史-父亲1",
jzs_fq2."家族史-父亲" as "家族史-父亲2",
jzs_fq3."家族史-父亲" as "家族史-父亲3",
jzs_mq1."家族史-母亲" as "家族史-母亲1",
jzs_mq2."家族史-母亲" as "家族史-母亲2",
jzs_mq3."家族史-母亲" as "家族史-母亲3",
jzs_xm1."家族史-兄妹" as "家族史-兄妹1",
jzs_xm2."家族史-兄妹" as "家族史-兄妹2",
jzs_xm3."家族史-兄妹" as "家族史-兄妹3",
jzs_zn1."家族史-子女" as "家族史-子女1",
jzs_zn2."家族史-子女" as "家族史-子女2",
jzs_zn3."家族史-子女" as "家族史-子女3",
ycs."遗传史" as "遗传史",
cjs1."残疾" as  "残疾1",
cjs2."残疾" as  "残疾2",
cjs3."残疾" as  "残疾3", 
(select dicvalue from phis_dictionary where dicid = 'CFPFSS' and diccode = c.kitchenVentilation) as "厨房排风设施",
(select dicvalue from phis_dictionary where dicid = 'RLLX' and diccode = c.fuelType) as "燃料类型",
(select dicvalue from phis_dictionary where dicid = 'YSLX' and diccode = c.water) as "饮水类型",
(select dicvalue from phis_dictionary where dicid = 'CSLX' and diccode = c.bathroom) as "厕所类型",
(select dicvalue from phis_dictionary where dicid = 'QXL' and diccode = c.livestockRailing) as "禽畜栏类型"

from  mpi_demographicinfo a ,
ehr_healthrecord b 
/**疾病史*/
left join 
(
select 
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "既往疾病名称",
to_char(jbs1.confirmdate,'yyyy') as "确诊年份",
to_char(jbs1.confirmdate,'MM') as "确诊月份"   from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '02'  and jbs1.confirmdate is not null 
)jbs1
on
b.empiid = jbs1.empiid 
and jbs1.rn = 1 

left join  
(
select 
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "既往疾病名称",
to_char(jbs1.confirmdate,'yyyy') as "确诊年份",
to_char(jbs1.confirmdate,'MM') as "确诊月份"   from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '02'  and jbs1.confirmdate is not null 
)jbs2
on
b.empiid = jbs2.empiid 
and jbs2.rn = 2 

left join  
(
select 
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "既往疾病名称",
to_char(jbs1.confirmdate,'yyyy') as "确诊年份",
to_char(jbs1.confirmdate,'MM') as "确诊月份"   from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '02'  and jbs1.confirmdate is not null 
)jbs3
on
b.empiid = jbs3.empiid 
and jbs2.rn = 3


left join  
(
select 
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "既往疾病名称",
to_char(jbs1.confirmdate,'yyyy') as "确诊年份",
to_char(jbs1.confirmdate,'MM') as "确诊月份"   from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '02'  and jbs1.confirmdate is not null 
)jbs4
on
b.empiid = jbs4.empiid 
and jbs4.rn = 4

/**手术记录*/
left join  
(
select 
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "手术史名称",
to_char(jbs1.confirmdate,'yyyy-mm-dd') as "手术时间" from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '03'  and jbs1.confirmdate is not null 
)sss1
on
b.empiid = sss1.empiid 
and sss1.rn = 1
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "手术史名称",
to_char(jbs1.confirmdate,'yyyy-mm-dd') as "手术时间" from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '03'  and jbs1.confirmdate is not null 
)sss2
on
b.empiid = sss2.empiid 
and sss1.rn = 2

/**外伤史*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "外伤史",
to_char(jbs1.confirmdate,'yyyy-mm-dd') as "外伤时间" from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '06'  and jbs1.confirmdate is not null 
)wss1
on
b.empiid = wss1.empiid 
and wss1.rn =1

left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "外伤史",
to_char(jbs1.confirmdate,'yyyy-mm-dd') as "外伤时间" from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '06'  and jbs1.confirmdate is not null 
)wss2
on
b.empiid = wss2.empiid 
and wss2.rn =1


/**输血史*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "输血史",
to_char(jbs1.confirmdate,'yyyy-mm-dd') as "输血时间" from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '04'  and jbs1.confirmdate is not null 
)sxs1
on
b.empiid = sxs1.empiid 
and sxs1.rn =1

left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "输血史",
to_char(jbs1.confirmdate,'yyyy-mm-dd') as "输血时间" from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '04'  and jbs1.confirmdate is not null 
)sxs2
on
b.empiid = sxs2.empiid 
and sxs2.rn =2

/**遗传史父亲*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-父亲"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '07'  and jbs1.confirmdate is not null 
)jzs_fq1
on
b.empiid = jzs_fq1.empiid 
and jzs_fq1.rn =1
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-父亲"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '07'  and jbs1.confirmdate is not null 
)jzs_fq2
on
b.empiid = jzs_fq2.empiid 
and jzs_fq2.rn =2
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-父亲"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '07'  and jbs1.confirmdate is not null 
)jzs_fq3
on
b.empiid = jzs_fq3.empiid 
and jzs_fq3.rn =3



/**遗传史母亲*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-母亲"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '08'  and jbs1.confirmdate is not null 
)jzs_mq1
on
b.empiid = jzs_mq1.empiid 
and jzs_mq1.rn =1
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-母亲"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '08'  and jbs1.confirmdate is not null 
)jzs_mq2
on
b.empiid = jzs_mq2.empiid 
and jzs_mq2.rn =2
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-母亲"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '08'  and jbs1.confirmdate is not null 
)jzs_mq3
on
b.empiid = jzs_mq3.empiid 
and jzs_mq3.rn =3


/**遗传史兄妹*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-兄妹"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '09'  and jbs1.confirmdate is not null 
)jzs_xm1
on
b.empiid = jzs_xm1.empiid 
and jzs_xm1.rn =1
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-兄妹"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '09'  and jbs1.confirmdate is not null 
)jzs_xm2
on
b.empiid = jzs_xm2.empiid 
and jzs_xm2.rn =2
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-兄妹"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '09'  and jbs1.confirmdate is not null 
)jzs_xm3
on
b.empiid = jzs_xm3.empiid 
and jzs_xm3.rn =3



/**遗传史子女*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-子女"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '10'  and jbs1.confirmdate is not null 
)jzs_zn1
on
b.empiid = jzs_zn1.empiid 
and jzs_zn1.rn =1
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-子女"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '10'  and jbs1.confirmdate is not null 
)jzs_zn2
on
b.empiid = jzs_zn2.empiid 
and jzs_zn2.rn =2
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "家族史-子女"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '10'  and jbs1.confirmdate is not null 
)jzs_zn3
on
b.empiid = jzs_zn3.empiid 
and jzs_zn3.rn =3

/**遗传史*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "遗传史"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '05'  and jbs1.confirmdate is not null 
)ycs
on
b.empiid = ycs.empiid 
and ycs.rn =1



/**残疾史*/
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "残疾"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '11'  and jbs1.confirmdate is not null 
)cjs1
on
b.empiid = cjs1.empiid 
and cjs1.rn =1
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "残疾"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '11'  and jbs1.confirmdate is not null 
)cjs2
on
b.empiid = cjs2.empiid 
and cjs2.rn =2
left join  
(
select
empiid,
row_number() over ( partition by jbs1.empiid order by PASTHISTORYID asc )as rn,
diseasetext as "残疾"  from  ehr_pasthistory jbs1 
where  jbs1.pastHisTypeCode = '11'  and jbs1.confirmdate is not null 
)cjs3
on
b.empiid = cjs3.empiid 
and cjs3.rn =3

left join ehr_lifestyle c
on b.empiid  = c.empiid 
where a.empiid = b.empiid  
and trunc((trunc(sysdate) - a.birthday)/365) >= 60
order by b.manaunitid