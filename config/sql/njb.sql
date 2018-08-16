 
select a.personName as "姓名",
	   a.idcard as "身份证",
       a.mobileNumber as "联系电话",
       b.checkDate as "年检日期",
       b.constriction as "高压",
       b.diastolic as "低压",
       b.height as "身高",
       b.weight as "体重",
       '' as "体质指数",
       '' as "老年人自评健康",
       '' as "老年人生活自理能力自我评估",
       case d.Physicalexercisefrequency
         when '1' then
          '每天'
         when '2' then
          '每周一次以上'
         when '3' then
          '偶尔'
         else
          '不锻炼'
       end as "体育锻炼频率",
       d.everyPhysicalExerciseTime as "每次锻炼时间（min）",
       d.insistexercisetime as "坚持锻炼时间",
       d.exerciseStyle as "锻炼方式",
       case d.dietaryHabit
         when '1' then
          '荤素均衡'
         when '2' then
          '荤食为主'
         when '3' then
          '素食为主'
         when '4' then
          '嗜盐'
         when '5' then
          '嗜油'
         else
          '嗜糖'
       end as "饮食习惯",
       
       case d.wehtherSmoke
         when '1' then
          '从不吸烟'
         when '2' then
          '已戒烟'
         else
          '吸烟'
       end as "吸烟状况",
       d.smokes as "日吸烟量",
       d.beginSmokeTime as "开始吸烟年龄",
       d.stopSmokeTime as "戒烟年龄",
       
       case d.drinkingFrequency
         when '1' then
          '从不'
         when '2' then
          '偶尔'
         when '3' then
          '经常'
         when '4' then
          '每天'
         else
          '戒酒'
       end as "饮酒频率",
       
       d.alcoholConsumption as "日饮酒量",
       d.geginToDrinkTime   as "开始饮酒年龄",
       
       case d.mainDrinkingVvarieties
         when '1' then
          '白酒(≥42度)'
         when '2' then
          '白酒(＜42度)'
         when '3' then
          '啤酒'
         when '4' then
          '黄酒、米酒'
         when '5' then
          '红酒'
         else
          '其它'
       end as "饮酒种类",
       case d.isDrink
         when '1' then
          '是'
         else
          '否'
       end as "近一年是否酗酒",
       
       case d.whetherDrink
         when '1' then
          '未戒酒'
         else
          '已戒酒'
       end as "是否戒酒",
       
       d.stopDrinkingTime as "戒酒年龄",
       '' as "运动功能",
       case e.barrelChest
         when '1' then
          '否'
         else
          '是'
       end as "是否为桶状胸",
       case e.breathSound
         when '1' then
          '正常'
         else
          '异常'
       end as "肺呼吸音是否正常",
       
       case e.rales
         when '1' then
          '无'
         when '2' then
          '干罗音'
         when '3' then
          '湿罗音'
         else
          '其他'
       end as "啰音类型",
       
       f.hgb as "血红蛋白",
       f.wbc as "白细胞",
       f.platelet as "血小板",
       f.proteinuria as "尿蛋白",
       f.glu as "尿糖",
       f.dka as "尿酮体",
       f.oc as "尿潜血",
       f.fbs as "空腹血糖（mmol/L）",
       f.hba1c as "糖化血红蛋白（%）",
       f.hbsag as "乙肝表面抗原",
       f.alt as "血清谷丙转氨酶（U/L）",
       f.ast as "血清谷草转氨酶（U/L）",
       f.alb as "白蛋白(g/L)",
       f.tbil as "总胆红素(μmol/L)",
       f.dbil as "结合胆红素(μmol/L)",
       f.cr as "血清肌酐(μmol/L)",
       f.bun as "血尿素氮(mmol/L)",
       f.kalemia as "血钾浓度(mmol/L)",
       f.natremia as "血钠浓度(mmol/L)",
       f.tc as "总胆固醇(mmol/L)",
       f.tg as "甘油三酯(mmol/L)",
       f.ldl as "血清低密度脂蛋白胆固醇(mmol/L)",
       f.hdl as "血清高密度脂蛋白胆固醇(mmol/L)"

  from HC_HealthCheck          b,
       MPI_DemographicInfo     a,
       EHR_HealthRecord        c,
       HC_LifestySituation     d,
       HC_Examination          e,
       HC_AccessoryExamination f
 where a.empiId = b.empiId
   and b.phrId = c.phrId
   and b.healthcheck = d.healthcheck
   and b.healthcheck = e.healthcheck
   and b.healthcheck = f.healthcheck
   and trunc((trunc(sysdate) - a.birthday) / 365) >= 60
   and b.checkdate >= date '2015-01-01'
   and b.checkdate <= date '2017-6-30' 

 