package sa.entity;

import lombok.Data;

/**
 * @author: 小手WA凉
 * @create: 2024-06-06
 */
@Data
public class Liked {
    private Integer id;
    private Integer subjectId;
    private String userName;
    private Integer status;
}
