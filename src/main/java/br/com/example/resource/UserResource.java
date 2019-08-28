package br.com.example.resource;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.example.config.RestApiController;
import br.com.example.enums.StatusEnum;
import br.com.example.model.User;
import br.com.example.service.UserService;

/**
 * UserResource
 */
@RestApiController("/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<User> findOne(@PathVariable Long id) {
    return new ResponseEntity<User>(userService.findOne(id), HttpStatus.OK);
  }

  @GetMapping("/count")
  public ResponseEntity<Long> count(@RequestParam(required = false) String nome,
      @RequestParam(required = false) Long id, @RequestParam(required = false) StatusEnum status,
      @RequestParam(required = false) LocalDate de, @RequestParam(required = false) LocalDate ate) {
    return new ResponseEntity<Long>(userService.count(nome, id, status, de, ate), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String nome,
      @RequestParam(required = false) Long id, @RequestParam(required = false) StatusEnum status,
      @RequestParam(required = false) LocalDate de, @RequestParam(required = false) LocalDate ate) {
    return new ResponseEntity<List<User>>(userService.findAll(nome, id, status, de, ate), HttpStatus.OK);
  }

  @GetMapping("/pageable")
  public ResponseEntity<Page<User>> findAllPageable(@RequestParam(required =  false) String nome, @RequestParam(required =  false) Long id,
      @RequestParam(required =  false) StatusEnum status, @RequestParam(required =  false) LocalDate de, @RequestParam(required =  false) LocalDate ate, Pageable pageable) {
    return new ResponseEntity<Page<User>>(userService.findAllPageable(nome, id, status, de, ate, pageable),
        HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody User user) {
    return new ResponseEntity<User>(userService.create(user), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
