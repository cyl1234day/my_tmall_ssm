package cn.cyl.util;

public class Page {

    //开始位置
    private int start;
    //每页显示几条数据
    private int countPerPage = 5;
    //总共有几条数据
    private long total;
    //参数
    private String param;

    public void setStart(int start) {
        this.start = start;
    }

    public Page() {

    }


    public Page (int start, int countPerPage) {
        this.start = start;
        this.countPerPage = countPerPage;
    }


    public int getStart() {
        return start;
    }


    public int getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    //获取分页的总页数
    public long getTotalPage() {
        if(total == 0) {
            return 1;
        }
        if(total % countPerPage == 0) {
            return total/countPerPage;
        } else {
            return total/countPerPage + 1;
        }
    }

    //获取最后一页从几号开始（默认从0开始编号,起始的编号都是defaultcountPerPage的倍数）
    public long getLast() {
        if(total <= countPerPage) {
            return 0;
        } else {
            long num = (total-1)/countPerPage;
            return num * countPerPage;
        }

    }

    //判断是否有前一页
    public boolean isHasPreviouse() {
        if(start == 0) {
            return false;
        }
        return true;
    }

    //判断是否有后一页
    public boolean isHasNext() {
        if(start == getLast()) {
            return false;
        }
        return true;
    }
}
