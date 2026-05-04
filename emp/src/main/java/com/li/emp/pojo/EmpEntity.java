package com.li.emp.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("emp") // 指定数据库表名
public class EmpEntity {

    @TableId(type = IdType.AUTO) // 主键自增
    private Integer id; //ID,主键
    
    private String username; //用户名
    
    private String password; //密码
    
    private String name; //姓名
    
    private Integer gender; //性别, 1:男, 2:女
    
    private String phone; //手机号
    
    private Integer job; //职位, 1:班主任,2:讲师,3:学工主管,4:教研主管,5:咨询师
    
    private Integer salary; //薪资
    
    private String image; //头像
    
    private LocalDate entryDate; //入职日期
    
    private Integer deptId; //关联的部门ID
    
    @TableField(fill = FieldFill.INSERT) // 插入时自动填充
    private LocalDateTime createTime; //创建时间
    
    @TableField(fill = FieldFill.INSERT_UPDATE) // 插入和更新时自动填充
    private LocalDateTime updateTime; //修改时间

    @TableField(exist = false) // 标注该字段在数据库表中不存在，仅用于业务逻辑传递
    private String token; // 登录成功后返回的令牌

}
