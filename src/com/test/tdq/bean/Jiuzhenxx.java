package com.test.tdq.bean;

import java.math.BigDecimal;

public class Jiuzhenxx {

	// 交易id 唯一标识码
	private String jiaoyixh;
	// 姓名
	private String xingming;
	// 身份证号
	private String shenfenzh;
	// 门诊住院标志 1门诊 2 住院
	private String menzhenzypb;
	// 就诊日期
	private String jiuzhenrq;
	// 住院号
	private String zhuyuanhao;
	// 出院日期
	private String chuyuanrq;
	// 就诊机构代码
	private String jiuzhenjg;
	// 主要诊断 上传疾病ICD10码
	private String zhuyaozd;
	// 次要诊断 上传疾病ICD10码
	private String ciyaozd;
	// 总金额
	private BigDecimal zongjine;
	// 自费金额
	private BigDecimal zifeije;
	// 报销补助金额
	private BigDecimal baoxiaoje;

	// 费用构成
	// 医疗机构减免
	private String ylfy01;
	// 基本医疗保险
	private String ylfy02;
	// 大病医疗保险
	private String ylfy03;
	// 重大疾病扶贫基金
	private String ylfy04;
	// 区域内住院费用全报销救助
	private String ylfy05;
	// 疾病应急救助
	private String ylfy06;
	// 民政医疗救助
	private String ylfy07;
	// 卫生扶贫基金
	private String ylfy08;
	// 医药爱心基金
	private String ylfy09;
	// 十免
	// 减免一般诊疗费
	private String jmfy1;
	// 减免院内会诊费
	private String jmfy2;
	// 减免白内障复明手术费
	private String jmfy3;
	// 减免艾滋病抗病毒治疗
	private String jmfy4;
	// 减免基本公共卫生服务
	private String jmfy5;
	// 减免妇幼卫生健康服务
	private String jmfy6;
	// 减免巡回医疗服务
	private String jmfy7;
	// 减免药物治疗包虫病
	private String jmfy8;
	// 减免基本医保个人缴费
	private String jmfy9;
	// 减免贫困孕产妇住院分娩
	private String jmfy10;
	// 四补
	// 晚期血吸虫病人补助
	private String bzfy2;
	// 手术治疗包虫病补助
	private String bzfy3;
	// 大骨节病患者年补助
	private String bzfy4;
	// 0-6岁残疾儿童手术、康复康复训练和辅具
	private String bzfy5;
	
	private BigDecimal zyh;
	private String jgid;

	// 上传标志. 1 成功 0 失败
	private String scbz = "0";

	public String getScbz() {
		return scbz;
	}

	public void setScbz(String scbz) {
		this.scbz = scbz;
	}

	public String getJgid() {
		return jgid;
	}

	public void setJgid(String jgid) {
		this.jgid = jgid;
	}

	public BigDecimal getZyh() {
		return zyh;
	}

	public void setZyh(BigDecimal zyh) {
		this.zyh = zyh;
	}

	public String getJiaoyixh() {
		return jiaoyixh;
	}

	public void setJiaoyixh(String jiaoyixh) {
		this.jiaoyixh = jiaoyixh;
	}

	public String getXingming() {
		return xingming;
	}

	public void setXingming(String xingming) {
		this.xingming = xingming;
	}

	public String getShenfenzh() {
		return shenfenzh;
	}

	public void setShenfenzh(String shenfenzh) {
		this.shenfenzh = shenfenzh;
	}

	public String getMenzhenzypb() {
		return menzhenzypb;
	}

	public void setMenzhenzypb(String menzhenzypb) {
		this.menzhenzypb = menzhenzypb;
	}

	public String getJiuzhenrq() {
		return jiuzhenrq;
	}

	public void setJiuzhenrq(String jiuzhenrq) {
		this.jiuzhenrq = jiuzhenrq;
	}

	public String getZhuyuanhao() {
		return zhuyuanhao;
	}

	public void setZhuyuanhao(String zhuyuanhao) {
		this.zhuyuanhao = zhuyuanhao;
	}

	public String getChuyuanrq() {
		return chuyuanrq;
	}

	public void setChuyuanrq(String chuyuanrq) {
		this.chuyuanrq = chuyuanrq;
	}

	public String getJiuzhenjg() {
		return jiuzhenjg;
	}

	public void setJiuzhenjg(String jiuzhenjg) {
		this.jiuzhenjg = jiuzhenjg;
	}

	public String getZhuyaozd() {
		return zhuyaozd;
	}

	public void setZhuyaozd(String zhuyaozd) {
		this.zhuyaozd = zhuyaozd;
	}

	public String getCiyaozd() {
		return ciyaozd;
	}

	public void setCiyaozd(String ciyaozd) {
		this.ciyaozd = ciyaozd;
	}

	public String getYlfy01() {
		return ylfy01;
	}

	public void setYlfy01(String ylfy01) {
		this.ylfy01 = ylfy01;
	}

	public String getYlfy02() {
		return ylfy02;
	}

	public void setYlfy02(String ylfy02) {
		this.ylfy02 = ylfy02;
	}

	public String getYlfy03() {
		return ylfy03;
	}

	public void setYlfy03(String ylfy03) {
		this.ylfy03 = ylfy03;
	}

	public String getYlfy04() {
		return ylfy04;
	}

	public void setYlfy04(String ylfy04) {
		this.ylfy04 = ylfy04;
	}

	public String getYlfy05() {
		return ylfy05;
	}

	public void setYlfy05(String ylfy05) {
		this.ylfy05 = ylfy05;
	}

	public String getYlfy06() {
		return ylfy06;
	}

	public void setYlfy06(String ylfy06) {
		this.ylfy06 = ylfy06;
	}

	public String getYlfy07() {
		return ylfy07;
	}

	public void setYlfy07(String ylfy07) {
		this.ylfy07 = ylfy07;
	}

	public String getYlfy08() {
		return ylfy08;
	}

	public void setYlfy08(String ylfy08) {
		this.ylfy08 = ylfy08;
	}

	public BigDecimal getZongjine() {
		return zongjine;
	}

	public void setZongjine(BigDecimal zongjine) {
		this.zongjine = zongjine;
	}

	public BigDecimal getZifeije() {
		return zifeije;
	}

	public void setZifeije(BigDecimal zifeije) {
		this.zifeije = zifeije;
	}

	public BigDecimal getBaoxiaoje() {
		return baoxiaoje;
	}

	public void setBaoxiaoje(BigDecimal baoxiaoje) {
		this.baoxiaoje = baoxiaoje;
	}

	public String getYlfy09() {
		return ylfy09;
	}

	public void setYlfy09(String ylfy09) {
		this.ylfy09 = ylfy09;
	}

	public String getJmfy1() {
		return jmfy1;
	}

	public void setJmfy1(String jmfy1) {
		this.jmfy1 = jmfy1;
	}

	public String getJmfy2() {
		return jmfy2;
	}

	public void setJmfy2(String jmfy2) {
		this.jmfy2 = jmfy2;
	}

	public String getJmfy3() {
		return jmfy3;
	}

	public void setJmfy3(String jmfy3) {
		this.jmfy3 = jmfy3;
	}

	public String getJmfy4() {
		return jmfy4;
	}

	public void setJmfy4(String jmfy4) {
		this.jmfy4 = jmfy4;
	}

	public String getJmfy5() {
		return jmfy5;
	}

	public void setJmfy5(String jmfy5) {
		this.jmfy5 = jmfy5;
	}

	public String getJmfy6() {
		return jmfy6;
	}

	public void setJmfy6(String jmfy6) {
		this.jmfy6 = jmfy6;
	}

	public String getJmfy7() {
		return jmfy7;
	}

	public void setJmfy7(String jmfy7) {
		this.jmfy7 = jmfy7;
	}

	public String getJmfy8() {
		return jmfy8;
	}

	public void setJmfy8(String jmfy8) {
		this.jmfy8 = jmfy8;
	}

	public String getJmfy9() {
		return jmfy9;
	}

	public void setJmfy9(String jmfy9) {
		this.jmfy9 = jmfy9;
	}

	public String getJmfy10() {
		return jmfy10;
	}

	public void setJmfy10(String jmfy10) {
		this.jmfy10 = jmfy10;
	}

	public String getBzfy2() {
		return bzfy2;
	}

	public void setBzfy2(String bzfy2) {
		this.bzfy2 = bzfy2;
	}

	public String getBzfy3() {
		return bzfy3;
	}

	public void setBzfy3(String bzfy3) {
		this.bzfy3 = bzfy3;
	}

	public String getBzfy4() {
		return bzfy4;
	}

	public void setBzfy4(String bzfy4) {
		this.bzfy4 = bzfy4;
	}

	public String getBzfy5() {
		return bzfy5;
	}

	public void setBzfy5(String bzfy5) {
		this.bzfy5 = bzfy5;
	}

}
