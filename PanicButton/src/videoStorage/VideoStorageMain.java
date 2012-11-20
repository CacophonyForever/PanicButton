package videoStorage;

public class VideoStorageMain {

		private static final String DEFAULT_SAVE_LOCATION = "~/PanicB";
	public static void main(String[] args)
	  {		
		new VideoStorageHost(DEFAULT_SAVE_LOCATION);
	  }
}
