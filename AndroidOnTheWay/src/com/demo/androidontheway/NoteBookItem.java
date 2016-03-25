package com.demo.androidontheway;

import java.io.Serializable;

/** �б���Ϣ */
@SuppressWarnings("serial")
public class NoteBookItem implements Serializable {
	
	/**������ĸ*/
	public String index;

	/** ���µ�ֵ */
	public String update=null;
	/** ���� */
	public String name;
	/** �ƺ� */
//	public String call;
	/** �绰���� */
//	public String number="null";
	/** �ֻ���� */
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
