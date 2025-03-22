package spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Order)实体类
 *
 * @author makejava
 * @since 2024-07-05 18:03:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;

    private String productName;

    private Integer num;
}
