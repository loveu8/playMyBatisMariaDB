package services;

import pojo.db.User;

import java.util.List;

public interface UserService {
  List<User> all();

  User getUserById(Long id);
}
