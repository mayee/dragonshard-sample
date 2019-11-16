/*
 *   Copyright 1999-2018 dragonshard.net.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.dragonshard.sample;

import java.util.Random;
import net.dragonshard.dsf.id.generator.local.LocalIdGenerator;
import net.dragonshard.dsf.id.generator.redis.RedisIdGenerator;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;

/**
 * 全局ID生成器测试
 *
 * @author mayee
 * @version v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IdGeneratorTest {

  @Value("${dragonshard.id-generator.local.data-center:1}")
  private String dataCenter;
  @Value("${dragonshard.id-generator.local.machine:1}")
  private String machine;

  @Value("${dragonshard.id-generator.redis.enabled:false}")
  private String redisEnabled;

  @Autowired
  private LocalIdGenerator localIdGenerator;
  @Autowired(required = false)
  private RedisIdGenerator redisIdGenerator;

  @Test
  public void testLocalIdGenerator() throws Exception {
    Assert.assertNotNull(localIdGenerator.nextUniqueId(), null);
    Assert.assertNotNull(
      localIdGenerator.nextUniqueId(Long.parseLong(dataCenter), Long.parseLong(machine)), null);
    Assert.assertEquals(localIdGenerator.nextUniqueIds(5).length, 5);
    Assert.assertEquals(
      localIdGenerator.nextUniqueIds(Long.parseLong(dataCenter), Long.parseLong(machine), 7).length,
      7);
  }

  @Test
  public void testRedisIdGenerator() throws Exception {
    // 当具备redis环境时才执行测试
    Assume.assumeTrue(Boolean.valueOf(redisEnabled));

    // 增加随机值，保证每次测试都能从1开始
    String name = "name-" + new Random().nextInt(1000);
    Assert.assertTrue(redisIdGenerator.nextUniqueId(name, "key", 1, 8).endsWith("00000001"));

    name = "name-" + new Random().nextInt(1000);
    Assert.assertTrue(redisIdGenerator.nextUniqueId(name, "key", 12345, 8).endsWith("00012345"));

    name = "name-" + new Random().nextInt(1000);
    Assert.assertTrue(redisIdGenerator.nextUniqueId(name, "key", 12345, 2).endsWith("45"));
  }

}
