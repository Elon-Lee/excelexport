
select distinct b.brid as "患者编号", floor(MONTHS_BETWEEN(sysdate, e.birthday) / 12) as "年龄",
    case e.sexcode when '1' then '男' else '女' end as "性别",
    e.address as "住址" ,
    c.zdmc as "诊断名称",
    c.icd10 as "icd10编码",
    to_char(c.zdsj, 'yyyy-mm-dd') as "就诊时间", 
    (select t.officename from sys_office t where t.id = d.ksdm) as "就诊科室", 
        (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,19,20,21,22,35,36,37,38,72,73,75)) as "门诊总费用",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (6,72)) as "诊疗费" ,
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (2,3,4)) as "药品费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (2)) as "西药费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (3)) as "中成药费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (4)) as "中草药费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (35)) as "挂号费",
    0 as "诊察费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (5,20,73)) as "检查费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (8)) as "手术费",
    (select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (9)) as "化验费",
    ( select sum(t.zjje) zjje  from  ms_sfmx t where t.mzxh =h.mzxh and t.sfxm in (1,10,11,12,13,14,15,16,17,19,21,22,36,37,38,75)) as "其他费用",
    
    case b.brxz when 13 then '门诊城乡统筹' 
      when 14 then '城职门特'
      when 15 then '城乡门特'
      when 1000 then '自费'
      when 6021 then '城职医保'
      when 6031 then '城乡居保'
      when 6061 then '政府记账'
      when 6071 then '政府记账' end as "参保类型" ,
    
    (select sum(t.fkje) from ms_fkxx  t where t.mzxh =h.mzxh   and t.fkfs =68) as "保险统筹基金支付费用",
    (select sum(t.fkje) from ms_fkxx  t where t.mzxh =h.mzxh   and t.fkfs =21) as  "个人账户支付费用", 
    (select sum(t.fkje) from ms_fkxx  t where t.mzxh =h.mzxh   and t.fkfs =1) as "患者账户支付费用",
    0 as "医疗救助负担"

    from ms_ghmx a, ms_brda b, ys_mz_jzls d, ms_brzd c, mpi_demographicinfo e , ms_mzxx h 
    where a.brid = b.brid
    and a.sbxh = d.ghxh
    and d.jzxh = c.jzxh
    and b.empiid = e.empiid 
    and a.thbz = 0
    and a.sbxh = h.ghgl
    and d.kssj >= (select trunc(add_months(sysdate,-12),'year') from dual)
    and d.kssj <=(select trunc(sysdate,'year')-1 from dual)
    and a.jgid = :jgid
    order by "就诊时间"  
 
  