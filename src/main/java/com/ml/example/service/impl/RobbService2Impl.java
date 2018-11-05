package com.ml.example.service.impl;

import com.ml.example.service.RobbService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobbService2Impl
  implements RobbService
{

  @Autowired
  private RobbService robbService;

  public String home(String paramString)
  {
		String ss = "this is "+getClass()+" home("+paramString+")";
		System.out.println(ss);
		return ss;
  }

  public void home1(String[] paramArrayOfString)
  {
    this.robbService.home1(paramArrayOfString);
  }

  public List<String> home2(String[] paramArrayOfString)
  {
    return this.robbService.home2(paramArrayOfString);
  }

  public Map<String, Object> home3(List<String> paramList)
  {
    return this.robbService.home3(paramList);
  }

  public Map home4(List paramList)
    throws IOException, RuntimeException
  {
    return this.robbService.home4(paramList);
  }

  public String home5(int paramInt, Long paramLong, BigDecimal paramBigDecimal)
  {
    return this.robbService.home5(paramInt, paramLong, paramBigDecimal);
  }

  public String home6(boolean paramBoolean, char paramChar, byte paramByte, short paramShort, int paramInt, float paramFloat, double paramDouble, int[] paramArrayOfInt, String[] paramArrayOfString, Integer paramInteger, long paramLong)
  {
    return this.robbService.home6(paramBoolean, paramChar, paramByte, paramShort, paramInt, paramFloat, paramDouble, paramArrayOfInt, paramArrayOfString, paramInteger, paramLong);
  }

  public String home8()
  {
    return this.robbService.home8();
  }

	public void setService(RobbService robbService) {
		this.robbService = robbService;
	}
}