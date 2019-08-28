package br.com.example.service;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.example.common.CommonRepository;
import br.com.example.enums.StatusEnum;
import br.com.example.model.User;
import br.com.example.repository.UserRepository;

/**
 * UserService
 */
@Service
public class UserService {

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAllPageable(String nome, Long id, StatusEnum status, LocalDate de, LocalDate ate,  Pageable pageable) {
       return commonRepository.newBuilder()
                .from(User.class)
                    .withPageable(pageable)
                    .withParameter("nome", "%", nome)
                    .withParameter("status", "=", status)
                    .withParameter("possuiCnh", "<>", false)
                    .withParameter("dataNascimento", ">=", de)
                    .withParameter("dataNascimento", "<=", ate)
                    .withSort("asc", "dataNascimento")
                .toPage();
    }

    public List<User> findAll(String nome, Long id, StatusEnum status, LocalDate de, LocalDate ate) {
        return commonRepository.newBuilder()
                 .from(User.class)
                     .withParameter("nome", "%", nome)
                     .withParameter("status", "=", status)
                     .withParameter("dataNascimento", ">=", de)
                     .withParameter("dataNascimento", "<=", ate)
                     .withSort("asc", "dataNascimento")
                 .toList();
     }


     public User findOne(Long id) {
        return userRepository.findOne(id);
     }

     public User create(User user) {
         user.setDataNascimento(LocalDate.now());
         return userRepository.save(user);
     }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public Long count(String nome, Long id, StatusEnum status, LocalDate de, LocalDate ate) {
        return commonRepository.newBuilder()
                 .from(User.class)
                     .withParameter("nome", "%", nome)
                     .withParameter("status", "=", status)
                     .withParameter("possuiCnh", "<>", false)
                     .withParameter("dataNascimento", ">=", de)
                     .withParameter("dataNascimento", "<=", ate)
                 .toCount();
     }

}