select fphm,
brbh as 患者编号,
mzxh,
age as  年龄,
csny as 出生年月,
brxb as 性别,
zdmc as 诊断名称,
icd10 as ICD10,
kssj as 就诊日期,
jzks as 就诊科室,
zjje as 门诊总费用,
zlf as 治疗费,
BRXZ as 参保类型,
(max(xyf) +  max(zcyf) + max(cyf)) as 药品费, 
max(xyf) as 西药,
max(zcyf) as 中成药,
max(cyf) as 中草药,
max(ghf) as 挂号费,
max(zcf) as 诊察费,
max(jcf) as 检查费,
max(ssf) as 手术费,
max(hyf) as 化验费,
max(qtf) as 其他费,
max(jjzf) as 保险统筹基金支付费用,
max(zhzf) as 个人账户支付费用,
max(zffy) as 患者自付费用 ,
'' as 医疗救助负担

  from (
        select distinct f.fphm,a.brbh,f.mzxh,
                         (select EXTRACT(year FROM a.KSSJ) - EXTRACT(year FROM b.CSNY) years from dual) as AGE,
                         b.CSNY as CSNY,
                         case
                           when b.BRXB = 1 then '男'
   when b.BRXB = 2 then '女'
   when b.BRXB = 9 then '未说明的性别'  
   else '未知的性别'  end BRXB,
 c.ZDMC as ZDMC,
 c.icd10 as icd10,
 a.KSSJ as KSSJ,
 (select offi.officename from SYS_Office offi where offi.id = d.JZKS) as JZKS,
  f.zjje,
 decode( g.sfxm , 6, g.zjje , '14', g.zjje ,'19', g.zjje  ,'21', g.zjje , '71', g.zjje ,  0) as ZLF, 
 decode( g.sfxm , '2', g.zjje ,  0) as xyf,
 decode( g.sfxm , '3', g.zjje ,  0) as zcyf,
 decode( g.sfxm , '4', g.zjje ,  0) as cyf, 
  decode( g.sfxm , '35', g.zjje ,  0) as ghf,     
 decode( g.sfxm ,'36',g.zjje,0) as zcf, 
 decode( g.sfxm , '5', g.zjje  , '73', g.zjje , '20', g.zjje ,  0) as jcf, 
 decode( g.sfxm , '8', g.zjje ,  0) as ssf,
 decode( g.sfxm , '9', g.zjje ,  0) as hyf, 
 decode( g.sfxm , '15', g.zjje  , '16', g.zjje , '17', g.zjje , '22', g.zjje ,
 '1', g.zjje  , '7', g.zjje , '10', g.zjje , '11', g.zjje ,
 '12', g.zjje  , '13', g.zjje , '18', g.zjje ,
 '37', g.zjje , '72', g.zjje ,0) as qtf, 
 (select brxz.xzmc from GY_BRXZ brxz where brxz.mzsy = 1 and brxz.brxz = f.BRXZ) as BRXZ ,
  decode( h.fkfs , '68',h.fkje,  0) as jjzf,
  decode( h.fkfs , '21', h.fkje  , '22' ,h.fkje,  0) as zhzf,
  decode(h.fkfs , '1', h.fkje, 0) as zffy
                 
 
   from YS_MZ_JZLS a,
        MS_BRDA    b,
        MS_BRZD    c,
        MS_BCJL    d,
        MS_GHMX    e,
        MS_MZXX    f,
        Ms_Sfmx    g ,
        ms_fkxx    h
        
  where a.brbh = b.brid
    and a.jzxh = c.jzxh
    and a.jzxh = d.jzxh
    and a.ghxh = e.sbxh
    and b.brid = f.brid
    and e.sbxh = f.ghgl
    and f.mzxh = g.mzxh 
    and f.mzxh = h.mzxh
    and f.zfpb != 1
    and c.ZZBZ = '1'
and e.THBZ != 1
and a.KSSJ >= to_date(?, 'yyyy-mm-dd hh24:mi:ss')
and a.JSSJ <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')
and f.sfrq >= to_date(?, 'yyyy-mm-dd hh24:mi:ss')
and f.sfrq <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')
           and a.jgid = ?
) ttt 
group by fphm,brbh,mzxh,age,csny,brxb,zdmc,icd10,kssj,jzks,zjje,zlf, BRXZ
 