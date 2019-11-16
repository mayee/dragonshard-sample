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

package net.dragonshard.sample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;
import net.dragonshard.dsf.core.toolkit.EncryptUtils;
import net.dragonshard.sample.Application;
import net.dragonshard.sample.model.bo.RoleBO;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleTest extends AbstractTransactionalTestNGSpringContextTests {

  private MockMvc mockMvc;

  // secret
  @Value("${dragonshard.secret.aes.key}")
  private String aesKey;

  @Autowired
  protected WebApplicationContext wac;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  /**
   * ------------------------------------------------------------
   * <p>
   * RESTful test
   * <p>
   * ------------------------------------------------------------
   */
  @Test
  public void test01RolePage() throws Exception {
    mockMvc.perform(
      get("/v1/api/role")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("roleName", "admin")
        .param("pageNum", "1")
        .param("pageSize", "2")
        .param("openPaging", "true")
    )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("200"))
      .andReturn();
  }

  @Test
  public void test02RoleGet() throws Exception {
    mockMvc.perform(
      get("/v1/api/role/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.data.id").value("1"))
      .andReturn();
  }

  @Test
  @Rollback(true)
  public void test03RoleInsert() throws Exception {
    RoleBO roleBO = new RoleBO();
    roleBO.setRoleName("testRoleInsert");
    roleBO.setRemark("remark");

    // 模拟请求的AES加密，BASE64模式
    String jsonByEncrypt = EncryptUtils.aesEncryptBase64(JSON.toJSONString(roleBO), aesKey);

    mockMvc.perform(
      post("/v1/api/role")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(jsonByEncrypt))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andReturn();
  }

  /**
   * 开启此测试用例之前，需要修改配置文件。 详见：
   */
  @Test
  @Rollback(true)
  public void test03RoleInsertByRSA() throws Exception {
    RoleBO roleBO = new RoleBO();
    roleBO.setRoleName("testRoleInsertByRSA");
    roleBO.setRemark("remark");

    // 生成密钥对：EncryptUtils.generateRsaKeyPairHexJson()
    // 私钥：
    String privateKey = "30820278020100300d06092a864886f70d0101010500048202623082025e02010002818100a10ab4526d4c68edb1240b89a12f1aef2ba0d25a1fa5b1a1d89ba3bc576a03bc6d47b5d214cc203745947745a4c5b5b97f9e7587ad69bd6dceeead8561ec0084b9c7016ff168a3c24f94a0b82305d8c0f3ccd63c9cbababfe02f0c61879d7f81f7d2107f6efcd7e63f2903c4fcb09c22941eaa105798e211871dde15f030597302030100010281810097e557d6135fa9ece053664a876cbdd3ef6bbe5ce152d0ec1e3a174353941c38033f4b40fefad63c2bf5f0561127a6d3738b0bc2508fd6eb96ee4b5eeed0c25ee65f0af2b59238f6ef3a2f7fc4a0e3b1c6f7eb7d701261ea892d699d71899fe8a2c3bbaf3e51e018e3e5ce4e0dad8a793290fcea49e94cb528a2c320e734a631024100fb880b93bbfa3f07c7e6739116b9651d322de1fed8766cb0aef31fb7424f3c118c1f446a0f5b7804c2899b2581c8210129e81bb2ca407f63d8d8ffa4f9404149024100a3e71db5acd1c9eb2fb77dd2a66f2e194d83a0956ef4768eeedf670d53f748bc228b1bc06249e4ec860cbc8403b54c02c8f693f3767ea969568e01bf7cf480db024100bf7c5e73e5932910df59cd7912f7a8c68540f0df762311b3a03c6e54b12268f462dc1ad53343cb26b482d59dc4237b1ccbae6c07bc794581d9bfb93efa91b421024100996249106299b55fce38e69c02bb5b25bcbfa8c10fa3e16ba3aa17d386378daeda98da30b10abc1c88da54752acf384206f592c1adab9d390212451a8182832f02403d7776922828cd8ad8d5047f49fcfe7521f8ffa7af7c7da73e83268c027fe3fc784dd41ec1cc0d08ac2edbbd2dfe662860b564cbae48586643e8d896c8c8dfad";
    // 公钥：
    String publicKey = "30819f300d06092a864886f70d010101050003818d0030818902818100a10ab4526d4c68edb1240b89a12f1aef2ba0d25a1fa5b1a1d89ba3bc576a03bc6d47b5d214cc203745947745a4c5b5b97f9e7587ad69bd6dceeead8561ec0084b9c7016ff168a3c24f94a0b82305d8c0f3ccd63c9cbababfe02f0c61879d7f81f7d2107f6efcd7e63f2903c4fcb09c22941eaa105798e211871dde15f03059730203010001";
    // 模拟请求的RSA公钥加密，HEX模式
    String jsonByEncrypt = EncryptUtils
      .rsaEncryptHexByPublicKey(JSON.toJSONString(roleBO), publicKey);

    mockMvc.perform(
      post("/v1/api/role/rsa")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(jsonByEncrypt))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andReturn();
  }

  @Test
  @Rollback(true)
  public void test04RoleDelete() throws Exception {
    mockMvc.perform(
      delete("/v1/api/role/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isNoContent())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andReturn();
  }

  @Test
  @Rollback(true)
  public void test05RoleUpdate() throws Exception {
    RoleBO roleBO = new RoleBO();
    roleBO.setRoleName("testRoleUpdate");

    mockMvc.perform(
      put("/v1/api/role/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(JSON.toJSONString(roleBO)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andReturn();

  }


  /**
   * ------------------------------------------------------------
   * <p>
   * errors test
   * <p>
   * ------------------------------------------------------------
   */
  @Test
  public void test06MethodArgumentNotValidException() throws Exception {
    mockMvc.perform(
      get("/v1/api/role/D")
        .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("400"))
      .andReturn();
  }

  @Test
  public void test07GetNoData() throws Exception {
    // 此方法可能受限流功能影响导致失败 ( 抛出 RequestReachMaxLimitException 异常 )
    mockMvc.perform(
      get("/v1/api/role/8848")
        .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("404"))
      .andReturn();
  }

  @Test
  @Rollback(true)
  public void test08InsertSecretError() throws Exception {
    RoleBO roleBO = new RoleBO();
    roleBO.setRoleName("testRoleInsert");
    roleBO.setRemark("remark");

    // 不对请求进行加密
    mockMvc.perform(
      post("/v1/api/role")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(JSON.toJSONString(roleBO)))
      .andExpect(status().isInternalServerError())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("500"))
      .andReturn();
  }

  @Test
  @Rollback(true)
  public void test09InsertValidatedRoleNameError() throws Exception {
    RoleBO roleBO = new RoleBO();
    // roleName为空
    roleBO.setRoleName("");

    // 模拟请求的AES加密，BASE64模式
    String jsonByEncrypt = EncryptUtils.aesEncryptBase64(JSON.toJSONString(roleBO), aesKey);

    mockMvc.perform(
      post("/v1/api/role")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(jsonByEncrypt))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("400"))
      .andReturn();
  }

  @Test
  @Rollback(true)
  public void test10InsertValidatedRemarkError() throws Exception {
    RoleBO roleBO = new RoleBO();
    roleBO.setRoleName("testRoleInsert");
    // remark字段长度超过128
    StringBuilder sb = new StringBuilder("remark");
    int n = 0;
    while (n < 13) {
      sb.append("1234567890");
      n++;
    }
    roleBO.setRemark(sb.toString());

    // 模拟请求的AES加密，BASE64模式
    String jsonByEncrypt = EncryptUtils.aesEncryptBase64(JSON.toJSONString(roleBO), aesKey);

    mockMvc.perform(
      post("/v1/api/role")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(jsonByEncrypt))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("400"))
      .andReturn();
  }


}
