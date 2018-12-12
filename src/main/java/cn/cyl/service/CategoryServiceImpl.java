package cn.cyl.service;

import cn.cyl.entity.Category;
import cn.cyl.mapper.CategoryMapper;
import cn.cyl.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-14-16:29
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryMapper mapper;

    @Override
    public List<Category> listAll() {
        return mapper.listAll();
    }

    @Override
    public void addCategory(Category category) {
        mapper.addCategory(category);
    }

    @Override
    public long totalNumber() {
        return mapper.totalNumber();
    }

    @Override
    public List<Category> listByPage(Page page) {
        return mapper.listByPage(page);
    }

    @Override
    public void deleteCategory(Category category) {
        mapper.deleteCategory(category);
    }

    @Override
    public Category findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public void updateCategory(Category category) {
        mapper.updateCategory(category);
    }
}
