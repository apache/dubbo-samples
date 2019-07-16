package org.dubbo.samples.protosubff.patch;

import java.io.IOException;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.protostuff.utils.WrapperUtils;
import org.apache.dubbo.common.utils.ClassHelper;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.exchange.Request;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

/**
 * 一个临时解决的办法
 * 主要描述了问题的产生
 * 环境
 * dubbo 2.7.1
 * protocol 使用 dubbo 序列化设置为 protosubff
 * 别的为默认
 * 
 * 
 * @author: chenyu
 * @date:   2019年7月16日 上午10:23:58 
 *
 */
public class ProtosubffPatch {

	/**
	 * 这里简单的做了处理 
	 * 
	 * 问题描述
	 * 当使用 protosubff序列化时
	 * NettyClientHandler.userEventTriggered  发送心跳时
		Request req = new Request();
        req.setVersion(Version.getProtocolVersion());
        req.setTwoWay(true);
        req.setEvent(Request.HEARTBEAT_EVENT);
        --->没有 setData
        ========================================================================================================================
	 * 编码时候
	 * 抛出java.lang.NullPointerException
	 * 原因：
	 * ExchangeCodec.encodeEventData
	 * 	-->out.writeObject(data); // data=null
	 * WrapperUtils.needWrapper(obj) // obj=null
	 * 	public static boolean needWrapper(Object obj) {
        	return needWrapper(obj.getClass()); //--->java.lang.NullPointerException
    	}
    	==============================================================================================================================
	 * 解码时候
	 * 抛出一场
	 * java.io.EOFException: null
	 * java.io.DataInputStream.readInt(DataInputStream.java:392)
	 * 原因
	 * DubboCodec.decodeBody -->122行
	 * if (req.isHeartbeat()) {
			data = decodeHeartbeatData(channel, in);
		}
		Request.isHeartbeat() //表示是一个心跳  
		public boolean isHeartbeat() {
        	return mEvent && HEARTBEAT_EVENT == mData; //-->HEARTBEAT_EVENT=null
    	}
    	所以
    	protected Object decodeHeartbeatData(Channel channel, ObjectInput in) throws IOException {
        try {
            return in.readObject(); // ---->这里 java.io.EOFException: null
        } catch (ClassNotFoundException e) {
            throw new IOException(StringUtils.toString("Read object failed.", e));
        }
    }
	 */
	public static void patch(){
		try{
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath(new LoaderClassPath(ClassHelper.getClassLoader()));
		CtClass ctClass = classPool.getCtClass("org.apache.dubbo.remoting.exchange.codec.ExchangeCodec");
		CtClass[] paramTypes = {classPool.get(ObjectOutput.class.getName()),classPool.get(Object.class.getName())}; 
		CtMethod m = ctClass.getDeclaredMethod("encodeEventData",paramTypes);
		m.setBody("{"
				+ "if($2!=null){"
				+ "$1.writeObject($2);"
				+ "}else{"
				+ "logger.debug(\"temp patch data is null\");"
				+ "}"
				+ "}");
		CtClass[] paramTypes2 = {classPool.get(Channel.class.getName()),classPool.get(ObjectInput.class.getName())}; 
		CtMethod m2 = ctClass.getDeclaredMethod("decodeHeartbeatData",paramTypes2);
		m2.setBody("{"
				+ "logger.debug(\"temp patch heartbeatData is null\");"
				+ "return null;"
				+ "}");
		ctClass.toClass(ClassHelper.getClassLoader(), ctClass.getClass().getProtectionDomain());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
