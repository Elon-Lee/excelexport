package com.test.tdq.uitl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * XML文档的工作作坊，提供一些基本的对xml的操作
 * 
 */
public class XMLUtil {
	public static XMLUtil xmlUtil = null;

	public static Map<String, String> hospitalMap = getXMLUtil().getDicList("hospital3");

	private XMLUtil() { // prevent instantiation

	}

	public static XMLUtil getXMLUtil() {
		if (xmlUtil == null) {
			xmlUtil = new XMLUtil();
		}
		return xmlUtil;
	}

	/**
	 * 线性图
	 * 
	 * @param caption
	 * @param subcaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param numberSuffix
	 * @param list
	 * @param funcion
	 * @return
	 */
	public static String getXMLLine(String caption, String subcaption, String xAxisName, String yAxisName, String numberSuffix, List list, String funcion) {
		if (list != null) {
			StringBuilder repprt = new StringBuilder("<chart rotateYAxisName=\"0\" baseFontSize=\"12\" caption=\"" + caption + "\" subcaption=\"" + subcaption
					+ "\" numberScaleValue=\"10000,10000\" borderAlpha=\"0\" lineColor=\"FF4500\"" + "  numberScaleUnit=\"万,亿\" bgColor=\"ffffff\" bgAlpha=\"0\" xAxisName=\""
					+ xAxisName + "\" yAxisName= \"" + yAxisName + "\" numberSuffix=\"" + numberSuffix + "\" " + " showValues=\"1\" showPercentvalues=");
			repprt.append(" \"0\"  unescapeLinks=\"0\" >");
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				if (objs != null && objs.length < 3) {
					repprt.append("<set label=\"" + objs[0] + "\" value=\"" + objs[1] + "\"/>");
					if (i == 0 && objs.length < 3) {
						repprt.append("");
					}
				} else {
					repprt.append("<set label=\"" + objs[0] + "\" value=\"" + objs[1] + "\"  link=\"JavaScript:" + funcion + "('" + objs[2] + "');\"  />");
				}
			}
			repprt.append("</chart>");
			return repprt.toString();
		}
		return null;
	}

	/**
	 * 趋势线及区域
	 * 
	 * @param caption
	 * @param subcaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param numberSuffix
	 * @param list
	 * @param funcion
	 * @return
	 */
	public static String getXMLTrendLines(String caption, String subcaption, String xAxisName, String yAxisName, String numberSuffix, List list, String funcion, String targetValue) {
		if (list != null) {
			StringBuilder repprt = new StringBuilder("<chart rotateYAxisName=\"1\"  slantLabels=\"1\" outCnvBaseFontSize=\"12\" caption=\"" + caption + "\"  subcaption=\""
					+ subcaption + "\" numberScaleValue=\"10000,10000\" " + "  numberScaleUnit=\"万,亿\" bgAlpha=\"0\" xAxisName=\"" + xAxisName + "\" yAxisName= \"" + yAxisName
					+ "\" numberSuffix=\"" + numberSuffix + "\" " + " showValues=\"1\" showPerceValues=\"1\" unescapeLinks=\"0\"  ");
			if (caption.equals("门诊抗生素使用比例") || caption.equals("门诊一联抗生素使用比图表")) {
				repprt.append("yAxisMaxValue=\"100\" ");
			}
			if (caption.equals("门诊二联抗生素使用比图表") || caption.equals("门诊三联抗生素使用比图表")) {
				repprt.append("yAxisMaxValue=\"50\" ");
			}
			repprt.append(">");
			StringBuilder sets = new StringBuilder("");
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				//
				sets.append("<set name=\"" + objs[0] + "\" value=\"" + objs[1] + "\" />");
			}
			repprt.append(sets);
			if (targetValue != null && !("").equals(targetValue.trim())) {
				repprt.append("<trendLines><line startValue='" + targetValue.trim() + "' color='FC0331' displayvalue='' valueOnRight='1' thickness='3'/></trendLines>");
			}
			repprt.append("</chart>");
			return repprt.toString();
		}
		return null;
	}

	/**
	 * 柱状图
	 * 
	 * @param caption
	 * @param subcaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param numberSuffix
	 * @param list
	 * @param funcion
	 * @return
	 */
	public static String getXMLMSColumn(String caption, String subcaption, String xAxisName, String yAxisName, String numberSuffix, List list, String funcion) {
		if (list != null) {
			StringBuilder repprt = new StringBuilder("<chart rotateYAxisName=\"1\" outCnvBaseFontSize=\"12\" caption=\"" + caption + "\" subcaption=\"" + subcaption
					+ "\" numberScaleValue=\"10000,10000\" " + "  numberScaleUnit=\"万,亿\" bgAlpha=\"0\" xAxisName=\"" + xAxisName + "\" yAxisName= \"" + yAxisName
					+ "\" numberSuffix=\"" + numberSuffix + "\" " + " showValues=\"1\" showPerceValues=\"1\" unescapeLinks=\"0\"  ");
			if (caption.equals("门诊抗生素使用比例") || caption.equals("门诊一联抗生素使用比图表")) {
				repprt.append("yAxisMaxValue=\"100\" ");
			}
			if (caption.equals("门急诊病人次均医药费用")) {
				repprt.append("yAxisMaxValue=\"200\" ");
			}
			if (caption.equals("出院病人人均医药费用")) {
				repprt.append("yAxisMaxValue=\"5000\" ");
			}

			if (caption.equals("门诊二联抗生素使用比图表") || caption.equals("门诊三联抗生素使用比图表")) {
				repprt.append("yAxisMaxValue=\"50\" ");
			}
			repprt.append(">");
			StringBuilder sets = new StringBuilder("");
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				//
				sets.append("<set name=\"" + objs[0] + "\" value=\"" + objs[1] + "\" />");
			}
			repprt.append(sets);
			repprt.append("</chart>");
			return repprt.toString();
		}
		return null;
	}

	/**
	 * 2d柱状图
	 * 
	 * @param caption
	 * @param subcaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param numberSuffix
	 * @param list
	 * @param funcion
	 * @return
	 */
	public static String getXMLMSColumn2D(String caption, String subcaption, String xAxisName, String yAxisName, String numberSuffix, List list, String funcion, String targetValue) {
		if (list != null) {
			StringBuilder repprt = new StringBuilder("<chart rotateYAxisName=\"1\" outCnvBaseFontSize=\"12\" palette=\"3\" canvasbgColor=\"FFfFFF\"  caption=\"" + caption
					+ "\" subcaption=\"" + subcaption + "\" numberScaleValue=\"10000,10000\" " + "  numberScaleUnit=\"万,亿\" bgAlpha=\"0\" xAxisName=\"" + xAxisName
					+ "\" yAxisName= \"" + yAxisName + "\" numberSuffix=\"" + numberSuffix + "\" "
					+ " showValues=\"1\" showPerceValues=\"1\" unescapeLinks=\"0\" useRoundEdges=\"1\" bgColor=\"FFFFFF,FFFFFF\" showBorder=\"0\" ");
			if (caption.equals("门诊抗生素使用比例") || caption.equals("门诊一联抗生素使用比图表") || caption.equals("住院抗生素使用比例") || caption.equals("全县高血压管理率") || caption.equals("全县糖尿病管理率")) {
				repprt.append("yAxisMaxValue=\"100\" ");
			}
			if (caption.equals("全县医疗单位均次处方费用")) {
				repprt.append("yAxisMaxValue=\"150\" ");
			}
			if (caption.equals("门诊二联抗生素使用比图表") || caption.equals("门诊三联抗生素使用比图表")) {
				repprt.append("yAxisMaxValue=\"50\" ");
			}
			repprt.append(">");
			StringBuilder sets = new StringBuilder("");
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				if (caption.equals("前10位均次处方费用") || funcion.equals("1")) {
					sets.append("<set name=\"" + objs[0] + "\n" + objs[1] + "\" value=\"" + objs[2] + "\" />");
				} else if (caption.equals("门诊前十位疾病排行") || caption.equals("前10位抗生素用药")) {

					sets.append("<set name=\"" + objs[0] + "\" value=\"" + objs[1] + "\" link=\"JavaScript:showTB('" + objs[0] + "');\"  />");
				} else if (objs != null && objs.length >= 3 && !caption.equals("门诊前十位疾病均次费用排行")) {
					sets.append("<set name=\"" + objs[0] + "\" value=\"" + objs[1] + "\" link=\"JavaScript:showTB('" + objs[2] + "');\"  />");
				} else {
					sets.append("<set name=\"" + objs[0] + "\" value=\"" + objs[1] + "\"  />");
				}
			}
			repprt.append(sets);
			if (targetValue != null && !("").equals(targetValue.trim())) {
				if (caption.equals("全县医疗单位均次处方费用")) {
					repprt.append("<trendLines><line startValue='"
							+ targetValue.trim()
							+ "' color='FC0331' displayvalue='' valueOnRight='1' thickness='3'/><line startValue='100' color='FC0331' displayvalue='' valueOnRight='1' thickness='3'/></trendLines>");
				} else {
					repprt.append("<trendLines><line startValue='" + targetValue.trim() + "' color='FC0331' displayvalue='' valueOnRight='1' thickness='3'/></trendLines>");
				}
			}
			repprt.append("</chart>");
			return repprt.toString();
		}
		return null;
	}

	/**
	 * 2D饼图
	 * 
	 * @param caption
	 * @param subcaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param numberSuffix
	 * @param list
	 * @param names
	 * @param indexs
	 * @return
	 */
	public static String getXMLPie2D(String caption, String subcaption, String xAxisName, String yAxisName, String numberSuffix, List list, String funcion) {
		if (list != null) {
			StringBuilder repprt = new StringBuilder("<chart rotateYAxisName=\"0\" baseFontSize=\"12\" caption=\"" + caption + "\" subcaption=\"" + subcaption
					+ "\" numberScaleValue=\"10000,10000\" borderAlpha=\"0\" lineColor=\"FF4500\"" + "  numberScaleUnit=\"万,亿\" bgColor=\"ffffff\" bgAlpha=\"0\" xAxisName=\""
					+ xAxisName + "\" yAxisName= \"" + yAxisName + "\" numberSuffix=\"" + numberSuffix + "\" "
					+ " showValues=\"1\"  showLabels=\"1\"  showLegend=\"1\" showPercentageInLabel=\"1\" ");
			repprt.append("  >");
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				if (funcion != null) {
					repprt.append("<set label=\"" + objs[0] + "\" value=\"" + objs[1] + "\"  link=\"JavaScript:" + funcion + "('" + objs[2] + "')\" />");
				} else {
					repprt.append("<set label=\"" + objs[0] + "\" value=\"" + objs[1] + "\"/>");
				}
			}
			repprt.append("<styles>" + "<definition>" + "<style type=\"font\" size=\"12\" color=\"#000000\" name=\"labels\"/>" + "</definition>" + "<application>"
					+ "<apply toObject=\"DATALABELS\" styles=\"labels\"/>" + "<apply toObject=\"TRENDVALUES\" styles=\"labels\"/>" + "</application>" + "</styles>");
			repprt.append("</chart>");
			return repprt.toString();
		}
		return null;
	}

	/**
	 * 饼图
	 * 
	 * @param caption
	 * @param subcaption
	 * @param xAxisName
	 * @param yAxisName
	 * @param numberSuffix
	 * @param list
	 * @param names
	 * @param indexs
	 * @return
	 */
	public static String getXMLPie(String caption, String subcaption, String xAxisName, String yAxisName, String numberSuffix, List list, String funcion) {
		if (list != null) {
			StringBuilder repprt = new StringBuilder("<chart rotateYAxisName=\"0\" baseFontSize=\"12\" caption=\"" + caption + "\" subcaption=\"" + subcaption
					+ "\" numberScaleValue=\"10000,10000\" borderAlpha=\"0\" lineColor=\"FF4500\"" + "  numberScaleUnit=\"万,亿\" bgColor=\"ffffff\" bgAlpha=\"0\" xAxisName=\""
					+ xAxisName + "\" yAxisName= \"" + yAxisName + "\" numberSuffix=\"" + numberSuffix + "\" " + " showValues=\"1\" showPercentvalues=");
			repprt.append(" \"0\"  unescapeLinks=\"0\" >");
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				repprt.append("<set label=\"" + objs[0] + "\" value=\"" + objs[1] + "\"/>");
				// if (objs != null && objs.length < 3) {
				// if (i == 0 && objs.length < 3) {
				// repprt.append("");
				// }
				// }else {
				// repprt.append("<set label=\"" + objs[0] + "\" value=\""
				// + objs[1] + "\"  link=\"JavaScript:" + funcion+ "('" +
				// objs[2] + "');\"  />");
				// }
			}
			repprt.append("<styles>" + "<definition>" + "<style type=\"font\" size=\"11\" color=\"#000000\" name=\"labels\"/>" + "</definition>" + "<application>"
					+ "<apply toObject=\"DATALABELS\" styles=\"labels\"/>" + "<apply toObject=\"TRENDVALUES\" styles=\"labels\"/>" + "</application>" + "</styles>");

			repprt.append("</chart>");
			return repprt.toString();
		}
		return null;
	}

	/**
	 * 查询字典list
	 * 
	 * @param fileName
	 * @return
	 */
	public Map<String, String> getDicList(String fileName) {
		String path = System.getProperty("user.dir") + "\\config\\" + fileName + ".xml";
		Document document = getDocFromFile(path);
		Map<String, String> map = new LinkedHashMap<String, String>();
		Element ele = document.getRootElement();
		getElementList(ele, map);
		document = null;
		return map;
	}

	/**
	 * 获取properties 文件内容封装到map中
	 * 
	 * @param fileName
	 * @return
	 */
	public Map<String, String> getProperties(String fileName) {
		String path = System.getProperty("user.dir") + "\\config\\" + fileName + ".properties";
		Map<String, String> map = new LinkedHashMap<String, String>();
		Properties prop = new Properties();
		try {
			// 读取属性文件a.properties
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			prop.load(in); // /加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				map.put(key, prop.getProperty(key));
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return map;
	}

	/**
	 * 查字典的值2 通过key 获得values
	 * 
	 * @param fileName
	 * @return
	 */
	public String getDicItemtTwo(String fileName, String itemkey) {
		String path = this.getClass().getClassLoader().getResource("/").getPath() + "\\com\\xjwsj\\datamonitor\\common\\dic\\" + fileName + ".xml";
		// String path
		// ="D:\\temp\\Center\\src\\com\\bsoft\\center\\config\\dic\\"+fileName+".xml";
		Document document = getDocFromFile(path);
		Map<String, String> map = new HashMap<String, String>();
		Element ele = document.getRootElement();
		String dic = getElementItemTwo(ele, itemkey);
		document = null;
		map.clear();
		return dic;
	}

	public String getElementItemTwo(Element element, String itemkey) {
		List elements = element.getChildren();
		if (elements.size() == 0) {

			if (element.getAttributeValue("text").equals(itemkey)) {

				return element.getAttributeValue("key");
			}

		} else {
			// 有子元素
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				String val = getElementItemTwo(elem, itemkey);
				if (val != null && !"".equals(val)) {
					return val;
				}
			}
		}
		return null;

	}

	/**
	 * 把XML文件转换为Doc
	 * 
	 * @param file
	 *            String
	 * @return Document
	 */
	public Document getDocFromFile(String file) {
		Document doc = null;
		try {
			InputStream stream = new FileInputStream(file);
			doc = getDocFromFile(stream);
			stream.close();
			stream = null;
		} catch (JDOMException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
		return doc;
	}

	/**
	 * 把XML文件流转换为Doc
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public Document getDocFromFile(InputStream stream) throws IOException, JDOMException {
		if (stream == null) {
			return null;
		}

		SAXBuilder reader = new SAXBuilder();
		Document doc = reader.build(stream);
		reader = null;
		return doc;
	}

	public void getElementList(Element element, Map<String, String> map) {
		List elements = element.getChildren();
		if (elements.size() == 0) {
			// 没有子元素
			String text = element.getAttributeValue("key");
			String key = element.getAttributeValue("text");
			map.put(text, key);

		} else {
			// 有子元素
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				getElementList(elem, map);

			}
		}

	}

	public String getElementItem(Element element, String itemkey) {
		List elements = element.getChildren();
		if (elements.size() == 0) {

			if (element.getAttributeValue("key").equals(itemkey)) {

				return element.getAttributeValue("text");
			}

		} else {
			// 有子元素
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				String val = getElementItem(elem, itemkey);
				if (val != null && !"".equals(val)) {
					return val;
				}
			}
		}
		return null;

	}

	/**
	 * 查字典的值 通过values 获得key
	 * 
	 * @param fileName
	 * @return
	 */
	public String getDicItemt(String fileName, String itemkey) {
		String path = this.getClass().getClassLoader().getResource("/").getPath() + "\\com\\xjwsj\\datamonitor\\common\\dic\\" + fileName + ".xml";
		// String path
		// ="D:\\temp\\Center\\src\\com\\bsoft\\center\\config\\dic\\"+fileName+".xml";
		Document document = getDocFromFile(path);
		Map<String, String> map = new HashMap<String, String>();
		Element ele = document.getRootElement();
		String dic = getElementItem(ele, itemkey);
		document = null;
		return dic;
	}

	public static void decodeJSONObject(JSONObject json) {
		Iterator<String> keys = json.keys();
		JSONObject jo = null;
		Object o;
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			o = json.get(key);
			if (o instanceof JSONObject) {
				jo = (JSONObject) o;
				if (jo.keySet().size() > 0) {
					decodeJSONObject(jo);
				} else {
					System.out.println(key);
				}
			} else {
				System.out.println(o);
			}
		}

	}
}
