# Common-repository

### Intuito do projeto

Ajudar pessoas que sofrem fazendo criterias builder para cada classe do projeto exemplo

```

public interface UserRepositoryQuery {

    Page <User> filtrar(UserFilter filtrer);
}


public class UserRepositoryImpl implements UserRepositoryQuery {


   @PersistenceContext
   private EntityManager entityManager;


   @Override
   public Page<User> filtrar(UserFilter filter) {
       .....
   } 
}

@Repository
public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryQuery {

}

```


