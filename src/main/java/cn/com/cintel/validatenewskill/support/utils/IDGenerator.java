package cn.com.cintel.validatenewskill.support.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 全局静态对象类
 * @author tom
 * @version 2017-11-14
 */
public class IDGenerator
{

	/*分布式下，服务器节点没有配置时的默认值---注意此处最大值为15*/
	private static String NODE="15";

	/*分布式下，自增变量的数值上限*/
	private static Integer INCSTATIC=250;

	/**单例设计模板（此处无任何意义）*/
	private IDGenerator(){}
	private static IDGenerator instance = null;
	public synchronized static IDGenerator getInstance() {
		if (instance == null)
			instance = new IDGenerator();
		return instance;
	}
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map =new HashMap<>();

	private static AtomicInteger counter =  new AtomicInteger(0);

	public static synchronized String getorderNo() {
		String hexString = null;//前8位时间戳
		String nodeIdString = null;//中间两位节点
		String inc = null;//后四位自增变量。
		long time = System.currentTimeMillis();
		String nodeId = NODE;
//		String nodeId = getConfig("node.id");
		if(StringUtils.isBlank(nodeId)){//若配置数据读取不到则赋值为默认值。
			nodeId=NODE;
		}
		hexString = String.format("%011X",  System.currentTimeMillis());
		nodeIdString = String.format("%01X", Integer.parseInt(nodeId));
		int increment = counter.getAndIncrement();
		if(increment>INCSTATIC){
			counter.set(0);
		}
		inc = String.format("%02X", increment);
		String str = hexString + nodeIdString+inc;
//		String str = hexString;
		return str;
	}
	/*测试方法*/
	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			System.out.println(getorderNo());
		}
	}

}
