package db.managers;

import java.util.List;

public interface IDefaultManager<T> {
  public void create( T value ) throws Exception;

  public void update( T value ) throws Exception;

  public void delete( T value ) throws Exception;

  public T getById( int id ) throws Exception;

  public List<T> getAll() throws Exception;
}
