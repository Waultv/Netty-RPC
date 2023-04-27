package com.xuzc.netty.medium;

import java.lang.reflect.Method;
import java.util.HashMap;
import com.alibaba.fastjson.JSONObject;
import com.xuzc.netty.handler.param.ServerRequest;
import com.xuzc.netty.util.Response;

public class Media {
	public static final HashMap<String, BeanMethod> beanMap = new HashMap<String,BeanMethod>();
	private static Media media = null;
	
	
	private Media(){}
	
	public static Media newInstance(){
		if(media == null){
			media = new Media();
		}
		
		return media;
	}
	
	public Response process(ServerRequest request){
		Response result = null;
		try {
			String command = request.getCommand();//command是key
			BeanMethod beanMethod = beanMap.get(command);
			if(beanMethod == null){
				return null;
			}
			
			Object bean = beanMethod.getBean();
			Method method = beanMethod.getMethod();
			Class<?> type = method.getParameterTypes()[0];//先只实现1个参数的方法
			Object content = request.getContent();
			Object args = JSONObject.parseObject(JSONObject.toJSONString(content), type);
			
			result = (Response) method.invoke(bean, args);
			result.setId(request.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
}

