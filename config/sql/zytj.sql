select 

t.jgid as jgid , 
t.bahm as "患者编号",
to_char(t.csny,'yyyy-mm-dd') as "出生日期",
decode(t.brxb , '1','男','2','女') as "性别",
t.lxdz as "现住址",
to_char(t.ryrq,'yyyy-mm-dd') as "入院日期",
to_char(t.cyrq,'yyyy-mm-dd') as "出院日期",
trunc(t.cyrq) - trunc(t.ryrq) as "实际住院天数",
(select  max(ksmc) from gy_ksdm a  ,  
zy_rcjl b where  a.ksdm = b.brks and b.zyh = t.zyh  and b.czlx ='1' ) as "入院科室",
(select max(ksmc) from gy_ksdm a  ,  
zy_rcjl b where  a.ksdm = b.brks and b.zyh = t.zyh  and b.czlx ='-1') as "入=出院科室",
 (select   a.jbmc as WBYY 
          from EMR_ZYZDJL a
         where zdlb = 45
           and rowid = (select max(rowid)
                          from EMR_ZYZDJL b
                         where a.jzxh = t.zyh
                           and b. zdlb = 45)) as "损失、中毒的外部原因",
DECODE(t.ryqk, '1', '危重', '2', '急诊', 3,'一般','4','其他') as "入院情况", --,入院情况 
( 
select max( decode(cyfs , '1','治愈', '2','好转', '3','未愈', '4','死亡','其它') )from zy_cyjl a where a.zyh = 
t.zyh and czlx = 3
) as "出院情况",
'' as "TNM分期",
'' as "检查情况",
decode(t2.lyfs,
'1','医嘱离院',
'2','医嘱转院',
'3','医嘱转社区卫生服务机构/乡镇卫生院',
'4','非医嘱离院',
'5','死亡',
'9','其他' ) as "离院方式" ,
case when  (select count(1) from emr_zyssjl a where a.jlxh = t.zyh) > 0 then '是' 
else '否' end as  "是否手术" ,
  
'' as "抗菌素名称1",
'' as "抗菌素名称2",
'' as "抗菌素名称3",
'' as "抗菌素名称4",
'' as "抗菌素使用情况*",

(
select  jbmc from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL  
 where zzbz = 1   )a
 where a.jzxh = t.zyh and ranknum = 1
) as "首要诊断疾病名称",
(
select  max(jbbm) from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL where    zzbz = 1    )a
 where a.jzxh = t.zyh and ranknum = 1  
) as "首要诊断疾病编码",
(
select  jbmc from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL  
 where zzbz <> 1   )a
 where a.jzxh = t.zyh and ranknum = 1
) as "其它疾病名称1",
(
select  max(jbbm) from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL where    zzbz <> 1    )a
 where a.jzxh = t.zyh and ranknum = 1  
) as "其它疾病编码1",
(
select  jbmc from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL  
 where zzbz <> 1   )a
 where a.jzxh = t.zyh and ranknum = 2
) as "其它疾病名称2",
(
select  max(jbbm) from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL where    zzbz <> 1    )a
 where a.jzxh = t.zyh and ranknum = 2  
) as "其它疾病编码2",
(
select  jbmc from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL  
 where zzbz <> 1   )a
 where a.jzxh = t.zyh and ranknum =3 
) as "其它疾病名称3",
(
select  max(jbbm) from (select zzbz , jzxh ,  rank() over(partition by jzxh order by jlxh asc) as ranknum ,  jbmc , jbbm from EMR_ZYZDJL where    zzbz <> 1    )a
 where a.jzxh = t.zyh and ranknum = 3 
) as "其它疾病编码3",
(
round(decode(t3.YBYLFWF, null, 0, t3.YBYLFWF),2) +
round(decode(t3.YBZLCZF, null, 0, t3.YBZLCZF),2) +
round(decode(t3.QTFY, null, 0, t3.QTFY),2) +
round(decode(t3.HLF, null, 0, t3.HLF),2) +
round(decode(t3.BLZDF, null, 0, t3.BLZDF),2) +
round(decode(t3.SYSZDF, null, 0, t3.SYSZDF),2) +
round(decode(t3.LCZDXMF, null, 0, t3.LCZDXMF),2) +
round(decode(t3.YXXZDF, null, 0, t3.YXXZDF),2) +
round(decode(t3.FSSZLXMF, null, 0, t3.FSSZLXMF),2) +
round(decode(t3.LCWLZLF, null, 0, t3.LCWLZLF),2) +
round(decode(t3.MZF, null, 0, t3.MZF),2) +
round(decode(t3.SSZLF, null, 0, t3.SSZLF),2) +
round(decode(t3.KFF, null, 0, t3.KFF),2) +
round(decode(t3.XF, null, 0, t3.XF),2) +
round(decode(t3.BDBLZPF, null, 0, t3.BDBLZPF),2) +
round(decode(t3.QDBLZPF, null, 0, t3.QDBLZPF),2) +
round(decode(t3.NXYZLZPF, null, 0, t3.NXYZLZPF),2) +
round(decode(t3.XBYZLZPF, null, 0, t3.XBYZLZPF),2) +
round(decode(t3.JCYCLF, null, 0, t3.JCYCLF),2) +
round(decode(t3.ZLYCLF, null, 0, t3.ZLYCLF),2) +
round(decode(t3.SSYCLF, null, 0, t3.SSYCLF),2) +
round(decode(t3.QTF, null, 0, t3.QTF),2) + 
round(decode(t4.xyf, null, 0, t4.xyf),2) +
round(decode(t4.zcyf, null, 0, t4.zcyf),2) +
round(decode(t4.zcy, null, 0, t4.zcy) ,2)
)  as "住院总费用",
nvl(t3.FSSZLXMF,0)  +nvl( t3.mzf,0)  +nvl( t3.SSZLF,0)   as "治疗费",
t3.fsszlxmf as "非手术治疗费",

t3.mzf as "麻醉费",

t3.sszlf as "手术治疗费",

nvl(t4.xyf,0) +nvl( t4.zcyf,0)  +nvl( t4.zcy,0)  as "药品费",

t4.xyf as "西药",

t4.zcyf as "中成药",

t4.zcy as "中草药" , 

nvl(t3.YBYLFWF,0) + 
nvl(t3.YBZLCZF,0)+
nvl(t3.QTFY,0) +
nvl(t3.hlf,0) as "综合医疗服务类",

t3.YBYLFWF as "一般医疗服务费",
t3.YBZLCZF as "一般治疗操作费",
t3.QTFY as "其它费用",
t3.hlf as "护理费",
 
nvl(t3.blzdf,0) + 
nvl(t3.syszdf,0)+
nvl(t3.lczdxmf,0) +
nvl(t3.yxxzdf,0) as "诊断类",
t3.blzdf as "病理诊断费",
t3.syszdf as "实验室诊断费",
t3.lczdxmf as "临床诊断项目费",
t3.yxxzdf as "影像学诊断费",


t3.kff as "康复费",

nvl(t3.xf,0) + nvl(t3.BDBLZPF ,0)+ nvl(t3. QDBLZPF,0)+ nvl(t3.NXYZLZPF,0)
+nvl( t3. XBYZLZPF ,0) as  "血液和血液制品" ,

nvl(t3.JCYCLF,0) +
nvl(t3.ZLYCLF,0) +
nvl(t3.SSYCLF,0)   as "耗材费",

t3.JCYCLF as "检查用一次性医用材料费" , 

t3.ZLYCLF as "治疗用一次性医用材料费",


t3.SSYCLF as "手术用一次性医用材料费",

t3.QTF as "其它费用" ,
(      
 SELECT  sum(YKA107) 
   FROM YB_JS02 a
  WHERE ZFPB = 0
    and MZZY = 2 
 and a.zyh = t.zyh ||''  and a.jgid = t.jgid  )as "保险统筹基金支付费用"  , 
 (      
 SELECT    max(GRZHZFZE)  
   FROM YB_JS02 a
  WHERE ZFPB = 0
    and MZZY = 2 
 and a.zyh = t.zyh ||'' and a.jgid = t.jgid  )as "个人账户支付" ,
    
 (      
 SELECT   max(xjjqtzf)  
   FROM YB_JS02 a
  WHERE ZFPB = 0
    and MZZY = 2 
 and a.zyh = t.zyh||'' and a.jgid = t.jgid  )as "个人自负费用" ,
 '0' as "医疗救助负担"
  
from  zy_brry t  , Emr_Basy t2   , ( 
          select jzxh,
                  brid,
                  jgid,
                  sum(YBYLFWF) as YBYLFWF,
                  sum(YBZLCZF) as YBZLCZF,
                  sum(QTFY) as QTFY,
                  sum(HLF) as HLF,
                  sum(BLZDF) as BLZDF,
                  sum(SYSZDF) as SYSZDF,
                  sum(LCZDXMF) as LCZDXMF,
                  sum(YXXZDF) as YXXZDF,
                  sum(FSSZLXMF) as FSSZLXMF,
                  sum(LCWLZLF) as LCWLZLF,
                  sum(MZF) as MZF,
                  sum(SSF) as SSZLF,
                  sum(KFF) as KFF,
                  sum(XF) as XF,
                  sum(BDBLZPF) as BDBLZPF,
                  sum(QDBLZPF) as QDBLZPF,
                  sum(NXYZLZPF) as NXYZLZPF,
                  sum(XBYZLZPF) as XBYZLZPF,
                  sum(JCYCLF) as JCYCLF,
                  sum(ZLYCLF) as ZLYCLF,
                  sum(SSYCLF) as SSYCLF, 
                  sum(XYF) as XYF, 
                  sum(ZCYF) as ZCYF, 
                  sum(ZCY) as ZCY,
                  sum(ZCYF) + sum(ZCY) as ZYZLF,
                  sum(QTF) as QTF
            from (select d.jzxh as jzxh,
                          c.brid as brid,
                          c.jgid as jgid,
                          case
                            when b.basygb = '11' then
                             a.zjje
                          end as YBYLFWF,
                          case
                            when b.basygb = '12' then
                             a.zjje
                          end as YBZLCZF,
                          case
                            when b.basygb = '13' then
                             a.zjje
                          end as QTFY,
                          case
                            when b.basygb = '14' then
                             a.zjje
                          end as HLF,
                          case
                            when b.basygb = '21' then
                             a.zjje
                          end as BLZDF,
                          case
                            when b.basygb = '22' then
                             a.zjje
                          end as SYSZDF,
                          case
                            when b.basygb = '23' then
                             a.zjje
                          end as LCZDXMF,
                          case
                            when b.basygb = '24' then
                             a.zjje
                          end as YXXZDF,
                          case
                            when b.basygb = '31' then
                             a.zjje
                          end as FSSZLXMF,
                          case
                            when b.basygb = '32' then
                             a.zjje
                          end as LCWLZLF,
                          case
                            when b.basygb = '41' then
                             a.zjje
                          end as MZF,
                          case
                            when b.basygb = '42' then
                             a.zjje
                          end as SSF,
                          case
                            when b.basygb = '50' then
                             a.zjje
                          end as KFF,
                          case
                            when b.basygb = '61' then
                             a.zjje
                          end as XF,
                          case
                            when b.basygb = '62' then
                             a.zjje
                          end as BDBLZPF,
                          case
                            when b.basygb = '63' then
                             a.zjje
                          end as QDBLZPF,
                          case
                            when b.basygb = '64' then
                             a.zjje
                          end as NXYZLZPF,
                          case
                            when b.basygb = '65' then
                             a.zjje
                          end as XBYZLZPF,
                          case
                            when b.basygb = '71' then
                             a.zjje
                          end as JCYCLF,
                          case
                            when b.basygb = '72' then
                             a.zjje
                          end as ZLYCLF,
                          case
                            when b.basygb = '73' then
                             a.zjje
                          end as SSYCLF,
                          case
                            when b.basygb = '99' then
                             a.zjje
                          end as QTF,
                          case
                            when a.yplx = 1 then
                            
                             a.zjje
                          end as XYF,
                          case
                            when a.yplx = 2 then
                            
                             a.zjje
                          end as ZCYF,
                          case
                            when a.yplx = 3 then
                             a.zjje
                          end as ZCY
                   
                     from ZY_FYMX a, GY_YLSF b, zy_brry c, emr_basy d
                    where a.yplx = 0
                      and a.zyh = c.zyh
                      and c.zyh = d.jzxh
                      and a.FYXH = b.FYXH
                      and b.BASYGB is not null)
           group by jzxh, brid, jgid ) t3 ,
           
            (
          
          select jzxh,
                  brid,
                  jgid,
                  sum(XYF) as XYF,
                  
                  sum(ZCYF) as ZCYF,
                  
                  sum(ZCY) as ZCY,
                  sum(ZCYF) + sum(ZCY) as ZYZLF,
                  0 as QTF
            from (select d.jzxh as jzxh,
                          c.brid as brid,
                          c.jgid as jgid,
                          case
                            when a.yplx = 1 then
                             a.zjje
                          end as XYF,
                          case
                            when a.yplx = 2 then
                             a.zjje
                          end as ZCYF,
                          case
                            when a.yplx = 3 then
                             a.zjje
                          end as ZCY
                     from ZY_FYMX a,  zy_brry c, emr_basy d
                    where a.yplx > 0
                      and a.zyh = c.zyh
                      and c.zyh = d.jzxh)
           group by jzxh, brid, jgid) t4   
           where t.zyh =t2.jzxh 
           and t.ryrq >= (select trunc(add_months(sysdate,-12),'year') from dual) 
           and t.ryrq <=(select trunc(sysdate,'year')-1 from dual)
           and  t.zyh = t3.jzxh (+)  
           and  t.zyh = t4.jzxh (+)
           and t.cypb = 8 
           and t.jgid = :jgid
 
 
 
 
 
