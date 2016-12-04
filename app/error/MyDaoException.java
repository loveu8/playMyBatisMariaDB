package error;

public class MyDaoException extends Exception {

  private static final long serialVersionUID = 1L;

  public MyDaoException(String message) {
    super(message);
  }
}
