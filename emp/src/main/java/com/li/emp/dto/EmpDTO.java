package com.li.emp.dto;




import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 员工分页条件查询的请求DTO
 * 完全匹配接口文档的queryString参数
 */
@Data
public class EmpDTO {

    // 对应参数name：姓名，非必填
    private String name;

    // 对应参数gender：性别，1男2女，非必填
    private Integer gender;

    // 对应参数begin：入职开始时间，非必填
    // 必须加@DateTimeFormat，指定前端传的日期格式，Spring才能把字符串转成LocalDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    // 对应参数end：入职结束时间，非必填
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    // 对应参数page：页码，必填，未指定默认1，必须大于0
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    private Integer page = 1;

    // 对应参数pageSize：每页条数，必填，未指定默认10，必须大于0
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数必须大于0")
    private Integer pageSize = 10;
}