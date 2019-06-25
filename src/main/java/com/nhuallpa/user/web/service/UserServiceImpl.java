package com.nhuallpa.user.web.service;

import com.nhuallpa.user.exception.ConflictException;
import com.nhuallpa.user.exception.GeneralException;
import com.nhuallpa.user.model.User;
import com.nhuallpa.user.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User create(User user) {
    if (!user.hasAllowedAge()) {
      throw new GeneralException("Age do not allowed");
    }

    User userExist = this.userRepository.findByUser(user);
    if (userExist != null) {
      throw new ConflictException("User already exist");
    }
    return this.userRepository.save(user);
  }

  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public User findById(Integer id) {
    return this.userRepository.findById(id).orElse(null);
  }


  @Override
  public User update(User user) {
    if (!user.hasAllowedAge()) {
      throw new GeneralException("Age do not allowed");
    }
    Optional<User> currentUser = userRepository.findById(user.getId());
    if (currentUser.isPresent()) {
      return this.userRepository.save(currentUser.get().copyValues(user));
    } else {
      return null;
    }
  }

  @Override
  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }


}
