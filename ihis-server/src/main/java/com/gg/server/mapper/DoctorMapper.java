package com.gg.server.mapper;

import com.gg.server.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gg.server.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gg
 * @since 2021-02-15
 */
@Component
public interface DoctorMapper extends BaseMapper<Doctor> {

}
