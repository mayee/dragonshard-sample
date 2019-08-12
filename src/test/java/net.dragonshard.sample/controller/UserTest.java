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
import com.google.common.collect.ImmutableList;
import net.dragonshard.dsf.core.toolkit.EncryptUtils;
import net.dragonshard.sample.Application;
import net.dragonshard.sample.model.bo.RoleBO;
import net.dragonshard.sample.model.bo.UserBO;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends AbstractTransactionalTestNGSpringContextTests {

    private MockMvc mockMvc;

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
    public void test01Page() throws Exception {
        mockMvc.perform(
            get("/v1/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("loginName", "user")
                .param("status", "1")
                .param("nickname", "Master-2")
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status").value("200"))
            .andReturn();
    }

    @Test
    public void test02Get() throws Exception {
        mockMvc.perform(
            get("/v1/api/user/1")
                .header("X-DSF-MappingName", "pathVariableDemo")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.data.id").value("1"))
            .andReturn();
    }

    @Test
    @Rollback(true)
    public void test03Insert() throws Exception {

        // 模拟前端用JS进行RSA加密，Hex模式，具体方法参考官网
        // 直接使用加密好的密文
        // 对应的明文为：{"loginName":"ds-user","nickname":"ds","email":"ds@dragonshard.net","roleIds":["1"]}
        String jsonByEncrypt = "171e494ce1d00466cf9547b25223a0f2d9a4b8c3d8067281205aac51fb86246b7c79c6f2d0bb6bb62c5d26f430854eec6cb16b99a4b65f12c9313da3b0fd65078cd847a8e4dcff439f90de146c481eb0be1860d4343972cf4803dfc7fdfaf0520a8f49e792f1a026a89532f109571e435d698d9db98d1960e326f27fae37f020";

        mockMvc.perform(
            post("/v1/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonByEncrypt))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn();
    }

    @Test
    @Rollback(true)
    public void test04Delete() throws Exception {
        mockMvc.perform(
            put("/v1/api/user/1/status")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"status\":\"0\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn();
    }

    @Test
    @Rollback(true)
    public void test05Update() throws Exception {
        UserBO userBo = new UserBO();
        userBo.setLoginName("user_master-1");
        userBo.setNickname("Master-1-update");
        userBo.setEmail("user_master1-update@dragonshard.net");
        userBo.setRoleIds(ImmutableList.of(2L));

        mockMvc.perform(
            put("/v1/api/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(userBo)))
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
            get("/v1/api/user/D")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status").value("400"))
            .andReturn();
    }

    @Test
    public void test07GetNoData() throws Exception {
        mockMvc.perform(
            get("/v1/api/user/8848")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status").value("404"))
            .andReturn();
    }

    @Test
    @Rollback(true)
    public void test08InsertSecretError() throws Exception {
        UserBO userBo = new UserBO();
        userBo.setLoginName("ds-user");
        userBo.setEmail("ds@dragonshard.net");
        userBo.setNickname("ds");
        userBo.setRoleIds(ImmutableList.of(1L));

        // 不对请求进行加密
        mockMvc.perform(
            post("/v1/api/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(userBo)))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status").value("500"))
            .andReturn();
    }


}
