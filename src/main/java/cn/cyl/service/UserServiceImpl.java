package cn.cyl.service;

import cn.cyl.entity.User;
import cn.cyl.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-1:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;

    @Override
    public List<User> listAll() {
        return mapper.listAll();
    }

    @Override
    public void addUser(User user) {
        mapper.addUser(user);
    }

    @Override
    public User findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public void updateUser(User user) {
        mapper.updateUser(user);
    }

    @Override
    public User findOneByName(String name) {
        return mapper.findOneByName(name);
    }

    @Override
    public boolean isExist(User user) {
        User db_user = this.findOneByName(user.getName());
        return db_user != null;
    }
}
