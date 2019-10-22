package dao;

import java.util.List;

public interface DAO<T> {

    boolean  create(T obj);
   void  update(T obj);
   boolean delete(String login);
   List<T> search(String name);
}
