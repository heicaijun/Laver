package cn.heicaijun.Laver.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import cn.heicaijun.Laver.Message.Message;
import cn.heicaijun.Laver.bean.ConfigBean;

public class CongfigSerializeUtility {
	/**
	 * 用于存储configBean配置
	 * @param file	需要存储的位置
	 * @param configBean	存储的ConfigBean对象
	 * @return	成功返回true，失败返回false
	 */
	public static boolean storeConfigBeanToXml(ConfigBean configBean, File file) {
		try (OutputStream os = new FileOutputStream(file);
				XMLEncoder encoder = new XMLEncoder(os);){
			if (configBean != null) {
				encoder.writeObject(configBean);
				encoder.flush();
				encoder.close();
				os.close();
				return true;
			}else {
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			Message.showMsg("存储ConfigBean时出错，信息如下：\n" + e.getMessage());
			return false;
		}
	}
	/**
	 * 加载ConfigBean配置档
	 * @param file	加载位置
	 * @return	如加载成功则返回配置档信息，失败返回null
	 */
	public static ConfigBean loadConfigBeanFromXml(File file) {
		try (FileInputStream fis = new FileInputStream(file);
				XMLDecoder decoder = new XMLDecoder(fis);){
			Object object = decoder.readObject();
			if (object instanceof ConfigBean) {
				return (ConfigBean)object;
			}else {
				Message.showMsg("配置档错误，无法读取正确文档");
				return null;
			}
		} catch (FileNotFoundException e) {
			Message.showMsg("文件不存在，请确认:\n" + e.getMessage());
			file.delete();
			e.printStackTrace();
		} catch (IOException e) {
			Message.showMsg("配置档错误，无法读取正确文档:\n" + e.getMessage());
			file.delete();
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通用的持久化类的方法
	 * @param object	需要持久化的类
	 * @param file	持久化后放置的位置
	 * @return	如果持久化成功返回true，否则false
	 */
	public static boolean store(Object object, File file) {
		try(FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);){
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				objectOutputStream.close();
				return true;
		} catch (FileNotFoundException e) {
			Message.showMsg("文件不存在，请确认:\n" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Message.showMsg("持久化Object时出错，信息如下：\n" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 通用的读取持久化类的方法
	 * @param file	读取的文件
	 * @return	Object 返回读取后的类，如果读取失败，返回null
	 */
	public static Object loadObject(File file) {
		try(FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);){
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Message.showMsg("文件不存在，请确认:\n" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Message.showMsg("配置档错误，无法读取正确文档:\n" + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Message.showMsg("ClassNotFoundException:\n" + e.getMessage());
		}
		return null;
	}
}
