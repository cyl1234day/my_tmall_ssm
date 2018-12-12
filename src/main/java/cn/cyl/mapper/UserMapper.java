package cn.cyl.mapper;

import cn.cyl.entity.User;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-1:25
 */
public interface UserMapper {

    //查询所有记录
    public List<User> listAll();

    //插入记录
    public void addUser(User user);

    //根据id查找记录
    public User findOneById(int id);

    //修改记录
    public void updateUser(User user);


//================================前端============================

    //根据用户名查找是否已存在用户
    public User findOneByName(String name);

}
