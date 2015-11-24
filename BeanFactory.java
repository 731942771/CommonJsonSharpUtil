package com.cuiweiyou.xianke.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 创建Bean
 */
public class BeanFactory {
	/** Bean实体 */
	public Object object = null;
	/** 全部属性的实体数据（属性名-值）集合 */
	public BeanMap beanMap = null;
	/** 全部属性的值拼接字串 */
	private StringBuilder sb2string = new StringBuilder();

	/**
	 * 创建Bean
	 * 得到Bean的属性实体集合
	 * @param propertyMap 全部属性的元数据（属性名-属性数据类型）的集合
	 */
	public BeanFactory(Map<String, Class> propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
		sb2string.append(object.getClass().getSimpleName() + " [");
	}

	/**
	 * 生成bean实体
	 * @param propertyMap bean实体属性的集合
	 * @return bean实体
	 */
	private Object generateBean(Map<String, Class> propertyMap) {
		// cglib中的bean生成器
		BeanGenerator generator = new BeanGenerator();
		Set<String> keySet = propertyMap.keySet();
		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			String key = i.next();
			// 添加属性。（属性名，属性的数据类型）
			generator.addProperty(key, propertyMap.get(key));
		}
		
		return generator.create();
	}

	/**
	 * 给bean的属性赋值
	 * @param property 属性名
	 * @param value 值
	 */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
		sb2string.append(property + "=" + value.toString() + ", ");
	}

	/**
	 * 得到bean属性的值
	 * @param property 属性名
	 * @return 值
	 */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/**
	 * 得到bean对象
	 * @return Bean实体
	 */
	public Object getObject() {
		return this.object;
	}
	
	/**
	 * Bean转为字符串
	 */
	@Override
	public String toString(){
		int lastIndex = sb2string.lastIndexOf(",");
		sb2string.replace(lastIndex, sb2string.length(), "");
		sb2string.append("]");
		
		return sb2string.toString();
	}
}
