package br.com.dicasdeumdev.api.services;


import java.util.List;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create (UserDTO obj);
    User update (UserDTO obj);
    void delete (Integer id);
}
