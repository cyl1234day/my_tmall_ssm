package cn.cyl.service;

import cn.cyl.entity.Category;
import cn.cyl.util.Page;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-14-16:28
 */
public interface CategoryService {
    //查询所有记录
    List<Category> listAll();

    //插入记录
    void addCategory(Category category);

    //返回总共有几条数据
    long totalNumber();

    //根据分页信息返回对象集合
    List<Category> listByPage(Page page);

    //删除记录
    void deleteCategory(Category category);

    //根据id查找记录
    Category findOneById(int id);

    //修改记录
    void updateCategory(Category category);
}
