package com.yuchengtech.bcrm.oneKeyAccount.utils;

import com.yuchengtech.bob.upload.FileTypeConstance;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.usermodel.PictureType;

class ConvertWordToHtmlUtil$2
  implements PicturesManager
{
  public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches)
  {
    String localPicPath = FileTypeConstance.getBipProperty("crm.localPicPath");
    return localPicPath + suggestedName;
  }
}