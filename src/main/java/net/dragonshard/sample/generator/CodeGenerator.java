package net.dragonshard.sample.generator;

import net.dragonshard.dsf.data.mybatis.generate.MybatisCodeGenerator;

/**
 * 代码生成器
 *
 * @author dragonshard.net
 */
public class CodeGenerator {

  public static void main(String[] args) {
    // 包含的表集合
    String[] inCludeTableNames = {
      "sys_user",
      "sys_role",
      "sys_user_role"
    };

    MybatisCodeGenerator mybatisGenerator = new MybatisCodeGenerator();
    mybatisGenerator.getDataSourceConfig()
      .setDriverName("com.mysql.jdbc.Driver")
      .setUrl(
        "jdbc:mysql://localhost:3306/dragonshard_sample_master?allowMultiQueries=true&useSSL=false")
      .setUsername("root")
      .setPassword("b123456");

    mybatisGenerator.getStrategyConfig()
      .setInclude(inCludeTableNames)
      // 去除前缀
      .setTablePrefix("sys_");

    mybatisGenerator.getPackageConfig()
      .setParent("net.dragonshard.sample");

    mybatisGenerator.getGlobalConfig()
      .setBaseResultMap(false)
      // XML columList
      .setBaseColumnList(false)
      .setOpen(false)
      .setAuthor("dragonshard")
      .setSwagger2(false);

    mybatisGenerator.execute();
  }

}
