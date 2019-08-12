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

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.dragonshard.dsf.web.core.mapping.ApiVersion;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * UserRole 前端控制器
 * </p>
 *
 * @author dragonshard
 */
@Slf4j
@Api(tags = {"UserRole" }, description = "系统用户角色关系表相关接口")
@RestController
@ApiVersion(1)
@RequestMapping(value = "/api/userRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UserRoleController  {

}
