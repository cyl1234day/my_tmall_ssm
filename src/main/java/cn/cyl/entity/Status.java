package cn.cyl.entity;

public enum Status {
	WAITPAY("待付款"),
	WAITDELIVERY("待发货"),
	WAITCONFIRM("待确认"),
	WAITREVIEW("待评价"),
	FINISH("已完成"),
	DELETE("已删除");
	
	private String name;
	 
    private Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
