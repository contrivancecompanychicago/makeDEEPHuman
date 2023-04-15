import java.lang.Object;

public abstract FirebaseApp extends Main{
  
  
FileInputStream serviceAccount =
  new FileInputStream("path/to/serviceAccountKey.json");

FirebaseOptions options = new FirebaseOptions.Builder()
  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
  .setDatabaseUrl("https://yellowfitexercisemaps-default-rtdb.firebaseio.com")
  .build();

FirebaseApp.initializeApp(options);
  
}
