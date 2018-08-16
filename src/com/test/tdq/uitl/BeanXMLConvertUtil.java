package com.test.tdq.uitl;

import com.test.tdq.bean.Jiuzhenxx;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * 
 * @author wind
 * @date 2016年9月13日 下午4:49:32
 * @Description: bean/XML 互转
 */
public class BeanXMLConvertUtil {

	public static void main(String[] args) {
		XStream xstream = new XStream(new StaxDriver());

		// XStream的XML输出更简洁,可以为您的自定义类名创建别名XML元素名称。这是唯一类型的映射需要使用XStream甚至是可选的。
		xstream.alias("person", Jiuzhenxx.class);

		Jiuzhenxx joe = new Jiuzhenxx();

		// bean to XML
		String xml = xstream.toXML(joe);
		// XML to bean
		Jiuzhenxx newJoe = (Jiuzhenxx) xstream.fromXML(xml);

		System.out.println(newJoe.getBaoxiaoje());

		System.out.println(xml);
	}
}