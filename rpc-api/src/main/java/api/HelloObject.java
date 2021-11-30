package api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ed
 * @create 2021-11-16 16:24
 */

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class HelloObject implements Serializable {

    private Integer id;
    private String message;

    public HelloObject(){

    }

    public HelloObject(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public String toString() {
        return "api.HelloObject{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
