package com.alinkeji.bot.retdata;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ApiRawData {

  @JSONField(name = "status")
  private String status;

  @JSONField(name = "retcode")
  private int retcode;

  @JSONField(name = "data")
  private JSONObject data;
}
