package cn.cyl.entity;

public class User {

	private int id;
	private String name;
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * 前台注册时要检验 用户名和密码都必须大于两位数
	 */


	//获取匿名姓名，中间使用*代替，用来在评价时显示
	public String getAnonymousName() {
		char i = name.charAt(0);
		char j = name.charAt(name.length()-1);
		String str = i+"******"+j;
		return str;
	}

	//获取匿名密码，中间使用*代替，用来在后台用户管理时显示
	public String getAnonymousPassword() {
		char i = password.charAt(0);
		char j = password.charAt(password.length()-1);
		String str = i+"******"+j;
		return str;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
	}

}
