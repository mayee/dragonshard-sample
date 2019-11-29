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

import com.tinify.Source;
import com.tinify.Tinify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dragonshard.dsf.core.toolkit.ExceptionUtils;
import net.dragonshard.dsf.tinypng.framework.service.ITinypngService;
import net.dragonshard.dsf.upload.local.common.model.UploadRequest;
import net.dragonshard.dsf.upload.local.common.model.UploadResult;
import net.dragonshard.dsf.upload.local.common.model.UploadToken;
import net.dragonshard.dsf.upload.local.exception.UploadTokenTimeoutException;
import net.dragonshard.dsf.upload.local.exception.UploadTokenValidException;
import net.dragonshard.dsf.upload.local.framework.service.IUploadLocalService;
import net.dragonshard.dsf.web.core.bean.Result;
import net.dragonshard.dsf.web.core.framework.controller.WebController;
import net.dragonshard.dsf.web.core.framework.exception.BizException;
import net.dragonshard.dsf.web.core.mapping.ApiVersion;
import net.dragonshard.sample.common.enums.BizErrorCodeEnum;
import net.dragonshard.sample.model.bo.UploadBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 上传文件 前端控制器
 * </p>
 *
 * @author dragonshard
 */
@Slf4j
@Api(tags = {"UploadLocal"}, description = "上传文件相关接口")
@RestController
@ApiVersion(1)
@RequestMapping(value = "/api/upload/local", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UploadLocalController extends WebController {

  @Autowired
  protected IUploadLocalService uploadLocalService;
  @Autowired(required = false)
  protected ITinypngService tinypngService;

  /**
   * 预上传，返回Token
   *
   * @param uploadBO 上传请求BO对象
   * @return httpResponse
   */
  @GetMapping("/preload")
  public ResponseEntity<Result<UploadToken>> preload(@Validated UploadBO uploadBO) {
    return success(uploadLocalService.preload(uploadBO.convert(UploadRequest.class)));
  }

  /**
   * 上传文件
   * <p>
   * 当开启 <code>dragonshard.upload.local.tinypng-async-compress = true</code>
   * 并且引入 dragonshard-tinypng-starter 时，会对 JPG/PNG 图片自动进行异步压缩，覆盖原文件。
   *
   * @param uploadFile 文件
   * @param uploadBO 上传请求BO对象
   * @return httpResponse
   */
  @ApiOperation(value = "上传文件")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "file", value = "待上传对象", required = true, paramType = "query")
  })
  @PostMapping("/upload")
  public ResponseEntity<Result<UploadResult>> upload(@RequestParam("file") MultipartFile uploadFile,
    @Validated UploadBO uploadBO) {
    try {
      return success(uploadLocalService.upload(uploadFile, uploadBO.convert(UploadRequest.class), uploadBO.getToken()));
    } catch (UploadTokenValidException e) {
      // 签名校验失败
      throw new BizException(BizErrorCodeEnum.UPLOAD_TOKEN_VALID_FAIL.convert());
    } catch (UploadTokenTimeoutException e) {
      // 签名超时
      throw new BizException(BizErrorCodeEnum.UPLOAD_TOKEN_TIMEOUT.convert());
    } catch (IOException e) {
      // 其它IO异常
      throw ExceptionUtils.get(e.getMessage());
    }
  }

  /**
   * 预览、下载文件
   *
   * @param fileName 文件名称
   * @return 文件流
   */
  @GetMapping("/media/{fileName}")
  public ResponseEntity preview(@PathVariable String fileName) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<>(uploadLocalService.files(fileName), headers, HttpStatus.OK);
  }

  /**
   * 通过 dragonshard-tinypng-starter 实现的图片异步压缩
   */
  @GetMapping("/compress")
  public ResponseEntity<Result<String>> compress() {
    tinypngService.compressAndSave("upload_local/demo_img.jpeg", "upload_local/demo_img_after.jpeg");
    return success("Has joined the compression queue!");
  }


}
