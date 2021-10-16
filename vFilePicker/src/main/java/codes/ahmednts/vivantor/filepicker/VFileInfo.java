package codes.ahmednts.vivantor.filepicker;

import android.media.MediaMetadataRetriever;
import java.io.File;

/**
 * Created by AhmedNTS on 2016-05-31.
 */
public class VFileInfo {
  private File fileObject;

  private String filePath;
  private int fileType;
  private String fileExtension;

  public VFileInfo(String filePath) {
    this.filePath = filePath;
    this.fileType = getFileType();
  }

  public String getFilePath() {
    return filePath;
  }

  public File getFileObject() {
    if (!VFileUtils.isLocal(filePath)) {
      return null;
    }

    if (fileObject == null) {
      fileObject = new File(filePath);
    }

    return fileObject;
  }

  public long getFileSize() {
    return getFileObject().length();//(1024*1024)*1 = 1 MB
  }

  public int getFileType() {
    File file = getFileObject();
    if (file == null) {
      throw new NullPointerException("getFileObject() returned null!");
    }

    String typeString = VFileUtils.getMimeType(file);

    if (typeString != null) {
      if (typeString.contains("image")) {
        fileType = VFilePicker.IMAGE;
      } else if (typeString.contains("audio")) {
        fileType = VFilePicker.AUDIO;
      } else if (typeString.contains("video")) {
        fileType = VFilePicker.VIDEO;
      }
    }

    return fileType;
  }

  public String getFileExtension() {
    if (fileExtension == null) {
      fileExtension = VFileUtils.getExtension(filePath);
    }

    return fileExtension;
  }

  public long getFileDuration() {
    if (fileType == VFilePicker.IMAGE) return 0;

    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    retriever.setDataSource(filePath);
    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    if (time == null) return 0;

    long timeInmillisec = Long.parseLong(time);
    long duration = timeInmillisec / 1000;
    //		long hours = duration / 3600;
    //		long minutes = (duration - hours * 3600) / 60;
    //		long seconds = duration - (hours * 3600 + minutes * 60);

    return duration;
  }

  public String getFileBase64String() {
    if (!VFileUtils.isLocal(filePath)) {
      return null;
    }

    try {
      if (fileType == VFilePicker.IMAGE) {
        //			    return VFileUtils.getBitmapBase64String(VFileUtils.getBitmap(filePath, 512));

        return VFileUtils.getBitmapBase64String(
            VFileUtils.rotateImageIfRequired(VFileUtils.getBitmap(filePath, 700), filePath));
      } else {
        return VFileUtils.getFileBase64String(getFileObject());
      }
    } catch (Exception e) {
      e.printStackTrace();

      return null;
    }
  }

  public byte[] getFileByteArray() {
    if (!VFileUtils.isLocal(filePath)) {
      return null;
    }

    try {
      if (fileType == VFilePicker.IMAGE) {
        //			    return VFileUtils.getBitmapByteArray(VFileUtils.getBitmap(filePath, 512));

        return VFileUtils.getBitmapByteArray(
            VFileUtils.rotateImageIfRequired(VFileUtils.getBitmap(filePath, 1024), filePath));
      } else {
        return VFileUtils.getFileByteArray(getFileObject());
      }
    } catch (Exception e) {
      e.printStackTrace();

      return null;
    }
  }
}
