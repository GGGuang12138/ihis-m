package com.gg.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 修改基本配置中的表名，执行 main 方法即可生成Controller、Service（继承Iservice）、mapper（继承baseMapper、以及mapper.xml）
 * @author cgg
 */

public class CodeGenerator {
    public static void main(String[] args) {

        AutoGenerator autoGenerator = new AutoGenerator();
        // 数据库连接配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("12345678");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/ihis_d?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        autoGenerator.setDataSource(dataSourceConfig);

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOpen(false); // 代码生成后打开目录
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        globalConfig.setAuthor("gg");
//        globalConfig.setIdType(IdType.ASSIGN_ID);// id 主键策略
//        globalConfig.setDateType(DateType.ONLY_DATE); // 定义生成的实体类中日期类型
        globalConfig.setSwagger2(true);// 开启实体Swagger注解
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.gg.server");
//        packageConfig.setEntity("entity.edu");
//        packageConfig.setMapper("mapper.edu");
//        packageConfig.setController("controller.edu");
//        packageConfig.setService("service.edu");
//        packageConfig.setServiceImpl("service.edu.impl");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.edu.impl");
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("m_user"); // 生成单表写法
        // strategyConfig.setInclude("user","product"); // 生成多张表写法。生成所有表，不用配置
        strategyConfig.setTablePrefix("m"+"_"); // 去表前缀d,根据实际情况填写
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);// 表映射规则
        strategyConfig.setColumnNaming(NamingStrategy.no_change);// 属性映射规则
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        autoGenerator.setStrategy(strategyConfig);

//        List<TableFill> list = new ArrayList<>();
//        TableFill tableFill1 = new TableFill("create_time", FieldFill.INSERT);
//        TableFill tableFill2 = new TableFill("update_time",FieldFill.INSERT_UPDATE);
//        list.add(tableFill1);
//        list.add(tableFill2);

//        strategyConfig.setTableFillList(list);
        autoGenerator.execute();
    }
}

