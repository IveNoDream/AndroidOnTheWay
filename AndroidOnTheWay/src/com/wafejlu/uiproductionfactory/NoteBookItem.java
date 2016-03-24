package com.wafejlu.uiproductionfactory;

import java.io.Serializable;

/** 列表信息 */
@SuppressWarnings("serial")
public class NoteBookItem implements Serializable {
	
	/**索引字母*/
	public String index;

	/** 更新的值 */
	public String update=null;
	/** 名字 */
	public String name;
	/** 称号 */
//	public String call;
	/** 电话号码 */
//	public String number="null";
	/** 手机号码 */
//	public String mobile="null";
	/** mail */
//	public String mail;
	/**imageID*/
	public int imageRscID;

	@Override
	public String toString() {
		return "NoteBookItem [update=" + update + ", name=" + name + //", call="
				//+ call + ", number=" + number + ", mobile=" + mobile
				//+ ", mail=" + mail + 
				"]";
	}
}
