package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface DAO<T> {
    default List<T> search(List<Predicate<T>> conditions) {
        return new ArrayList<T>();
    }
    boolean  create(T obj);
   void  update(T obj);
   boolean delete(String login);

    default boolean delete(int id) {
        return false;
    }

    default List<T> search(String name) {
        return new ArrayList<T>();
    }
}
