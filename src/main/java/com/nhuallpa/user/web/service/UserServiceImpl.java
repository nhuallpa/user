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
  private UserRepository usuarioRepository;

  @Override
  public User create(User user) {
    if (!user.hasAllowedAge()) {
      throw new GeneralException("Age do not allowed");
    }

    User userExist = this.usuarioRepository.findByUser(user);
    if (userExist != null) {
      throw new ConflictException("User already exist");
    }
    return this.usuarioRepository.save(user);
  }

  public List<User> findAll() {
    return this.usuarioRepository.findAll();
  }

  @Override
  public User findById(Integer id) {
    return this.usuarioRepository.findById(id).orElse(null);
  }


  @Override
  public User update(User user) {
    if (!user.hasAllowedAge()) {
      throw new GeneralException("Age do not allowed");
    }
    Optional<User> currentUser = usuarioRepository.findById(user.getId());
    if (currentUser.isPresent()) {
      return this.usuarioRepository.save(currentUser.get().copyValues(user));
    } else {
      return null;
    }
  }

  @Override
  public void deleteById(Integer id) {
    usuarioRepository.deleteById(id);
  }


}
