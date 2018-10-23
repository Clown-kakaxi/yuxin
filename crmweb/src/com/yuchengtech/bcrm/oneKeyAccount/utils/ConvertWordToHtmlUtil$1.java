package com.yuchengtech.bcrm.oneKeyAccount.utils;

import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.usermodel.PictureType;

class ConvertWordToHtmlUtil$1
  implements PicturesManager
{
  public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches)
  {
    return "test/" + suggestedName;
  }
}