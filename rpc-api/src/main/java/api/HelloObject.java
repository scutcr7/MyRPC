package api;

import java.io.Serializable;

/**
 * @author Ed
 * @create 2021-11-16 16:24
 */
public class HelloObject implements Serializable {

    private Integer id;
    private String message;

    @Override
    public String toString() {
        return "api.HelloObject{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
