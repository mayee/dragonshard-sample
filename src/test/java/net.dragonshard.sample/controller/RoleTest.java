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

import com.alibaba.fastjson.JSON;
import net.dragonshard.dsf.core.toolkit.EncryptUtils;
import net.dragonshard.sample.Application;
import net.dragonshard.sample.model.bo.RoleBO;
import net.dragonshard.sample.model.entity.Role;
import net.dragonshard.sample.service.IRoleService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        while (n < 13){
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
