package com.gg.server.service.edu.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.entity.edu.ArticleStatistics;
import com.gg.server.mapper.edu.ArticleStatisticsMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯文章统计信息表 服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public class ArticleStatisticsServiceImpl extends ServiceImpl<ArticleStatisticsMapper, ArticleStatistics> implements IService<ArticleStatistics> {

}
